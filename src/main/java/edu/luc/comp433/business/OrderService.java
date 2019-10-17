package edu.luc.comp433.business;

import edu.luc.comp433.business.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    OrderDTO getOrder(Long id);

    OrderDTO createOrder(OrderDTO toDTO);

    List<OrderDTO> list();

    OrderDTO cancelOrder(Long id);

    OrderDTO ship(Long id);

    OrderDTO deliverOrder(Long id);

    //  void createOrder(Order order) throws QuantityNotSufficientException;

}
