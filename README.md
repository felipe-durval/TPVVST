# TPVVST

MÓDULO: Gestão de Pedidos de Restaurante 

Aggregate Root (Pedido): Order – mantém a consistência do pedido e coordena as entidades internas.

Entidade Interna: OrderItem – representa cada prato ou bebida incluída no pedido.

Value Objects:

CustomerId – cliente associado ao pedido.

Money – representa valores monetários.

Repositório:

OrderRepository – repositorio de pedidos.

Serviços:

PlaceOrderService – registrar um novo pedido.

AddItemToOrderService – adicionar itens em pedidos.

ChangeOrderStatus – alterar status do pedido.

CalculateTotalService – calcular o valor total.

CancelOrderService – cancelar pedidos que ainda não foram entregues.
