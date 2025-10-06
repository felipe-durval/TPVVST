package br.ifsp.demo.infrastructure.order;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "order_items")
public class OrderItemEntity {
    @Id
    @GeneratedValue
    private UUID id;

}
