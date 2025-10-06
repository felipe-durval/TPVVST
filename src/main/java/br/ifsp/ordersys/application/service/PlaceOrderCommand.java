package br.ifsp.ordersys.application.service;

import java.math.BigDecimal;
import java.util.List;

public record PlaceOrderCommand(int tableNumber, List<Item> items) {
    public record Item(String productName, int quantity, BigDecimal unitPrice) {}
}
