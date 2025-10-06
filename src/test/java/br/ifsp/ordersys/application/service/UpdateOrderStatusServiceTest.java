package br.ifsp.ordersys.application.service;

import br.ifsp.ordersys.domain.aggregate.Order;
import br.ifsp.ordersys.domain.entity.OrderItem;
import br.ifsp.ordersys.domain.valueobject.Table;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateOrderStatusServiceTest {

    @Test
    void shouldUpdateOrderStatusFromReceivedToInPreparation() {
        // Dado que o pedido está no status RECEBIDO
        Table table = new Table("mesa-01");
        Order order = new Order("customer-01", table, List.of(
                new OrderItem("Pizza", 30, 1, true)
        ));
        assertEquals("RECEBIDO", order.getStatus());

        // Quando o cozinheiro altera o status para EM_PREPARO
        UpdateOrderStatusService service = new UpdateOrderStatusService();
        service.updateStatus(order, "EM_PREPARO");

        // Então o pedido é atualizado corretamente
        assertEquals("EM_PREPARO", order.getStatus());
    }
}