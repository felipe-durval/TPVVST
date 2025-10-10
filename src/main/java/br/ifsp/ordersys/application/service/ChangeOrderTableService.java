package br.ifsp.ordersys.application.service;

import br.ifsp.ordersys.domain.aggregate.Order;
import br.ifsp.ordersys.domain.valueobject.Table;

import java.util.UUID;

public class ChangeOrderTableService {

    private final PlaceOrderService placeOrderService;

    public ChangeOrderTableService(PlaceOrderService placeOrderService) {
        this.placeOrderService = placeOrderService;
    }

    public void changeTable(UUID orderId, Table newTable) {
        if (newTable == null || newTable.getId() == null || newTable.getId().isBlank()) {
            throw new IllegalArgumentException("INVALID_TABLE");
        }


        boolean tableOccupied = placeOrderService.getAllOrders().stream()
                .anyMatch(o -> o.getTable().getId().equals(newTable.getId())
                        && !o.getId().equals(orderId)
                        && !o.getStatus().equals("CANCELED"));

        if (tableOccupied) {
            throw new IllegalStateException("TABLE_ALREADY_OCCUPIED");
        }

        Order order = placeOrderService.getAllOrders().stream()
                .filter(o -> o.getId().equals(orderId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("ORDER_NOT_FOUND"));

        order.setTable(newTable);
    }

    public Order getOrder(UUID orderId) {
        return placeOrderService.getAllOrders().stream()
                .filter(o -> o.getId().equals(orderId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("ORDER_NOT_FOUND"));
    }
}