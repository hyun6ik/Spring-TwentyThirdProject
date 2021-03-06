package springeighthproject.spring_jpa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springeighthproject.spring_jpa.domain.Delivery;
import springeighthproject.spring_jpa.domain.Member;
import springeighthproject.spring_jpa.domain.Order;
import springeighthproject.spring_jpa.domain.OrderItem;
import springeighthproject.spring_jpa.domain.item.Item;
import springeighthproject.spring_jpa.repository.ItemRepository;
import springeighthproject.spring_jpa.repository.MemberRepository;
import springeighthproject.spring_jpa.repository.OrderRepository;
import springeighthproject.spring_jpa.repository.OrderSearch;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberJpaRepository;
    private final ItemRepository itemRepository;

    // 주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        // 엔티티 조회
        Member member = memberJpaRepository.findById(memberId).orElse(null);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createorder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);
        return order.getId();


    }
    // 취소
    @Transactional
    public void cancelOrder(Long orderId){
        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
    }

    // 검색
    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAllByCriteria(orderSearch);
    }
}
