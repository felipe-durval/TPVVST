package br.ifsp.ordersys.application.service;

import br.ifsp.ordersys.domain.aggregate.Order;
import br.ifsp.ordersys.domain.entity.OrderItem;

import java.util.UUID;

public class AddItemToOrderService {

    private final PlaceOrderService placeOrderService;

    public AddItemToOrderService(PlaceOrderService placeOrderService) {
        this.placeOrderService = placeOrderService;
    }

    public void addItem(UUID orderId, OrderItem item) {
        Order order = placeOrderService.getOrderById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("ORDER_NOT_FOUND");
        }
        order.addItem(item);
    }

    public Order getOrder(UUID orderId) {
        return placeOrderService.getOrderById(orderId);
    }
}
