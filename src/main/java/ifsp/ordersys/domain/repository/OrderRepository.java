package ifsp.ordersys.domain.repository;

import ifsp.ordersys.domain.model.aggregate.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    void save(Order order);

    Optional<Order> findById(String orderId);

    List<Order> findAll();

    void delete(String orderId);
}
