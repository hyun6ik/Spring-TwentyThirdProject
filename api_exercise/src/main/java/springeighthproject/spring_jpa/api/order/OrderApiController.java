package springeighthproject.spring_jpa.api.order;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springeighthproject.spring_jpa.api.Result;
import springeighthproject.spring_jpa.domain.Order;
import springeighthproject.spring_jpa.domain.OrderItem;
import springeighthproject.spring_jpa.repository.OrderRepository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderApiRepository orderApiRepository;

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

    /**
     * 1:N 페치조인시 -> 페이징 불가능!! 치명적 단점!!
     */

    @GetMapping("/api/v3/orders")
    public Result<OrderDto> ordersV3(){
        List<Order> orders = orderApiRepository.findAllWithItem();
        for (Order order : orders) {
            System.out.println("order = " + order);
        }
        List<OrderDto> collect = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @GetMapping("/api/v3.1/orders")
    public Result<OrderDto> ordersV3_page(){
        PageRequest page = PageRequest.of(1, 100);
        List<Order> orders = orderApiRepository.findAllByMemberAndDelivery(); // ToOne

        List<OrderDto> collect = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());
        return new Result(collect);

    }

    @GetMapping("/api/v3.2/orders")
    public Result<List<OrderDto>> ordersV3_page2(){
        PageRequest pageRequest = PageRequest.of(0, 100);

        List<Order> orders = orderApiRepository.find2AllByMemberAndDeliveryPage(pageRequest);// ToOne


        List<OrderDto> collect = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());
        return new Result(collect);

    }
}
