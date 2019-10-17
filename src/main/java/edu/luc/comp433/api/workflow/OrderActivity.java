package edu.luc.comp433.api.workflow;

import edu.luc.comp433.api.payload.OrderRepresentation;
import edu.luc.comp433.api.payload.OrderRequest;
import edu.luc.comp433.business.OrderService;
import edu.luc.comp433.business.dto.OrderDTO;
import edu.luc.comp433.exceptions.EntryNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderActivity {

    private OrderService orderService;

    public OrderActivity(OrderService orderService) {
        this.orderService = orderService;
    }

    public OrderRepresentation getOrder(Long id) {
        OrderDTO dto = orderService.getOrder(id);
        if (dto == null) throw new EntryNotFoundException("Order not found with this id:" + id);
        return OrderRepresentation.of(dto);
    }

    public OrderRepresentation createOrder(OrderRequest orderRequest) {
        OrderDTO dto = orderService.createOrder(orderRequest.toDTO());
        return OrderRepresentation.of(dto);
    }

    public OrderRepresentation cancelOrder(Long id) {
        OrderDTO dto = orderService.cancelOrder(id);
        return OrderRepresentation.of(dto);
    }

    public OrderRepresentation shipOrder(Long id) {
        OrderDTO dto = orderService.ship(id);
        return OrderRepresentation.of(dto);
    }

    public OrderRepresentation deliverOrder(Long id) {
        OrderDTO dto = orderService.deliverOrder(id);
        return OrderRepresentation.of(dto);
    }

    public List<OrderRepresentation> list() {
        List<OrderDTO> list = orderService.list();
        List<OrderRepresentation> result = new ArrayList<>();
        list.forEach(dto -> result.add(OrderRepresentation.of(dto)));
        return result;
    }
}
