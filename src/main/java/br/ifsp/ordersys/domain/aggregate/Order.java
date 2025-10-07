package br.ifsp.ordersys.domain.aggregate;

import br.ifsp.ordersys.domain.entity.OrderItem;
import br.ifsp.ordersys.domain.valueobject.Money;
import br.ifsp.ordersys.domain.valueobject.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Order {

    private final UUID id;
    private final String customerId;
    private final List<OrderItem> items;
    private Table table;
    private String status;
    private Money total;

    public Order(String customerId, Table table, List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("EMPTY_ORDER");
        }

        this.id = UUID.randomUUID();
        this.customerId = customerId;
        this.table = table;


        this.items = new ArrayList<>(items);

        this.status = "RECEBIDO";
        this.total = items.stream()
                .map(OrderItem::total)
                .reduce(Money.zero(), Money::add);
    }

    public void addItem(OrderItem item) {
        if (item.getQuantity() <= 0 || item.getUnitPrice() <= 0) {
            throw new IllegalArgumentException("INVALID_ITEM");
        }

        this.items.add(item);
        this.total = this.total.add(item.total());
    }



    public UUID getId() { return id; }
    public String getCustomerId() { return customerId; }
    public Table getTable() { return table; }
    public String getStatus() { return status; }
    public Money getTotal() { return total; }
    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setTable(Table newTable) {
        this.table = newTable;
    }

}
