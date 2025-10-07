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
class CancelOrderServiceTest {

    @Test
    void shouldCancelOrderWhenNotDelivered() {
        Table table = new Table("mesa-11");
        CustomerId customerId = new CustomerId(table.getId());

        List<OrderItem> items = List.of(
                new OrderItem("Lasanha", 35, 1, true)
        );

        PlaceOrderService placeService = new PlaceOrderService();
        Order order = placeService.createOrder(customerId, table, items);

        CancelOrderService cancelService = new CancelOrderService(placeService);

        cancelService.cancelOrder(order.getId());

        Order canceled = cancelService.getOrder(order.getId());
        assertEquals("CANCELED", canceled.getStatus());
    }
}
