package ifsp.ordersys.application.service;

import ifsp.ordersys.domain.model.aggregate.Order;
import ifsp.ordersys.domain.repository.OrderRepository;

import java.util.Optional;

public class ApplyDiscountService {

    private final OrderRepository orderRepository;

    public ApplyDiscountService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void execute(String orderId, double percentage) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            throw new IllegalArgumentException("ORDER_NOT_FOUND");
        }
        Order order = optionalOrder.get();
        order.applyDiscount(percentage);
        orderRepository.save(order);
    }
}
