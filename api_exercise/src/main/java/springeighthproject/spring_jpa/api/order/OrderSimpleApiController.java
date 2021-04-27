package springeighthproject.spring_jpa.api.order;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springeighthproject.spring_jpa.api.Result;
import springeighthproject.spring_jpa.domain.Order;
import springeighthproject.spring_jpa.repository.OrderRepository;
import springeighthproject.spring_jpa.repository.OrderSearch;
import springeighthproject.spring_jpa.service.OrderService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * xToOne(ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName(); // LAZY 강제 초기화
            order.getDelivery().getAddress(); // LAZY 강제 초기화

        }
        return all;
    }
    @GetMapping("/api/v2/simple-orders")
    public Result<List<SimpleOrderDto>> ordersV2(){
        // ORDER 2개
        // N + 1 오더1 + 회원 N +  배송 N
        List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());
        List<SimpleOrderDto> collect = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @GetMapping("/api/v3/simple-orders")
    public Result<List<SimpleOrderDto>> ordersV3(){
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> collect = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return new Result(collect);
    }
}