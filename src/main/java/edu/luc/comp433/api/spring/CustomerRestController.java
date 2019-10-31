package edu.luc.comp433.api.spring;

import edu.luc.comp433.api.payload.*;
import edu.luc.comp433.api.workflow.CustomerActivity;
import edu.luc.comp433.api.ws.CustomerWebService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CustomerRestController implements CustomerWebService {

    private CustomerActivity customerActivity;

    public CustomerRestController(CustomerActivity customerActivity) {
        this.customerActivity = customerActivity;
    }

    @Override
    @GetMapping(value = "/customer/{id}", produces = {"application/hal+json"})
    public CustomerRepresentation getCustomer(@PathVariable("id") long id) {
        CustomerRepresentation customer = customerActivity.getCustomer(id);
        return withLinks(customer);
    }

    @Override
    @PostMapping(value = "/customers", consumes = {"application/json"}, produces = {"application/hal+json"})
    public CustomerRepresentation createCustomer(@RequestBody @Validated CustomerRequest customerRequest) {
        CustomerRepresentation customer = customerActivity.createCustomer(customerRequest);
        return withLinks(customer);
    }

    @Override
    @GetMapping(value = "/customers", produces = {"application/hal+json"})
    public List<CustomerRepresentation> allCustomers() {
        List<CustomerRepresentation> list = customerActivity.listCustomers();
        return withLinks(list);
    }

    @Override
    @PutMapping(value = "/customer/{id}", consumes = {"application/json"}, produces = {"application/hal+json"})
    public CustomerRepresentation updateCustomer(@PathVariable("id") long id, @RequestBody @Validated CustomerRequest customerRequest) {
        CustomerRepresentation customer = customerActivity.update(id, customerRequest);
        return withLinks(customer);
    }

    @Override
    @DeleteMapping("/customer/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("id") long id) {
        customerActivity.delete(id);
    }

    @Override
    @PostMapping(value = "/customer/{id}/addresses", consumes = {"application/json"}, produces = {"application/hal+json"})
    public AddressRepresentation addAddress(@PathVariable("id") long id, @Validated @RequestBody AddressRequest addressRequest) {
        AddressRepresentation address = customerActivity.addAddress(id, addressRequest);
        return withLinks(id, address);
    }

    @Override
    @DeleteMapping("/customer/{id}/address/{addressId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAddress(@PathVariable("id") long id, @PathVariable("addressId") long addressId) {
        customerActivity.removeAddress(id, addressId);
    }

    @Override
    @GetMapping(value = "/customer/{id}/addresses", produces = {"application/hal+json"})
    public List<AddressRepresentation> getAddresses(@PathVariable("id") long id) {
        List<AddressRepresentation> addresses = customerActivity.getAddresses(id);
        return withLinksAddresses(id, addresses);
    }


    @Override
    @PostMapping(value = "/customer/{id}/payments", consumes = { "application/json"}, produces = {"application/hal+json"})
    public PaymentRepresentation addPayment(@PathVariable("id") long id, @RequestBody @Validated PaymentRequest paymentRequest) {
        PaymentRepresentation payment = customerActivity.addPayment(id, paymentRequest);
        return withLinks(id, payment);
    }

    @Override
    @DeleteMapping("/customer/{id}/payment/{paymentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePayment(@PathVariable("id") long id, @PathVariable("paymentId") long paymentId) {
        customerActivity.removePayment(id, paymentId);
    }

    @Override
    @GetMapping(value = "/customer/{id}/payments", produces = {"application/hal+json"})
    public List<PaymentRepresentation> getPayments(@PathVariable("id") long id) {
        List<PaymentRepresentation> list = customerActivity.getPayments(id);
        return withLinksPayments(id, list);
    }

    protected CustomerRepresentation withLinks(CustomerRepresentation customer) {
        customer.add(linkTo(methodOn(CustomerRestController.class).getCustomer(customer.getId())).withSelfRel());
        customer.add(linkTo(methodOn(CustomerRestController.class).allCustomers()).withRel("all"));
        customer.add(linkTo(methodOn(CustomerRestController.class).getAddresses(customer.getId())).withRel("addresses"));
        customer.add(linkTo(methodOn(CustomerRestController.class).getPayments(customer.getId())).withRel("payments"));
        return customer;
    }

    protected List<CustomerRepresentation> withLinks(List<CustomerRepresentation> customers) {
        customers.forEach(this::withLinks);
        return customers;
    }

    protected AddressRepresentation withLinks(long customerId, AddressRepresentation address) {
        address.add(linkTo(methodOn(CustomerRestController.class).getCustomer(customerId)).withRel("customer"));
        address.add(linkTo(methodOn(CustomerRestController.class).getAddresses(customerId)).withRel("all"));
        return address;
    }

    protected List<AddressRepresentation> withLinksAddresses(long id, List<AddressRepresentation> addresses) {
        addresses.forEach(a -> withLinks(id, a));
        return addresses;
    }

    protected PaymentRepresentation withLinks(long customerId, PaymentRepresentation payment) {
        payment.add(linkTo(methodOn(CustomerRestController.class).getCustomer(customerId)).withRel("customer"));
        payment.add(linkTo(methodOn(CustomerRestController.class).getPayments(customerId)).withRel("all"));
        return payment;
    }

    protected List<PaymentRepresentation> withLinksPayments(long id, List<PaymentRepresentation> payments) {
        payments.forEach(p -> withLinks(id, p));
        return payments;
    }
}
