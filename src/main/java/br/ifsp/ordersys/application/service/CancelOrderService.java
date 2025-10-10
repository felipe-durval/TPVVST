package br.ifsp.ordersys.application.service;

import br.ifsp.ordersys.domain.aggregate.Order;
import java.util.UUID;

public class CancelOrderService {

    private final PlaceOrderService placeOrderService;

    public CancelOrderService(PlaceOrderService placeOrderService) {
        this.placeOrderService = placeOrderService;
    }

    public void cancelOrder(UUID orderId) {
        Order order = placeOrderService.getOrderById(orderId); // evita varrer lista
        if (order == null) {
            throw new IllegalArgumentException("ORDER_NOT_FOUND");
        }

        String status = order.getStatus();
        if ("ENTREGUE".equalsIgnoreCase(status)) {
            throw new IllegalStateException("CANNOT_CANCEL_DELIVERED_ORDER");
        }
        if ("CANCELADO".equalsIgnoreCase(status) || "CANCELED".equalsIgnoreCase(status)) {
            throw new IllegalStateException("ORDER_ALREADY_CANCELLED");
        }
        order.setStatus("CANCELADO");
    }



    public Order getOrder(UUID orderId) {
        Order order = placeOrderService.getOrderById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("ORDER_NOT_FOUND");
        }
        return order;
    }
}
