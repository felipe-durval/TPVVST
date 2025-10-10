package br.ifsp.ordersys.application.service;

import br.ifsp.ordersys.domain.aggregate.Order;

import java.util.Objects;

public class UpdateOrderStatusService {

    public void updateStatus(Order order, String newStatus) {
        Objects.requireNonNull(order, "order");
        Objects.requireNonNull(newStatus, "newStatus");

        order.setStatus(newStatus);
    }
}
