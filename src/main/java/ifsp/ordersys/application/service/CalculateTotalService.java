package ifsp.ordersys.application.service;

import ifsp.ordersys.domain.model.aggregate.Order;
import ifsp.ordersys.domain.repository.OrderRepository;
import ifsp.ordersys.domain.model.valueobject.Money;

import java.util.Optional;

public class CalculateTotalService {

    private final OrderRepository orderRepository;

    public CalculateTotalService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Money execute(String orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            throw new IllegalArgumentException("ORDER_NOT_FOUND");
        }
        Order order = optionalOrder.get();
        return order.getTotal();
    }
}
