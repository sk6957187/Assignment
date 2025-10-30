package in.nayak.foodiesapi.controller;

import in.nayak.foodiesapi.io.OrderRequest;
import in.nayak.foodiesapi.io.OrderResponse;
import in.nayak.foodiesapi.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public OrderResponse createOrderWithPayment(@RequestBody OrderRequest request){
        OrderResponse response = orderService.createOrderWithPayment(request);
        return response;
    }
}
