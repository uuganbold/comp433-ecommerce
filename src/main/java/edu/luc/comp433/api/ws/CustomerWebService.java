package edu.luc.comp433.api.ws;

import edu.luc.comp433.api.payload.*;

import javax.jws.WebService;
import javax.validation.Valid;
import java.util.List;

@WebService
public interface CustomerWebService {

    CustomerRepresentation getCustomer(long id);

    CustomerRepresentation createCustomer(@Valid CustomerRequest customerRequest);

    List<CustomerRepresentation> allCustomers();

    CustomerRepresentation updateCustomer(long id, @Valid CustomerRequest customerRequest);

    void deleteCustomer(long id);

    AddressRepresentation addAddress(long id, AddressRequest addressRequest);

    void removeAddress(long id, long addressId);

    List<AddressRepresentation> getAddresses(long id);

    PaymentRepresentation addPayment(long id, PaymentRequest paymentRequest);

    void removePayment(long id, long paymentId);

    List<PaymentRepresentation> getPayments(long id);

}
