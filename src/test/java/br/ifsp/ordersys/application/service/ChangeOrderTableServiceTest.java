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


        Table invalidTable = new Table("");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> changeService.changeTable(order.getId(), invalidTable)
        );

        assertEquals("INVALID_TABLE", exception.getMessage());
    }
    @Test
    void shouldRejectChangeToOccupiedTable() {
        // mesa original
        Table table04 = new Table("mesa-04");
        CustomerId customer1 = new CustomerId(table04.getId());
        List<OrderItem> items1 = List.of(new OrderItem("Pizza", 30, 1, true));

        // mesa já ocupada
        Table table02 = new Table("mesa-02");
        CustomerId customer2 = new CustomerId(table02.getId());
        List<OrderItem> items2 = List.of(new OrderItem("Lasanha", 35, 1, true));

        PlaceOrderService placeService = new PlaceOrderService();
        Order pedido103 = placeService.createOrder(customer1, table04, items1);
        placeService.createOrder(customer2, table02, items2); // mesa ocupada

        ChangeOrderTableService changeService = new ChangeOrderTableService(placeService);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> changeService.changeTable(pedido103.getId(), table02)
        );

        assertEquals("TABLE_ALREADY_OCCUPIED", exception.getMessage());
    }

}
