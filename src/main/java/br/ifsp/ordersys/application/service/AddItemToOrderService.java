package br.ifsp.ordersys.application.service;

import br.ifsp.ordersys.domain.aggregate.Order;
import br.ifsp.ordersys.domain.entity.OrderItem;

import java.util.Objects;
import java.util.UUID;

public class AddItemToOrderService {

    private final PlaceOrderService placeOrderService;

    public AddItemToOrderService(PlaceOrderService placeOrderService) {
        this.placeOrderService = Objects.requireNonNull(placeOrderService, "placeOrderService");
    }

    public void addItem(UUID orderId, OrderItem item) {
        Order order = placeOrderService.getOrderById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("ORDER_NOT_FOUND");
        }

        if ("ENTREGUE".equalsIgnoreCase(order.getStatus())) {
            throw new IllegalArgumentException("ORDER_ALREADY_CLOSED");
        }

        validateItem(item);
        order.addItem(item);
    }

    private void validateItem(OrderItem item) {
        if (item == null
                || item.getQuantity() <= 0
                || item.getUnitPrice() <= 0
                || item.getName() == null
                || item.getName().isBlank()) {
            throw new IllegalArgumentException("INVALID_ITEM");
        }
    }

    public Order getOrder(UUID orderId) {
        return placeOrderService.getOrderById(orderId);
    }
}
