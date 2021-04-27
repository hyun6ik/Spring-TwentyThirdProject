package springeighthproject.spring_jpa.api.order.simple;

import lombok.Data;
import springeighthproject.spring_jpa.domain.Address;
import springeighthproject.spring_jpa.domain.Order;
import springeighthproject.spring_jpa.domain.OrderStatus;

import java.time.LocalDateTime;

@Data
public class SimpleOrderDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private String result;

    public SimpleOrderDto(Order order){
        orderId = order.getId();
        name = order.getMember().getName();  //LAZY 초기화
        orderDate = order.getOrderDate();
        orderStatus = order.getStatus();
        address = order.getDelivery().getAddress(); // LAZY 초기화화        result = "성공";
    }
}
