package br.ifsp.ordersys.application.service;

import br.ifsp.ordersys.domain.aggregate.Order;
import br.ifsp.ordersys.domain.entity.OrderItem;
import br.ifsp.ordersys.domain.valueobject.CustomerId;
import br.ifsp.ordersys.domain.valueobject.Table;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.UUID;


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

    @Test
    void shouldRejectInvalidItemWhenAddingToOrder() {

        Table table = new Table("mesa-15");
        CustomerId customerId = new CustomerId(table.getId());

        List<OrderItem> initialItems = List.of(
                new OrderItem("Suco", 10, 1, true)
        );

        PlaceOrderService placeService = new PlaceOrderService();
        Order existingOrder = placeService.createOrder(customerId, table, initialItems);

        AddItemToOrderService addService = new AddItemToOrderService(placeService);


        OrderItem invalidItem1 = new OrderItem("Água", 5, 0, true);
        OrderItem invalidItem2 = new OrderItem("Refri", -10, 1, true);

        IllegalArgumentException exception1 = assertThrows(
                IllegalArgumentException.class,
                () -> addService.addItem(existingOrder.getId(), invalidItem1)
        );
        assertEquals("INVALID_ITEM", exception1.getMessage());

        IllegalArgumentException exception2 = assertThrows(
                IllegalArgumentException.class,
                () -> addService.addItem(existingOrder.getId(), invalidItem2)
        );
        assertEquals("INVALID_ITEM", exception2.getMessage());


    }
    @Test
    void shouldRejectAddingItemToNonexistentOrder() {
        // Dado que o garçom informa um número de pedido inexistente
        Table table = new Table("mesa-20");
        CustomerId customerId = new CustomerId(table.getId());

        PlaceOrderService placeService = new PlaceOrderService();
        AddItemToOrderService addService = new AddItemToOrderService(placeService);

        UUID nonexistentOrderId = UUID.randomUUID();

        OrderItem lasanha = new OrderItem("Lasanha", 35, 1, true);


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> addService.addItem(nonexistentOrderId, lasanha)
        );

        assertEquals("ORDER_NOT_FOUND", exception.getMessage());
    }

    @Test
    void shouldRejectAddingItemToDeliveredOrder() {
        // Dado que existe um pedido no status ENTREGUE
        Table table = new Table("mesa-25");
        CustomerId customerId = new CustomerId(table.getId());

        List<OrderItem> initialItems = List.of(
                new OrderItem("Pizza", 30, 1, true)
        );

        PlaceOrderService placeService = new PlaceOrderService();
        Order existingOrder = placeService.createOrder(customerId, table, initialItems);

        // Simula mudança de status do pedido para ENTREGUE
        existingOrder.setStatus("ENTREGUE");

        AddItemToOrderService addService = new AddItemToOrderService(placeService);

        OrderItem lasanha = new OrderItem("Lasanha", 35, 1, true);

        // Quando tenta adicionar novo item
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> addService.addItem(existingOrder.getId(), lasanha)
        );

        // Então o sistema rejeita com ORDER_ALREADY_CLOSED
        assertEquals("ORDER_ALREADY_CLOSED", exception.getMessage());
    }

}
