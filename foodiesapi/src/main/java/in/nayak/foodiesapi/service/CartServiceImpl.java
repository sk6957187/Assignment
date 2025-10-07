package in.nayak.foodiesapi.service;

import in.nayak.foodiesapi.entity.CartEntity;
import in.nayak.foodiesapi.io.CartRequest;
import in.nayak.foodiesapi.io.CartResponse;
import in.nayak.foodiesapi.repository.CartRespository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService{

    private final CartRespository cartRespository;
    private final UserService userService;

    @Override
    @Transactional
    public void clearCart(){
        String loggedInUserId = userService.findByUserId();
        cartRespository.deleteByUserId(loggedInUserId);

    }

    @Override
    public CartResponse removeFromCart(CartRequest request) {
        String loggedInUserId = userService.findByUserId();
        CartEntity entity = cartRespository.findByUserId(loggedInUserId)
                .orElseThrow(() -> new RuntimeException("Cart is not found"));

        Map<String, Integer> cartItems = entity.getItems();
        if(cartItems.containsKey(request.getFoodId())){
            int currentQty = cartItems.get(request.getFoodId());
            if(currentQty > 0){
                cartItems.put(request.getFoodId(), currentQty -1 );
            } else {
                cartItems.remove(request.getFoodId());
            }
            entity = cartRespository.save(entity);
        }

        return convertToResponse(entity);
    }

    @Override
    public CartResponse addCart(CartRequest request){
        String loggedInUserId = userService.findByUserId();
        Optional<CartEntity> cartEntityOptional = cartRespository.findByUserId(loggedInUserId);
        CartEntity cartEntity =  cartEntityOptional.orElseGet(() -> new CartEntity(loggedInUserId, new HashMap<>()));
        Map<String, Integer> cartItems = cartEntity.getItems();
        cartItems.put(request.getFoodId(), cartItems.getOrDefault(request.getFoodId(), 0)+1);
        cartEntity.setItems(cartItems);
        cartEntity = cartRespository.save(cartEntity);
        return convertToResponse(cartEntity);
    }

    @Override
    public CartResponse getCart() {
        String loggedInUserId = userService.findByUserId();
        CartEntity entity = cartRespository.findByUserId(loggedInUserId).orElse(new CartEntity(null, loggedInUserId,
                new HashMap<>()));
        return convertToResponse(entity);
    }

    private CartResponse convertToResponse(CartEntity cartEntity){
        return CartResponse.builder()
                .id(String.valueOf(cartEntity.getId()))
                .userId(cartEntity.getUserId())
                .items(cartEntity.getItems())
                .build();
    }
}
