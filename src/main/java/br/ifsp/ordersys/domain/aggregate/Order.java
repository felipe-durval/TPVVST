package br.ifsp.ordersys.domain.aggregate;

import br.ifsp.ordersys.domain.entity.OrderItem;
import br.ifsp.ordersys.domain.entity.OrderStatus;
import br.ifsp.ordersys.domain.valueobject.Money;
import br.ifsp.ordersys.domain.valueobject.Table;

import java.util.*;

public class Order {

    private final UUID id;
    private final String customerId;
    private final List<OrderItem> items;
    private Table table;
    private OrderStatus status;
    private Money total;

    public Order(String customerId, Table table, List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("EMPTY_ORDER");
        }

        boolean hasUnavailable = items.stream().anyMatch(i -> !i.isAvailable());
        if (hasUnavailable) {
            throw new IllegalArgumentException("ITEM_UNAVAILABLE");
        }

        boolean hasInvalidQuantity = items.stream().anyMatch(i -> i.getQuantity() <= 0);
        if (hasInvalidQuantity) {
            throw new IllegalArgumentException("INVALID_QUANTITY");
        }

        this.id = UUID.randomUUID();
        this.customerId = Objects.requireNonNull(customerId, "customerId");
        this.table = Objects.requireNonNull(table, "table");
        this.items = new ArrayList<>(items);
        this.status = OrderStatus.RECEBIDO;
        this.total = items.stream()
                .map(OrderItem::total)
                .reduce(Money.zero(), Money::add);
    }

    public void addItem(OrderItem item) {
        if (item == null
                || item.getQuantity() <= 0
                || item.getUnitPrice() <= 0
                || item.getName() == null
                || item.getName().isBlank()) {
            throw new IllegalArgumentException("INVALID_ITEM");
        }
        this.items.add(item);
        this.total = this.total.add(item.total());
    }

    public UUID getId() { return id; }
    public String getCustomerId() { return customerId; }
    public Table getTable() { return table; }
    public Money getTotal() { return total; }

    public String getStatus() { return toExternal(status); }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void setStatus(String newStatusExternal) {
        if (this.status == OrderStatus.ENTREGUE && "EM_PREPARO".equals(newStatusExternal)) {
            throw new IllegalStateException("INVALID_STATUS_CHANGE");
        }
        this.status = toInternal(newStatusExternal);
    }

    public void setTable(Table newTable) {
        this.table = Objects.requireNonNull(newTable, "newTable");
    }

    private static String toExternal(OrderStatus st) {
        return switch (st) {
            case RECEBIDO -> "RECEBIDO";
            case PREPARANDO -> "EM_PREPARO";
            case PRONTO -> "PRONTO";
            case ENTREGUE -> "ENTREGUE";
            case CANCELADO -> "CANCELADO";
        };
    }

    private static OrderStatus toInternal(String external) {
        if (external == null) throw new IllegalArgumentException("INVALID_STATUS");
        return switch (external) {
            case "RECEBIDO" -> OrderStatus.RECEBIDO;
            case "EM_PREPARO" -> OrderStatus.PREPARANDO;
            case "PRONTO" -> OrderStatus.PRONTO;
            case "ENTREGUE" -> OrderStatus.ENTREGUE;
            case "CANCELADO" -> OrderStatus.CANCELADO;
            default -> throw new IllegalArgumentException("INVALID_STATUS");
        };
    }
}
