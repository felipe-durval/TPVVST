package br.ifsp.ordersys.application.service;

import br.ifsp.ordersys.domain.aggregate.Order;

import java.util.List;
import java.util.UUID;

public class GetOrderHistoryService {

    private final PlaceOrderService placeOrderService;

    public GetOrderHistoryService(PlaceOrderService placeOrderService) {
        this.placeOrderService = placeOrderService;
    }

    public Order findOrderById(UUID orderId) {
        List<Order> all = placeOrderService.getAllOrders(); // implementaremos isso abaixo
        return all.stream()
                .filter(o -> o.getId().equals(orderId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("ORDER_NOT_FOUND"));
    }

    public List<Order> findAllOrders() {
        return placeOrderService.getAllOrders();
    }

}
