package edu.luc.comp433.api.cxf;

import edu.luc.comp433.api.payload.*;
import edu.luc.comp433.api.workflow.CustomerActivity;
import edu.luc.comp433.api.ws.CustomerWebService;
import org.apache.cxf.jaxrs.ext.ResponseStatus;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

public class CustomerResource implements CustomerWebService {

    private CustomerActivity customerActivity;

    public CustomerResource(CustomerActivity customerActivity) {
        this.customerActivity = customerActivity;
    }

    @Override
    @GET
    @Path("/customer/{id}")
    @Produces("application/hal+json")
    public CustomerRepresentation getCustomer(@PathParam("id") long id) {
        return customerActivity.getCustomer(id);
    }

    @Override
    @POST
    @Path(value = "/customers")
    @Consumes({"application/json"})
    @Produces("application/hal+json")
    public CustomerRepresentation createCustomer(CustomerRequest customerRequest) {
            return customerActivity.createCustomer(customerRequest);
    }

    @Override
    @GET
    @Path(value = "/customers")
    @Produces("application/hal+json")
    public List<CustomerRepresentation> allCustomers() {
        return customerActivity.listCustomers();
    }

    @Override
    @PUT
    @Path(value = "/customer/{id}")
    @Consumes({"application/json"})
    @Produces("application/hal+json")
    public CustomerRepresentation updateCustomer(@PathParam("id") long id, CustomerRequest customerRequest) {
            return customerActivity.update(id, customerRequest);
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
            return customerActivity.addAddress(id, addressRequest);
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
            return customerActivity.getAddresses(id);
    }


    @Override
    @POST
    @Path(value = "/customer/{id}/payments")
    @Consumes({"application/json"})
    @Produces("application/hal+json")
    public PaymentRepresentation addPayment(@PathParam("id") long id, PaymentRequest paymentRequest) {
            return customerActivity.addPayment(id, paymentRequest);
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
            return customerActivity.getPayments(id);
    }
}
