package br.ifsp.ordersys.domain.aggregate;

import br.ifsp.ordersys.domain.valueobject.Money;
import br.ifsp.ordersys.domain.valueobject.CustomerId;
import br.ifsp.ordersys.domain.valueobject.Table;

public class Order {
    private String status;
    private Money total;
    private CustomerId customerId;
    private Table table;

    public Order(CustomerId customerId, Table table) {
        this.status = "RECEBIDO";
        this.total = new Money(40);
        this.customerId = customerId;
        this.table = table;
    }

    public Order() {

    }

    public String getStatus() {
        return status;
    }

    public Money getTotal() {
        return total;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public Table getTable() {
        return table;
    }
}
