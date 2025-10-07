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

    //US -04 - CENARIO 1
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

    //US-04 - CENARIO 2

    @Test
    void shouldReturnAllOrders() {
        Table table1 = new Table("mesa-01");
        CustomerId customer1 = new CustomerId(table1.getId());

        Table table2 = new Table("mesa-02");
        CustomerId customer2 = new CustomerId(table2.getId());

        PlaceOrderService placeService = new PlaceOrderService();

        List<OrderItem> pedido1 = List.of(new OrderItem("Pizza", 30, 1, true));
        List<OrderItem> pedido2 = List.of(new OrderItem("Suco", 10, 2, true));

        placeService.createOrder(customer1, table1, pedido1);
        placeService.createOrder(customer2, table2, pedido2);

        GetOrderHistoryService historyService = new GetOrderHistoryService(placeService);

        List<Order> allOrders = historyService.findAllOrders();

        assertEquals(2, allOrders.size());
        assertTrue(allOrders.stream().anyMatch(o ->
                o.getItems().stream().anyMatch(i -> i.getName().equals("Pizza"))
        ));
        assertTrue(allOrders.stream().anyMatch(o ->
                o.getItems().stream().anyMatch(i -> i.getName().equals("Suco"))
        ));
    }


}
