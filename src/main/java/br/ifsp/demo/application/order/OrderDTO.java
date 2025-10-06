package br.ifsp.demo.application.order;


import java.math.BigDecimal;
import java.util.UUID;

public record OrderDTO(UUID id, int tableNumber, String status, BigDecimal total) {}
