package br.ifsp.ordersys.application.service;

import br.ifsp.ordersys.domain.aggregate.Order;
import br.ifsp.ordersys.domain.entity.OrderItem;
import br.ifsp.ordersys.domain.valueobject.CustomerId;
import br.ifsp.ordersys.domain.valueobject.Table;

import java.util.List;

public class PlaceOrderService {
    public Order createOrder(CustomerId customerId, Table table, List<OrderItem> items) {
        return new Order(customerId.getValue().toString(), table, items);
    }

}
