package in.nayak.foodiesapi.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import in.nayak.foodiesapi.entity.CartEntity;
import in.nayak.foodiesapi.entity.OrderEntity;
import in.nayak.foodiesapi.io.OrderRequest;
import in.nayak.foodiesapi.io.OrderResponse;
import in.nayak.foodiesapi.repository.CartRespository;
import in.nayak.foodiesapi.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Value("${razorpay.key}")
    private String RAZORPAY_KEY;

    @Value("${razorpay.secret}")
    private String RAZORPAY_SECRET;

    private final OrderRepository orderRepository;
    private final CartRespository cartRespository;
    private final UserService userService;

    @Override
    public OrderResponse createOrderWithPayment(OrderRequest request) {
        try {
            // Step 1️⃣ Save initial order
            OrderEntity newOrder = convertToEntity(request);
            newOrder.setPaymentStatus("PENDING");
            newOrder.setOrderStatus("CREATED");
            newOrder = orderRepository.save(newOrder);

            // Step 2️⃣ Create Razorpay order
            RazorpayClient razorpayClient = new RazorpayClient(RAZORPAY_KEY, RAZORPAY_SECRET);
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", (int) (newOrder.getAmount() * 100)); // in paise
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "txn_" + newOrder.getId());
            orderRequest.put("payment_capture", 1);

            Order razorpayOrder = razorpayClient.orders.create(orderRequest);

            // Step 3️⃣ Update with Razorpay order details
            newOrder.setRazorpayOrderId(razorpayOrder.get("id"));
            newOrder = orderRepository.save(newOrder);

            // Step 4️⃣ Return response
            return convertToResponse(newOrder);

        } catch (RazorpayException e) {
            throw new RuntimeException("Failed to create Razorpay order", e);
        }
    }

    @Override
    public void verifyPayment(Map<String, String> paymentData, String status) {
        String razorpayOrderId = paymentData.get("razorpay_order_id");
        OrderEntity existingOrder = orderRepository.findByRazorpayOrderId(razorpayOrderId)
                .orElseThrow(()->new RuntimeException("Order not found"));
        existingOrder.setPaymentStatus(status);
        existingOrder.setRazorpayPaymentId(paymentData.get("razorpay_payment_id"));
        existingOrder.setRazorpaySignature(paymentData.get("razorpay_signature"));
        String loggedInUserId = userService.findByUserId();
        existingOrder.setUserId(loggedInUserId);

        orderRepository.save(existingOrder);

        if ("paid".equalsIgnoreCase(status)) {
            Optional<CartEntity> cartOpt = cartRespository.findByUserId(existingOrder.getUserId());
            if (cartOpt.isPresent()) {
                CartEntity cart = cartOpt.get();
                cart.getItems().clear();
                cartRespository.save(cart);
                System.out.println("Cart cleared after payment, order history retained.");
            }
        }


    }

    @Override
    public List<OrderResponse> getUserOrders() {
        String loggedInUserId = userService.findByUserId();
        List<OrderEntity> list = orderRepository.findByUserId(loggedInUserId);

        return list.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void removeOrder(String orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<OrderResponse> getOrderOfAllUsers() {
        List<OrderEntity> list = orderRepository.findAll();
        return list.stream().map(entity -> convertToResponse(entity)).collect(Collectors.toList());
    }

    @Override
    public void updateOrderStatus(String orderId, String status) {
        OrderEntity entity = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        entity.setOrderStatus(status);
        orderRepository.save(entity);
    }

    private OrderResponse convertToResponse(OrderEntity newOrder) {
        return OrderResponse.builder()
                .orderId(newOrder.getId())
                .razorpayOrderId(newOrder.getRazorpayOrderId())
                .amount(newOrder.getAmount())
                .userAddress(newOrder.getUserAddress())
                .currency("INR")
                .status(newOrder.getOrderStatus())
                .orderItemList(newOrder.getOrderItemList())
                .build();
    }

    private OrderEntity convertToEntity(OrderRequest request) {
        String loggedInUserId = userService.findByUserId();
        OrderEntity order = OrderEntity.builder()
                .userId(loggedInUserId)
                .userAddress(request.getUserAddress())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .amount(request.getAmount())
                .paymentStatus("PENDING")
                .orderStatus(request.getOrderStatus())
                .build();

        if (request.getOrderItemList() != null) {
            request.getOrderItemList().forEach(item -> item.setOrder(order)); // ✅ set parent
            order.setOrderItemList(request.getOrderItemList());
        }

        return order;
    }

}
