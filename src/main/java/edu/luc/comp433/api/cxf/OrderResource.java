package edu.luc.comp433.api.cxf;

import edu.luc.comp433.api.payload.OrderRepresentation;
import edu.luc.comp433.api.payload.OrderRequest;
import edu.luc.comp433.api.workflow.OrderActivity;
import edu.luc.comp433.api.ws.OrderWebService;

import javax.ws.rs.*;
import java.util.List;

public class OrderResource implements OrderWebService {

    private OrderActivity orderActivity;

    public OrderResource(OrderActivity orderActivity) {
        this.orderActivity = orderActivity;
    }

    @Override
    @GET
    @Path(value = "/order/{id}")
    @Produces("application/hal+json")
    public OrderRepresentation getOrder(@PathParam("id") Long id) {
        return orderActivity.getOrder(id);
    }

    @Override
    @POST
    @Path(value = "/orders")
    @Consumes({"application/json"})
    @Produces("application/hal+json")
    public OrderRepresentation createOrder(OrderRequest orderRequest) {
        return orderActivity.createOrder(orderRequest);
    }

    @Override
    @PUT
    @Path(value = "/order/{id}/cancel")
    @Produces("application/hal+json")
    public OrderRepresentation cancel(@PathParam("id") Long id) {
        return orderActivity.cancelOrder(id);
    }

    @Override
    @PUT
    @Path(value = "/order/{id}/ship")
    @Produces("application/hal+json")
    public OrderRepresentation ship(@PathParam("id") Long id) {

        return orderActivity.shipOrder(id);
    }

    @Override
    @PUT
    @Path(value = "/order/{id}/deliver")
    @Produces("application/hal+json")
    public OrderRepresentation deliver(@PathParam("id") Long id) {
        return orderActivity.deliverOrder(id);
    }

    @Override
    @GET
    @Path(value = "/orders")
    @Produces("application/hal+json")
    public List<OrderRepresentation> allOrders() {
        return orderActivity.list();
    }
}
