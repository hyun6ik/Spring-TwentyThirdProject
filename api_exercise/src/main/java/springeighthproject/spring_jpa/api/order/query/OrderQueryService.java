package springeighthproject.spring_jpa.api.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderQueryService {

    private final OrderQueryRepository orderQueryRepository;

    @Transactional
    public List<OrderQueryDto> findOrderQueryDtos() {
        List<OrderQueryDto> result = orderQueryRepository.findOrders();

        result.forEach(o -> {
            List<OrderItemQueryDto> orderItems = orderQueryRepository.findOrderItems(o.getOrderId());
            o.setOrderItems(orderItems);
        });
        return result;
    }
}
