package edu.luc.comp433.api.ws;

import edu.luc.comp433.api.payload.OrderRepresentation;
import edu.luc.comp433.api.payload.OrderRequest;

import javax.jws.WebService;
import java.util.List;

@WebService
public interface OrderWebService {

    OrderRepresentation getOrder(Long id);

    OrderRepresentation createOrder(OrderRequest orderRequest);

    OrderRepresentation cancel(Long id);

    OrderRepresentation ship(Long id);

    OrderRepresentation deliver(Long id);

    List<OrderRepresentation> allOrders();
}
