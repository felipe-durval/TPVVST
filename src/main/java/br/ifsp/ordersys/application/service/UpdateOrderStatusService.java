package br.ifsp.ordersys.application.service;

import br.ifsp.ordersys.domain.aggregate.Order;
import org.springframework.stereotype.Service;

@Service
public class UpdateOrderStatusService {

    public void updateStatus(Order order, String newStatus) {
        order.setStatus(newStatus);
    }
}