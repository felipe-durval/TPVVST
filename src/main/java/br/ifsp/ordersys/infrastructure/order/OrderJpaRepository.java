package br.ifsp.ordersys.infrastructure.order;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {}
