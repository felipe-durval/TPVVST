package br.ifsp.demo.application.service;

import java.math.BigDecimal;
import java.util.List;

public record CreateOrderCommand(int tableNumber, List<Item> items) {
    public record Item(String productName, int quantity, BigDecimal unitPrice) {}
}
