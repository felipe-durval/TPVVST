package br.ifsp.ordersys.infrastructure.order;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    private UUID id;

}
