package springeighthproject.spring_jpa.api.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import springeighthproject.spring_jpa.domain.Order;

import java.util.List;

public interface OrderSimpleQueryRepository extends JpaRepository<Order, Long> {

    @Query("select new springeighthproject.spring_jpa.api.order.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
            " from Order o join o.member m " +
            "join o.delivery d")
    List<OrderSimpleQueryDto> findOrderDtos();
}
