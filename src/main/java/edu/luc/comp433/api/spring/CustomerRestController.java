package edu.luc.comp433.api.spring;

import edu.luc.comp433.api.payload.*;
import edu.luc.comp433.api.workflow.CustomerActivity;
import edu.luc.comp433.api.ws.CustomerWebService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerRestController implements CustomerWebService {

    private CustomerActivity customerActivity;

    public CustomerRestController(CustomerActivity customerActivity) {
        this.customerActivity = customerActivity;
    }

    @Override
    public CustomerRepresentation getCustomer(long id) {
        return null;
    }

    @Override
    public CustomerRepresentation createCustomer(CustomerRequest customerRequest) {
        return null;
    }

    @Override
    public List<CustomerRepresentation> allCustomers() {
        return null;
    }

    @Override
    public CustomerRepresentation updateCustomer(long id, CustomerRequest customerRequest) {
        return null;
    }

    @Override
    public void deleteCustomer(long id) {

    }

    @Override
    public AddressRepresentation addAddress(long id, AddressRequest addressRequest) {
        return null;
    }

    @Override
    public void removeAddress(long id, long addressId) {

    }

    @Override
    public List<AddressRepresentation> getAddresses(long id) {
        return null;
    }

    @Override
    public PaymentRepresentation addPayment(long id, PaymentRequest paymentRequest) {
        return null;
    }

    @Override
    public void removePayment(long id, long paymentId) {

    }

    @Override
    public List<PaymentRepresentation> getPayments(long id) {
        return null;
    }
}
