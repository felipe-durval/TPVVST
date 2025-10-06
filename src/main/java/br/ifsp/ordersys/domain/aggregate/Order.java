package br.ifsp.ordersys.domain.aggregate;

import br.ifsp.ordersys.domain.entity.OrderItem;
import br.ifsp.ordersys.domain.valueobject.Money;
import br.ifsp.ordersys.domain.valueobject.Table;

import java.util.List;
import java.util.UUID;

public class Order {

    private final UUID id;
    private final String customerId;
    private final Table table;
    private final List<OrderItem> items;
    private final String status;
    private final Money total;

    public Order(String customerId, Table table, List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("EMPTY_ORDER");
        }
        boolean hasUnavailable = items.stream().anyMatch(i -> !i.isAvailable());
        if (hasUnavailable) {
            throw new IllegalArgumentException("ITEM_UNAVAILABLE");
        }
        this.id = UUID.randomUUID();
        this.customerId = customerId;
        this.table = table;
        this.items = items;
        this.status = "RECEBIDO";
        this.total = items.stream()
                .map(OrderItem::total)
                .reduce(Money.zero(), Money::add);
    }

    public UUID getId() { return id; }
    public String getCustomerId() { return customerId; }
    public Table getTable() { return table; }
    public List<OrderItem> getItems() { return items; }
    public String getStatus() { return status; }
    public Money getTotal() { return total; }
}
