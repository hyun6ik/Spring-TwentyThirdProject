package springeighthproject.spring_jpa.api.order;

import lombok.Data;
import springeighthproject.spring_jpa.domain.Address;
import springeighthproject.spring_jpa.domain.Order;
import springeighthproject.spring_jpa.domain.OrderStatus;

import java.time.LocalDateTime;

@Data
public class OrderSimpleQueryDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;


    public OrderSimpleQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }
}
