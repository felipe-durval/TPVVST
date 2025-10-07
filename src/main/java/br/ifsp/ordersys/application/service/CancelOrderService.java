package br.ifsp.ordersys.application.service;

import br.ifsp.ordersys.domain.aggregate.Order;
import java.util.UUID;

public class CancelOrderService {

    private final PlaceOrderService placeOrderService;

    public CancelOrderService(PlaceOrderService placeOrderService) {
        this.placeOrderService = placeOrderService;
    }

    public void cancelOrder(UUID orderId) {
        Order order = placeOrderService.getAllOrders().stream()
                .filter(o -> o.getId().equals(orderId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("ORDER_NOT_FOUND"));

        if (!order.getStatus().equals("ENTREGUE")) {
            order.setStatus("CANCELED");
        } else {
            throw new IllegalStateException("CANNOT_CANCEL_DELIVERED_ORDER");
        }
    }

    public Order getOrder(UUID orderId) {
        return placeOrderService.getAllOrders().stream()
                .filter(o -> o.getId().equals(orderId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("ORDER_NOT_FOUND"));
    }
}
