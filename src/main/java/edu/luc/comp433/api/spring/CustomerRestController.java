package edu.luc.comp433.api.spring;

import edu.luc.comp433.api.payload.*;
import edu.luc.comp433.api.workflow.CustomerActivity;
import edu.luc.comp433.api.ws.CustomerWebService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerRestController implements CustomerWebService {

    private CustomerActivity customerActivity;

    public CustomerRestController(CustomerActivity customerActivity) {
        this.customerActivity = customerActivity;
    }

    @Override
    @GetMapping("/customer/{id}")
    public CustomerRepresentation getCustomer(@PathVariable("id") long id) {
        return customerActivity.getCustomer(id);
    }

    @Override
    @PostMapping(value = "/customers", consumes = {"text/xml", "application/json"}, produces = {"text/xml", "application/json"})
    public CustomerRepresentation createCustomer(@RequestBody @Validated CustomerRequest customerRequest) {
        return customerActivity.createCustomer(customerRequest);
    }

    @Override
    @GetMapping(value = "/customers", produces = {"text/xml", "application/json"})
    public List<CustomerRepresentation> allCustomers() {
        return customerActivity.listCustomers();
    }

    @Override
    @PutMapping(value = "/customer/{id}", consumes = {"text/xml", "application/json"}, produces = {"text/xml", "application/json"})
    public CustomerRepresentation updateCustomer(@PathVariable("id") long id, @RequestBody @Validated CustomerRequest customerRequest) {
        return customerActivity.update(id, customerRequest);
    }

    @Override
    @DeleteMapping("/customer/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("id") long id) {
        customerActivity.delete(id);
    }

    @Override
    @PostMapping(value = "/customer/{id}/addresses", consumes = {"text/xml", "application/json"}, produces = {"text/xml", "application/json"})
    public AddressRepresentation addAddress(@PathVariable("id") long id, @Validated @RequestBody AddressRequest addressRequest) {
        return customerActivity.addAddress(id, addressRequest);
    }

    @Override
    @DeleteMapping("/customer/{id}/address/{addressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAddress(@PathVariable("id") long id, @PathVariable("addressId") long addressId) {
        customerActivity.removeAddress(id, addressId);
    }

    @Override
    @GetMapping(value = "/customer/{id}/addresses", produces = {"text/xml", "application/json"})
    public List<AddressRepresentation> getAddresses(@PathVariable("id") long id) {
        return customerActivity.getAddresses(id);
    }


    @Override
    @PostMapping(value = "/customer/{id}/payments", consumes = {"text/xml", "application/json"}, produces = {"text/xml", "application/json"})
    public PaymentRepresentation addPayment(@PathVariable("id") long id, @RequestBody @Validated PaymentRequest paymentRequest) {
        return customerActivity.addPayment(id, paymentRequest);
    }

    @Override
    @DeleteMapping("/customer/{id}/payment/{paymentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePayment(@PathVariable("id") long id, @PathVariable("paymentId") long paymentId) {
        customerActivity.removePayment(id, paymentId);
    }

    @Override
    @GetMapping(value = "/customer/{id}/payments", produces = {"text/xml", "application/json"})
    public List<PaymentRepresentation> getPayments(@PathVariable("id") long id) {
        return customerActivity.getPayments(id);
    }
}
