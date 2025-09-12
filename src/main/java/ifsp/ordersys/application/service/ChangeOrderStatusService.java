package ifsp.ordersys.application.service;

import ifsp.ordersys.domain.model.aggregate.Order;
import ifsp.ordersys.domain.repository.OrderRepository;
import ifsp.ordersys.domain.model.aggregate.Order.Status;


import java.util.Optional;

public class ChangeOrderStatusService {

    private final OrderRepository orderRepository;

    public ChangeOrderStatusService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void execute(String orderId, Status newStatus) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            throw new IllegalArgumentException("ORDER_NOT_FOUND");
        }
        Order order = optionalOrder.get();
        order.changeStatus(newStatus);
        orderRepository.save(order);
    }
}
