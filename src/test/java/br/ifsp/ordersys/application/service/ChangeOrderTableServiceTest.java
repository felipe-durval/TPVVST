package br.ifsp.ordersys.application.service;

import br.ifsp.ordersys.domain.aggregate.Order;
import br.ifsp.ordersys.domain.entity.OrderItem;
import br.ifsp.ordersys.domain.valueobject.CustomerId;
import br.ifsp.ordersys.domain.valueobject.Table;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("UnitTest")
@Tag("TDD")
class ChangeOrderTableServiceTest {

    @Test
    void shouldChangeOrderTableWhenNewTableIsFree() {
        Table oldTable = new Table("mesa-05");
        CustomerId customerId = new CustomerId(oldTable.getId());

        List<OrderItem> items = List.of(
                new OrderItem("Lasanha", 35, 1, true)
        );

        PlaceOrderService placeService = new PlaceOrderService();
        Order order = placeService.createOrder(customerId, oldTable, items);

        ChangeOrderTableService changeService = new ChangeOrderTableService(placeService);

        Table newTable = new Table("mesa-08");
        changeService.changeTable(order.getId(), newTable);

        Order updated = changeService.getOrder(order.getId());

        assertEquals("mesa-08", updated.getTable().getId());
    }

    @Test
    void shouldRejectChangeToInvalidTable() {
        Table oldTable = new Table("mesa-05");
        CustomerId customerId = new CustomerId(oldTable.getId());

        List<OrderItem> items = List.of(
                new OrderItem("Pizza", 30, 1, true)
        );

        PlaceOrderService placeService = new PlaceOrderService();
        Order order = placeService.createOrder(customerId, oldTable, items);

        ChangeOrderTableService changeService = new ChangeOrderTableService(placeService);

        // mesa inválida (não cadastrada)
        Table invalidTable = new Table("");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> changeService.changeTable(order.getId(), invalidTable)
        );

        assertEquals("INVALID_TABLE", exception.getMessage());
    }
}
