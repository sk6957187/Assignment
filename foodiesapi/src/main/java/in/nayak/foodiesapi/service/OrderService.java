package in.nayak.foodiesapi.service;

import in.nayak.foodiesapi.io.OrderRequest;
import in.nayak.foodiesapi.io.OrderResponse;

public interface OrderService {
    OrderResponse createOrderWithPayment(OrderRequest request);
}
