package ifsp.ordersys.application.service;

import ifsp.ordersys.domain.model.aggregate.Order;
import ifsp.ordersys.domain.model.entity.OrderItem;
import ifsp.ordersys.domain.repository.OrderRepository;
import ifsp.ordersys.domain.model.valueobject.CustomerId;

import java.util.List;

public class CreateOrderService {

    private final OrderRepository orderRepository;

    public CreateOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order execute(String orderId, CustomerId customerId, List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("EMPTY_ORDER");
        }
        Order order = new Order(orderId, customerId, items);
        orderRepository.save(order);
        return order;
    }
}
