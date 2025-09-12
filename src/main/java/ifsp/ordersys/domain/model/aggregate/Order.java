package ifsp.ordersys.domain.model.aggregate;

import ifsp.ordersys.domain.model.entity.OrderItem;
import ifsp.ordersys.domain.model.valueobject.CustomerId;
import ifsp.ordersys.domain.model.valueobject.Money;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {

    public enum Status {
        RECEBIDO,
        EM_PREPARO,
        PRONTO,
        ENTREGUE
    }

    private final String id;
    private final CustomerId customerId;
    private final List<OrderItem> items;
    private Money total;
    private Status status;
    private final List<String> specialRequests;

    public Order(String id, CustomerId customerId, List<OrderItem> items) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id do pedido não pode ser nulo ou vazio.");
        }
        if (customerId == null) {
            throw new IllegalArgumentException("CustomerId não pode ser nulo.");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Um pedido deve ter pelo menos um item.");
        }

        this.id = id;
        this.customerId = customerId;
        this.items = new ArrayList<>(items);
        this.total = calculateTotal();
        this.status = Status.RECEBIDO;
        this.specialRequests = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public Money getTotal() {
        return total;
    }

    public Status getStatus() {
        return status;
    }

    public List<String> getSpecialRequests() {
        return Collections.unmodifiableList(specialRequests);
    }

    private Money calculateTotal() {
        Money sum = new Money(0);
        for (OrderItem item : items) {
            sum = sum.add(item.getPrice().multiply(item.getQuantity()));
        }
        return sum;
    }

    // Alterar status do pedido seguindo fluxo válido
    public void changeStatus(Status newStatus) {
        if (!isValidStatusTransition(this.status, newStatus)) {
            throw new IllegalStateException("INVALID_STATUS_CHANGE");
        }
        this.status = newStatus;
    }

    private boolean isValidStatusTransition(Status current, Status next) {
        switch (current) {
            case RECEBIDO: return next == Status.EM_PREPARO;
            case EM_PREPARO: return next == Status.PRONTO;
            case PRONTO: return next == Status.ENTREGUE;
            default: return false;
        }
    }

    // Aplicar desconto (somente em pedidos não finalizados)
    public void applyDiscount(double percentage) {
        if (status == Status.ENTREGUE) {
            throw new IllegalStateException("DISCOUNT_NOT_ALLOWED");
        }
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Porcentagem de desconto inválida.");
        }
        double desconto = total.getValue().doubleValue() * (percentage / 100.0);
        this.total = new Money(total.getValue().doubleValue() - desconto);
    }

    public void addSpecialRequest(String request) {
        if (request == null || request.isBlank()) {
            throw new IllegalArgumentException("INVALID_SPECIAL_REQUEST");
        }
        this.specialRequests.add(request);
    }
}
