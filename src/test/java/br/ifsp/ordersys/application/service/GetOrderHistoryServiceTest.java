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
class GetOrderHistoryServiceTest {

    @Test
    void shouldReturnSpecificOrderData() {
        Table table = new Table("mesa-07");
        CustomerId customerId = new CustomerId(table.getId());

        List<OrderItem> items = List.of(
                new OrderItem("Lasanha", 35, 1, true),
                new OrderItem("Suco", 10, 2, true)
        );

        PlaceOrderService placeService = new PlaceOrderService();
        Order created = placeService.createOrder(customerId, table, items);

        GetOrderHistoryService historyService = new GetOrderHistoryService(placeService);

        Order order = historyService.findOrderById(created.getId());

        assertNotNull(order);
        assertEquals(created.getId(), order.getId());
        assertEquals(2, order.getItems().size());
        assertEquals("Lasanha", order.getItems().get(0).getName());
        assertEquals(35, order.getItems().get(0).getUnitPrice());
    }
}
