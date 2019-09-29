package edu.luc.comp433.business;

import edu.luc.comp433.exceptions.QuantityNotSufficientException;
import edu.luc.comp433.model.Order;
import edu.luc.comp433.model.OrderStatus;

public interface OrderService {

    void createOrder(Order order) throws QuantityNotSufficientException;

    void updateStatus(Long id, OrderStatus status);

    void cancelOrder(Long id);
}
