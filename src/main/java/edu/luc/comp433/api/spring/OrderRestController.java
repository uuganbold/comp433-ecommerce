package edu.luc.comp433.api.spring;

import edu.luc.comp433.api.payload.OrderRepresentation;
import edu.luc.comp433.api.payload.OrderRequest;
import edu.luc.comp433.api.workflow.OrderActivity;
import edu.luc.comp433.api.ws.OrderWebService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderRestController implements OrderWebService {

    private OrderActivity orderActivity;

    public OrderRestController(OrderActivity orderActivity) {
        this.orderActivity = orderActivity;
    }

    @Override
    @GetMapping(value = "/order/{id}", produces = {"text/xml", "application/json"})
    public OrderRepresentation getOrder(@PathVariable Long id) {
        return orderActivity.getOrder(id);
    }

    @Override
    @PostMapping(value = "/orders", consumes = {"text/xml", "application/json"}, produces = {"text/xml", "application/json"})
    public OrderRepresentation createOrder(@RequestBody @Validated OrderRequest orderRequest) {
        return orderActivity.createOrder(orderRequest);
    }

    @Override
    @PutMapping(value = "/order/{id}/cancel", produces = {"text/xml", "application/json"})
    public OrderRepresentation cancel(@PathVariable Long id) {
        return orderActivity.cancelOrder(id);
    }

    @Override
    @PutMapping(value = "/order/{id}/ship", produces = {"text/xml", "application/json"})
    public OrderRepresentation ship(@PathVariable Long id) {

        return orderActivity.shipOrder(id);
    }

    @Override
    @PutMapping(value = "/order/{id}/deliver", produces = {"text/xml", "application/json"})
    public OrderRepresentation deliver(@PathVariable Long id) {
        return orderActivity.deliverOrder(id);
    }

    @Override
    @GetMapping(value = "/orders", produces = {"text/xml", "application/json"})
    public List<OrderRepresentation> allOrders() {
        return orderActivity.list();
    }
}
