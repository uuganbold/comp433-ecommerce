package edu.luc.comp433.api.cxf;

import edu.luc.comp433.api.payload.*;
import edu.luc.comp433.api.workflow.CustomerActivity;
import edu.luc.comp433.api.ws.CustomerWebService;
import edu.luc.comp433.exceptions.DuplicatedEntryException;
import edu.luc.comp433.exceptions.EntryNotFoundException;
import edu.luc.comp433.exceptions.NotRemovableException;
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
    public CustomerRepresentation getCustomer(@PathParam("id") long id) {
        CustomerRepresentation customer = customerActivity.getCustomer(id);
        if (customer == null) throw new NotFoundException("Customer Not Found");
        return customer;
    }

    @Override
    @POST
    @Path(value = "/customers")
    @Consumes({"text/xml", "application/json"})
    public CustomerRepresentation createCustomer(CustomerRequest customerRequest) {
        try {
            return customerActivity.createCustomer(customerRequest);
        } catch (DuplicatedEntryException dive) {
            throw new WebApplicationException(dive.getMessage(), Response.Status.CONFLICT);
        }
    }

    @Override
    @GET
    @Path(value = "/customers")
    @Produces({"text/xml", "application/json"})
    public List<CustomerRepresentation> allCustomers() {
        return customerActivity.listCustomers();
    }

    @Override
    @PUT
    @Path(value = "/customer/{id}")
    @Consumes({"text/xml", "application/json"})
    public CustomerRepresentation updateCustomer(@PathParam("id") long id, CustomerRequest customerRequest) {
        try {
            return customerActivity.update(id, customerRequest);
        } catch (EntryNotFoundException enf) {
            throw new NotFoundException(enf.getMessage());
        } catch (DuplicatedEntryException dee) {
            throw new WebApplicationException(dee.getMessage(), Response.Status.CONFLICT);
        }
    }

    @Override
    @DELETE
    @Path("/customer/{id}")
    @ResponseStatus(Response.Status.NO_CONTENT)
    public void deleteCustomer(@PathParam("id") long id) {
        try {
            customerActivity.delete(id);
        } catch (EntryNotFoundException enf) {
            throw new NotFoundException(enf.getMessage());
        } catch (NotRemovableException nre) {
            throw new WebApplicationException(nre.getMessage(), Response.Status.BAD_REQUEST);
        }
    }

    @Override
    @POST
    @Path(value = "/customer/{id}/addresses")
    @Consumes({"text/xml", "application/json"})
    public AddressRepresentation addAddress(@PathParam("id") long id, AddressRequest addressRequest) {
        try {
            return customerActivity.addAddress(id, addressRequest);
        } catch (EntryNotFoundException dive) {
            throw new NotFoundException(dive.getMessage());
        }
    }

    @Override
    @DELETE
    @Path("/customer/{id}/address/{addressId}")
    @ResponseStatus(Response.Status.NO_CONTENT)
    public void removeAddress(@PathParam("id") long id, @PathParam("addressId") long addressId) {
        try {
            customerActivity.removeAddress(id, addressId);
        } catch (EntryNotFoundException enf) {
            throw new NotFoundException(enf.getMessage());
        } catch (NotRemovableException nre) {
            throw new BadRequestException(nre.getMessage());
        }
    }

    @Override
    @GET
    @Path(value = "/customer/{id}/addresses")
    @Produces({"text/xml", "application/json"})
    public List<AddressRepresentation> getAddresses(@PathParam("id") long id) {
        try {
            return customerActivity.getAddresses(id);
        } catch (EntryNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }


    @Override
    @POST
    @Path(value = "/customer/{id}/payments")
    @Consumes({"text/xml", "application/json"})
    public PaymentRepresentation addPayment(@PathParam("id") long id, PaymentRequest paymentRequest) {
        try {
            return customerActivity.addPayment(id, paymentRequest);
        } catch (EntryNotFoundException dive) {
            throw new NotFoundException(dive.getMessage());
        }
    }

    @Override
    @DELETE
    @Path("/customer/{id}/payment/{paymentId}")
    @ResponseStatus(Response.Status.NO_CONTENT)
    public void removePayment(@PathParam("id") long id, @PathParam("paymentId") long paymentId) {
        try {
            customerActivity.removePayment(id, paymentId);
        } catch (EntryNotFoundException enf) {
            throw new NotFoundException(enf.getMessage());
        } catch (NotRemovableException nre) {
            throw new BadRequestException(nre.getMessage());
        }
    }

    @Override
    @GET
    @Path(value = "/customer/{id}/payments")
    @Produces({"text/xml", "application/json"})
    public List<PaymentRepresentation> getPayments(@PathParam("id") long id) {
        try {
            return customerActivity.getPayments(id);
        } catch (EntryNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }
}
