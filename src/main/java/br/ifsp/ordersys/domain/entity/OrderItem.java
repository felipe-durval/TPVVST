package br.ifsp.ordersys.domain.entity;



public class OrderItem {
    private String name;
    private int price;
    private int quantity;

    public OrderItem(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
