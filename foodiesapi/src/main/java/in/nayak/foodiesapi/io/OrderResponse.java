package in.nayak.foodiesapi.io;

import in.nayak.foodiesapi.entity.OrderItem;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private String orderId;
    private String razorpayOrderId;
    private double amount;
    private String currency;
    private String status;
    private List<OrderItem> orderItemList;
}
