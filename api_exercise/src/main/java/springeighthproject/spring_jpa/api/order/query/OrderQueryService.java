package springeighthproject.spring_jpa.api.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springeighthproject.spring_jpa.api.Result;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderQueryService {

    private final OrderQueryRepository orderQueryRepository;

    @Transactional
    public List<OrderQueryDto> findOrderQueryDtos() {
        List<OrderQueryDto> result = orderQueryRepository.findOrders();    //쿼리 1번

        result.forEach(o -> {
            List<OrderItemQueryDto> orderItems = orderQueryRepository.findOrderItems(o.getOrderId()); //쿼리 N번
            o.setOrderItems(orderItems);
        });
        return result;
    }

    public Result<List<OrderQueryDto>> findAllByDto_optimization() {
        List<OrderQueryDto> orders = orderQueryRepository.findOrders();

        List<Long> orderIds = orders.stream()
                .map(o -> o.getOrderId())
                .collect(Collectors.toList());

        List<OrderItemQueryDto> orderItems = orderQueryRepository.findOrderItems_Optimization(orderIds);
        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream()
                .collect(Collectors.groupingBy(orderItemQueryDto -> orderItemQueryDto.getOrderId()));

        orders.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return new Result(orders);
    }
}
