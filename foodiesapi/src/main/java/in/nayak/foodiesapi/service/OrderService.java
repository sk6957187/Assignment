package in.nayak.foodiesapi.service;

import in.nayak.foodiesapi.io.OrderRequest;
import in.nayak.foodiesapi.io.OrderResponse;

import java.util.List;
import java.util.Map;

public interface OrderService {
    OrderResponse createOrderWithPayment(OrderRequest request);

    void verifyPayment(Map<String, String> paymentData, String Status);

    List<OrderResponse> getUserOrders();

    void removeOrder(String orderId);

    List<OrderResponse> getOrderOfAllUsers();

    void updateOrderStatus(String orderId, String status);
}
