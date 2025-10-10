package br.ifsp.ordersys.application.service;

import br.ifsp.ordersys.domain.aggregate.Order;
import br.ifsp.ordersys.domain.entity.OrderItem;
import br.ifsp.ordersys.domain.valueobject.CustomerId;
import br.ifsp.ordersys.domain.valueobject.Table;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PlaceOrderService {

    private final Map<String, List<Order>> ordersByTable = new ConcurrentHashMap<>();
    private final Map<UUID, Order> indexById = new ConcurrentHashMap<>();

    public Order createOrder(CustomerId customerId, Table table, List<OrderItem> items) {
        Objects.requireNonNull(customerId, "customerId");
        Objects.requireNonNull(table, "table");
        Objects.requireNonNull(items, "items");

        Order order = new Order(customerId.getValue(), table, items);
        ordersByTable.computeIfAbsent(table.getId(), k -> new ArrayList<>()).add(order);
        indexById.put(order.getId(), order);
        return order;
    }

    public List<Order> getOrdersForTable(String tableId) {
        return Collections.unmodifiableList(ordersByTable.getOrDefault(tableId, List.of()));
    }

    public Order getOrderById(UUID orderId) {
        return indexById.get(orderId);
    }

    public List<Order> getAllOrders() {
        return Collections.unmodifiableList(indexById.values().stream().toList());
    }
}
