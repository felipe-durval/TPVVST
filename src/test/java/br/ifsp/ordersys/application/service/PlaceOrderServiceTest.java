package br.ifsp.ordersys.application.service;


import br.ifsp.ordersys.domain.aggregate.Order;
import br.ifsp.ordersys.domain.entity.OrderItem;
import br.ifsp.ordersys.domain.valueobject.CustomerId;
import br.ifsp.ordersys.domain.valueobject.Table;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("UnitTest")
@Tag("TDD")
public class PlaceOrderServiceTest {
    //US-01 - SCENARIO 1
    @Test
    public void shouldCreateOrderWithValidItems() {

        Table table = new Table("mesa-12");
        CustomerId customerId = new CustomerId(table.getId());


        List<OrderItem> items = List.of(
                new OrderItem("Pizza", 30, 1),
                new OrderItem("Suco", 10, 1)
        );

        PlaceOrderService service = new PlaceOrderService();

        Order order = service.createOrder(customerId, table, items);

        assertNotNull(order);
        assertEquals("RECEBIDO", order.getStatus());


        assertEquals(0, order.getTotal().getValue().compareTo(BigDecimal.valueOf(40)));
    }

    //US-01 - SCENARIO 2
    @Test
    void shouldRejectEmptyOrder() {
        Table table = new Table("mesa-08");
        CustomerId customerId = new CustomerId(table.getId());
        List<OrderItem> items = List.of();

        PlaceOrderService service = new PlaceOrderService();

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                service.createOrder(customerId, table, items)
        );

        assertEquals("EMPTY_ORDER", exception.getMessage());
    }

    //US-01 - SCENARIO 3
    @Test
    void shouldRejectOrderWithUnavailableItem() {
        Table table = new Table("mesa-02");
        CustomerId customerId = new CustomerId(table.getId());

        OrderItem unavailableItem = new OrderItem("Lasanha", 25, 1);
        List<OrderItem> items = List.of(unavailableItem);

        PlaceOrderService service = new PlaceOrderService();

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                service.createOrder(customerId, table, items)
        );

        assertEquals("ITEM_UNAVAILABLE", exception.getMessage());
    }

}
