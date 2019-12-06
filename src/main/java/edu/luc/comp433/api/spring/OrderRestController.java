package edu.luc.comp433.api.spring;

import edu.luc.comp433.api.payload.OrderRepresentation;
import edu.luc.comp433.api.payload.OrderRequest;
import edu.luc.comp433.api.workflow.OrderActivity;
import edu.luc.comp433.api.ws.OrderWebService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class OrderRestController implements OrderWebService {

    private OrderActivity orderActivity;

    public OrderRestController(OrderActivity orderActivity) {
        this.orderActivity = orderActivity;
    }

    @Override
    @GetMapping(value = "/order/{id}", produces = { "application/hal+json"})
    public OrderRepresentation getOrder(@PathVariable Long id) {
        return withLinks(orderActivity.getOrder(id));
    }

    @Override
    @PostMapping(value = "/orders", consumes = {"application/json"}, produces = {"application/hal+json"})
    public OrderRepresentation createOrder(@RequestBody @Validated OrderRequest orderRequest) {
        return withLinks(orderActivity.createOrder(orderRequest));
    }

    @Override
    @PutMapping(value = "/order/{id}/cancel", produces = { "application/hal+json"})
    public OrderRepresentation cancel(@PathVariable Long id) {
        return withLinks(orderActivity.cancelOrder(id));
    }

    @Override
    @PutMapping(value = "/order/{id}/ship", produces = {"application/hal+json"})
    public OrderRepresentation ship(@PathVariable Long id) {

        return withLinks(orderActivity.shipOrder(id));
    }

    @Override
    @PutMapping(value = "/order/{id}/deliver", produces = {"application/hal+json"})
    public OrderRepresentation deliver(@PathVariable Long id) {
        return withLinks(orderActivity.deliverOrder(id));
    }

    @Override
    @GetMapping(value = "/orders", produces = {"application/hal+json"})
    public List<OrderRepresentation> allOrders() {
        return withLinks(orderActivity.list());
    }

    protected OrderRepresentation withLinks(OrderRepresentation order) {
        order.add(linkTo(methodOn(OrderRestController.class).getOrder(order.getId())).withSelfRel());
        order.add(linkTo(methodOn(CustomerRestController.class).getCustomer(order.getCustomer().getId())).withRel("customer"));
        order.add(linkTo(methodOn(OrderRestController.class).ship(order.getId())).withRel("ship"));
        order.add(linkTo(methodOn(OrderRestController.class).cancel(order.getId())).withRel("cancel"));
        order.add(linkTo(methodOn(OrderRestController.class).deliver(order.getId())).withRel("deliver"));
        order.add(linkTo(methodOn(OrderRestController.class).allOrders()).withRel("all"));
        order.getItems().forEach(i -> {
            i.add(linkTo(methodOn(ProductRestController.class).getProduct(i.getProduct().getId())).withRel("product"));
        });
        return order;
    }

    protected List<OrderRepresentation> withLinks(List<OrderRepresentation> list) {
        list.forEach(this::withLinks);
        return list;
    }
}
