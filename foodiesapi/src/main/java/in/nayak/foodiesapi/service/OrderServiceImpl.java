package in.nayak.foodiesapi.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import in.nayak.foodiesapi.entity.OrderEntity;
import in.nayak.foodiesapi.io.OrderRequest;
import in.nayak.foodiesapi.io.OrderResponse;
import in.nayak.foodiesapi.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Value("${razorpay.key}")
    private String RAZORPAY_KEY;

    @Value("${razorpay.secret}")
    private String RAZORPAY_SECRET;

    private final OrderRepository orderRepository;

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

    private OrderResponse convertToResponse(OrderEntity newOrder) {
        return OrderResponse.builder()
                .orderId(newOrder.getId())
                .razorpayOrderId(newOrder.getRazorpayOrderId())
                .amount(newOrder.getAmount())
                .currency("INR")
                .status(newOrder.getOrderStatus())
                .orderItemList(newOrder.getOrderItemList())
                .build();
    }

    private OrderEntity convertToEntity(OrderRequest request) {
        OrderEntity order = OrderEntity.builder()
                .userId(request.getUserId())
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
