package br.ifsp.ordersys.domain.valueobject;

import java.util.Objects;

public final class Table {
    private final String id;

    public Table(String id) {
        this.id = Objects.requireNonNull(id, "id");
    }

    public String getId() { return id; }
}
