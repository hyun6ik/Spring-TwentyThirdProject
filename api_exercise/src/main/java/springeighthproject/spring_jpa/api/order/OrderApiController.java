package springeighthproject.spring_jpa.api.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springeighthproject.spring_jpa.api.Result;
import springeighthproject.spring_jpa.domain.Order;
import springeighthproject.spring_jpa.domain.OrderItem;
import springeighthproject.spring_jpa.repository.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/orders")
    public Result<List<Order>> ordersV1(){
        List<Order> all = orderRepository.findAll();
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName());
        }
        return new Result(all);
    }

    @GetMapping("/api/v2/orders")
    public Result<List<OrderDto>> ordersV2(){
        List<Order> orders = orderRepository.findAll();
        List<OrderDto> collect = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());
        return new Result(collect);
    }
}
