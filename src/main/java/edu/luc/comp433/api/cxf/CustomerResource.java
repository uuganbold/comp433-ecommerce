package edu.luc.comp433.api.cxf;

import edu.luc.comp433.api.cxf.linkbuilder.LinkBuilder;
import edu.luc.comp433.api.payload.*;
import edu.luc.comp433.api.workflow.CustomerActivity;
import edu.luc.comp433.api.ws.CustomerWebService;
import org.apache.cxf.jaxrs.ext.ResponseStatus;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@CrossOriginResourceSharing(
        allowAllOrigins = true,
        maxAge = 200
)
public class CustomerResource implements CustomerWebService {

    private CustomerActivity customerActivity;

    @Context
    private UriInfo uriInfo;

    public CustomerResource(CustomerActivity customerActivity) {
        this.customerActivity = customerActivity;
    }

    @Override
    @GET
    @Path("/customer/{id}")
    @Produces("application/hal+json")
    public CustomerRepresentation getCustomer(@PathParam("id") long id) {
        return withLinks(customerActivity.getCustomer(id));
    }

    @Override
    @POST
    @Path(value = "/customers")
    @Consumes({"application/json"})
    @Produces("application/hal+json")
    public CustomerRepresentation createCustomer(CustomerRequest customerRequest) {
        return withLinks(customerActivity.createCustomer(customerRequest));
    }

    @Override
    @GET
    @Path(value = "/customers")
    @Produces("application/hal+json")
    public List<CustomerRepresentation> allCustomers() {
        return withLinks(customerActivity.listCustomers());
    }

    @Override
    @PUT
    @Path(value = "/customer/{id}")
    @Consumes({"application/json"})
    @Produces("application/hal+json")
    public CustomerRepresentation updateCustomer(@PathParam("id") long id, CustomerRequest customerRequest) {
        return withLinks(customerActivity.update(id, customerRequest));
    }

    @Override
    @DELETE
    @Path("/customer/{id}")
    @ResponseStatus(Response.Status.NO_CONTENT)
    public void deleteCustomer(@PathParam("id") long id) {
        customerActivity.delete(id);
    }

    @Override
    @POST
    @Path(value = "/customer/{id}/addresses")
    @Consumes({"application/json"})
    @Produces("application/hal+json")
    public AddressRepresentation addAddress(@PathParam("id") long id, AddressRequest addressRequest) {
        return withLinks(id, customerActivity.addAddress(id, addressRequest));
    }

    @Override
    @DELETE
    @Path("/customer/{id}/address/{addressId}")
    @ResponseStatus(Response.Status.NO_CONTENT)
    public void removeAddress(@PathParam("id") long id, @PathParam("addressId") long addressId) {
        customerActivity.removeAddress(id, addressId);
    }

    @Override
    @GET
    @Path(value = "/customer/{id}/addresses")
    @Produces("application/hal+json")
    public List<AddressRepresentation> getAddresses(@PathParam("id") long id) {
        return withLinksAddresses(id, customerActivity.getAddresses(id));
    }


    @Override
    @POST
    @Path(value = "/customer/{id}/payments")
    @Consumes({"application/json"})
    @Produces("application/hal+json")
    public PaymentRepresentation addPayment(@PathParam("id") long id, PaymentRequest paymentRequest) {
        return withLinks(id, customerActivity.addPayment(id, paymentRequest));
    }

    @Override
    @DELETE
    @Path("/customer/{id}/payment/{paymentId}")
    @ResponseStatus(Response.Status.NO_CONTENT)
    public void removePayment(@PathParam("id") long id, @PathParam("paymentId") long paymentId) {
        customerActivity.removePayment(id, paymentId);
    }

    @Override
    @GET
    @Path(value = "/customer/{id}/payments")
    @Produces("application/hal+json")
    public List<PaymentRepresentation> getPayments(@PathParam("id") long id) {
        return withLinksPayments(id, customerActivity.getPayments(id));
    }

    protected CustomerRepresentation withLinks(CustomerRepresentation customer) {
        customer.add(LinkBuilder.get(uriInfo).linkTo(CustomerResource.class, "getCustomer").withSelfRel().build(customer.getId()));
        customer.add(LinkBuilder.get(uriInfo).linkTo(CustomerResource.class, "allCustomers").withRel("all").build());
        customer.add(LinkBuilder.get(uriInfo).linkTo(CustomerResource.class, "getAddresses").withRel("addresses").build(customer.getId()));
        customer.add(LinkBuilder.get(uriInfo).linkTo(CustomerResource.class, "getPayments").withRel("payments").build(customer.getId()));
        return customer;
    }

    protected List<CustomerRepresentation> withLinks(List<CustomerRepresentation> customers) {
        customers.forEach(this::withLinks);
        return customers;
    }

    protected AddressRepresentation withLinks(long customerId, AddressRepresentation address) {
        address.add(LinkBuilder.get(uriInfo).linkTo(CustomerResource.class, "getCustomer").withRel("customer").build(customerId));
        address.add(LinkBuilder.get(uriInfo).linkTo(CustomerResource.class, "getAddresses").withRel("all").build(customerId));
        return address;
    }

    protected List<AddressRepresentation> withLinksAddresses(long id, List<AddressRepresentation> addresses) {
        addresses.forEach(a -> withLinks(id, a));
        return addresses;
    }

    protected PaymentRepresentation withLinks(long customerId, PaymentRepresentation payment) {
        payment.add(LinkBuilder.get(uriInfo).linkTo(CustomerResource.class, "getCustomer").withRel("customer").build(customerId));
        payment.add(LinkBuilder.get(uriInfo).linkTo(CustomerResource.class, "getPayments").withRel("all").build(customerId));
        return payment;
    }

    protected List<PaymentRepresentation> withLinksPayments(long id, List<PaymentRepresentation> payments) {
        payments.forEach(p -> withLinks(id, p));
        return payments;
    }
}
