package in.nayak.foodiesapi.io;

import in.nayak.foodiesapi.entity.OrderItem;
import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    private String userId;
    private String userAddress;
    private String phoneNumber;
    private String email;
    private double amount;
    private String orderStatus;
    private List<OrderItem> orderItemList;
}
