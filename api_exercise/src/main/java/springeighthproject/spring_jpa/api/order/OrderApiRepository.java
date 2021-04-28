package springeighthproject.spring_jpa.api.order;

import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import springeighthproject.spring_jpa.domain.Order;

import java.util.List;

public interface OrderApiRepository extends JpaRepository<Order,Long> {

    @Query("select distinct o from Order o join fetch o.member m join fetch o.delivery d" +
            " join fetch o.orderItems oi" +
            " join fetch oi.item i")
    List<Order> findAllWithItem();

    @Query("select o from Order o join fetch o.member m join fetch o.delivery d")
    List<Order> findAllByMemberAndDelivery();

    @Query("select o from Order o join fetch o.member m join fetch o.delivery d")
    List<Order> find2AllByMemberAndDeliveryPage(Pageable pageable);

}
