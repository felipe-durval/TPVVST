package br.ifsp.ordersys.application.service;

import br.ifsp.ordersys.domain.aggregate.Order;
import br.ifsp.ordersys.domain.entity.OrderItem;
import br.ifsp.ordersys.domain.valueobject.CustomerId;
import br.ifsp.ordersys.domain.valueobject.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaceOrderService {
    // armazenamento em memória (histórico)
    private final Map<String, List<Order>> ordersByTable = new HashMap<>();

    public Order createOrder(CustomerId customerId, Table table, List<OrderItem> items) {
        Order order = new Order(customerId.getValue(), table, items);
        ordersByTable.computeIfAbsent(table.getId(), k -> new ArrayList<>()).add(order);
        return order;
    }

    public List<Order> getOrdersForTable(String tableId) {
        return ordersByTable.getOrDefault(tableId, List.of());
    }
}
