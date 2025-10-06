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
        // implementação mínima — propositalmente vazia (RED)
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Order getOrder(UUID orderId) {
        // apenas placeholder para o teste compilar
        return null;
    }
}
