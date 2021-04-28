package springeighthproject.spring_jpa.api.order.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springeighthproject.spring_jpa.domain.Order;

import java.util.List;

public interface OrderQueryRepository extends JpaRepository<Order, Long> {

    @Query("select new springeighthproject.spring_jpa.api.order.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address) " +
            "from Order o join o.member m join o.delivery d")
    List<OrderQueryDto> findOrders();

    @Query("select new springeighthproject.spring_jpa.api.order.query.OrderItemQueryDto" +
            "(oi.order.id, i.name, oi.orderPrice, oi.count)" +
            " from OrderItem oi join oi.item i where oi.order.id = :orderId")
    List<OrderItemQueryDto> findOrderItems(@Param("orderId") Long orderId);

}
