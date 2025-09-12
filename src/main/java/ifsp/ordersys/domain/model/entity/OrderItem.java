package ifsp.ordersys.domain.model.entity;

import ifsp.ordersys.domain.model.valueobject.Money;

import java.util.Objects;

public class OrderItem {

    private final String name;
    private final Money price;
    private final int quantity;

    public OrderItem(String name, Money price, int quantity) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("O nome do item não pode ser vazio.");
        }
        if (price == null) {throw new IllegalArgumentException("O preço do item não pode ser nulo.");
        }
        if (quantity < 1) {
            throw new IllegalArgumentException("A quantidade deve ser pelo menos 1.");
        }

        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public Money getTotalPrice() {
        return price.multiply(quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;
        OrderItem that = (OrderItem) o;
        return quantity == that.quantity &&
                Objects.equals(name, that.name) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, quantity);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
