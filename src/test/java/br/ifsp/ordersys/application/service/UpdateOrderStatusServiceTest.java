package br.ifsp.ordersys.application.service;
import static org.junit.jupiter.api.Assertions.*;
import br.ifsp.ordersys.domain.aggregate.Order;
import br.ifsp.ordersys.domain.entity.OrderItem;
import br.ifsp.ordersys.domain.valueobject.Table;
import org.junit.jupiter.api.Test;

import java.util.List;



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

    @Test
    void shouldRejectStatusChangeForDeliveredOrder() {
        Table table = new Table("mesa-07");
        Order order = new Order("customer-07", table, List.of(
                new OrderItem("Pizza", 30, 1, true)
        ));

        // Simula que o pedido já foi entregue
        order.setStatus("ENTREGUE");

        UpdateOrderStatusService service = new UpdateOrderStatusService();

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> service.updateStatus(order, "EM_PREPARO")
        );

        assertEquals("INVALID_STATUS_CHANGE", exception.getMessage());
    }
}