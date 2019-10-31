package edu.luc.comp433.api.cxf;

import edu.luc.comp433.api.cxf.linkbuilder.LinkBuilder;
import edu.luc.comp433.api.payload.OrderRepresentation;
import edu.luc.comp433.api.payload.OrderRequest;
import edu.luc.comp433.api.workflow.OrderActivity;
import edu.luc.comp433.api.ws.OrderWebService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.util.List;

public class OrderResource implements OrderWebService {

    private OrderActivity orderActivity;

    @Context
    private UriInfo uriInfo;

    public OrderResource(OrderActivity orderActivity) {
        this.orderActivity = orderActivity;
    }

    @Override
    @GET
    @Path(value = "/order/{id}")
    @Produces("application/hal+json")
    public OrderRepresentation getOrder(@PathParam("id") Long id) {
        return withLinks(orderActivity.getOrder(id));
    }

    @Override
    @POST
    @Path(value = "/orders")
    @Consumes({"application/json"})
    @Produces("application/hal+json")
    public OrderRepresentation createOrder(OrderRequest orderRequest) {
        return withLinks(orderActivity.createOrder(orderRequest));
    }

    @Override
    @PUT
    @Path(value = "/order/{id}/cancel")
    @Produces("application/hal+json")
    public OrderRepresentation cancel(@PathParam("id") Long id) {
        return withLinks(orderActivity.cancelOrder(id));
    }

    @Override
    @PUT
    @Path(value = "/order/{id}/ship")
    @Produces("application/hal+json")
    public OrderRepresentation ship(@PathParam("id") Long id) {

        return withLinks(orderActivity.shipOrder(id));
    }

    @Override
    @PUT
    @Path(value = "/order/{id}/deliver")
    @Produces("application/hal+json")
    public OrderRepresentation deliver(@PathParam("id") Long id) {
        return withLinks(orderActivity.deliverOrder(id));
    }

    @Override
    @GET
    @Path(value = "/orders")
    @Produces("application/hal+json")
    public List<OrderRepresentation> allOrders() {
        return withLinks(orderActivity.list());
    }

    protected OrderRepresentation withLinks(OrderRepresentation order) {
        order.add(LinkBuilder.get(uriInfo).linkTo(OrderResource.class, "getOrder").withSelfRel().build(order.getId()));
        order.add(LinkBuilder.get(uriInfo).linkTo(CustomerResource.class, "getCustomer").withRel("customer").build(order.getCustomer().getId()));
        order.getItems().forEach(i -> {
            i.add(LinkBuilder.get(uriInfo).linkTo(ProductResource.class, "getProduct").withRel("product").build(i.getProduct().getId()));
        });
        return order;
    }

    protected List<OrderRepresentation> withLinks(List<OrderRepresentation> list) {
        list.forEach(this::withLinks);
        return list;
    }
}
