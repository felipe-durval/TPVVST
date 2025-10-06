package br.ifsp.ordersys.application.service;

import br.ifsp.ordersys.domain.aggregate.Order;
import br.ifsp.ordersys.domain.entity.OrderItem;
import br.ifsp.ordersys.domain.valueobject.CustomerId;
import br.ifsp.ordersys.domain.valueobject.Table;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddItemToOrderServiceTest {
    @Test
    void shouldAddValidItemToExistingOrder() {
        // dado que existe um pedido aberto
        Table table = new Table("mesa-10");
        CustomerId customerId = new CustomerId(table.getId());

        List<OrderItem> initialItems = List.of(
                new OrderItem("Suco", 10, 1, true)
        );

        PlaceOrderService placeService = new PlaceOrderService();
        Order existingOrder = placeService.createOrder(customerId, table, initialItems);

        AddItemToOrderService addService = new AddItemToOrderService(placeService);

        // quando o garçom adiciona Lasanha
        OrderItem lasanha = new OrderItem("Lasanha", 35, 1, true);

        addService.addItem(existingOrder.getId(), lasanha);

        // então o item é adicionado
        Order updatedOrder = addService.getOrder(existingOrder.getId());
        assertTrue(
                updatedOrder.getItems().stream()
                        .anyMatch(i -> i.getName().equals("Lasanha"))
        );
    }
}
