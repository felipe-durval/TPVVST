package br.ifsp.ordersys.application.service;

import org.junit.jupiter.api.Test;
import ordersys.domain.model.Order;
import ordersys.domain.model.OrderItem;
import ordersys.domain.model.CustomerId;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@UnitTest
@TDD
public class PlaceOrderServiceTest {

    @Test
    public void shouldCreateOrderWithValidItems() {
        CustomerId customerId = new CustomerId("mesa-12");


        List<OrderItem> items = List.of(
                new OrderItem("Pizza", 30, 1),
                new OrderItem("Suco", 10, 1)
        );

        PlaceOrderService service = new PlaceOrderService();

        Order order = service.createOrder(customerId, items);

        assertNotNull(order);
        assertEquals("RECEBIDO", order.getStatus());

        // E o valor total exibido é R$40
        assertEquals(40, order.getTotal().getValue());
    }
}