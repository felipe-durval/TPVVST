package br.ifsp.ordersys.domain.valueobject;

import java.util.Objects;

public final class CustomerId {
    private final String value;

    public CustomerId(String value) {
        this.value = Objects.requireNonNull(value, "value");
    }

    public String getValue() { return value; }
}