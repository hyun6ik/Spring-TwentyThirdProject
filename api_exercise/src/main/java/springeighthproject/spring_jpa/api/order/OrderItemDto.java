package springeighthproject.spring_jpa.api.order;

import lombok.Data;
import springeighthproject.spring_jpa.domain.OrderItem;

@Data
public class OrderItemDto {

    private String itemName;
    private int orderPrice;
    private int count;

    public OrderItemDto(OrderItem orderItem) {
        itemName = orderItem.getItem().getName();
        orderPrice = orderItem.getOrderPrice();
        count = orderItem.getCount();
    }
}
