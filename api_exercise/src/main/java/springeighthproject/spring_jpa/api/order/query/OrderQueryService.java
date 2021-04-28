package springeighthproject.spring_jpa.api.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springeighthproject.spring_jpa.api.Result;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

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
                .collect(toList());

        List<OrderItemQueryDto> orderItems = orderQueryRepository.findOrderItems_Optimization(orderIds);
        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream()
                .collect(groupingBy(orderItemQueryDto -> orderItemQueryDto.getOrderId()));

        orders.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return new Result(orders);
    }

    public List<OrderQueryDto> findAllByDto_flat() {
        List<OrderFlatDto> flats = orderQueryRepository.findAllByDto_flat();

        return flats.stream()
                .collect(groupingBy(o -> new OrderQueryDto(o.getOrderId(), o.getName(), o.getOrderDate(), o.getOrderStatus(),o.getAddress()),
                        mapping(o -> new OrderItemQueryDto(o.getOrderId(), o.getItemName(), o.getOrderPrice(), o.getCount()), toList())))
                        .entrySet().stream()
                        .map(e -> new OrderQueryDto(e.getKey().getOrderId(), e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(), e.getKey().getAddress(), e.getValue()))
                        .collect(toList());
    }
}
