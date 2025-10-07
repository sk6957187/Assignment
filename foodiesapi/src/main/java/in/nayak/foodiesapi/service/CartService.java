package in.nayak.foodiesapi.service;

import in.nayak.foodiesapi.io.CartRequest;
import in.nayak.foodiesapi.io.CartResponse;

public interface CartService {
    CartResponse addCart(CartRequest request);

    CartResponse getCart();

    void clearCart();

    CartResponse removeFromCart(CartRequest request);
}
