package br.ifsp.ordersys.domain.entity;

import br.ifsp.ordersys.domain.valueobject.Money;

public class OrderItem {
    private final String name;
    private final int unitPrice;
    private final int quantity;

    public OrderItem(String name, int unitPrice, int quantity) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public Money total() {
        return Money.of(unitPrice * quantity);
    }

    public String getName() { return name; }
    public int getUnitPrice() { return unitPrice; }
    public int getQuantity() { return quantity; }
}
