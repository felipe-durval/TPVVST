package ifsp.ordersys.application.service;

import ifsp.ordersys.domain.model.aggregate.Order;
import ifsp.ordersys.domain.repository.OrderRepository;

import java.util.Optional;

public class AddSpecialRequestService {

    private final OrderRepository orderRepository;

    public AddSpecialRequestService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void execute(String orderId, String request) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            throw new IllegalArgumentException("ORDER_NOT_FOUND");
        }
        Order order = optionalOrder.get();
        order.addSpecialRequest(request);
        orderRepository.save(order);
    }
}
