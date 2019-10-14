package edu.luc.comp433.api.workflow;

import edu.luc.comp433.api.payload.*;
import edu.luc.comp433.business.CustomerService;
import edu.luc.comp433.business.dto.AddressDto;
import edu.luc.comp433.business.dto.CustomerDto;
import edu.luc.comp433.business.dto.PaymentDto;
import edu.luc.comp433.exceptions.DuplicatedEntryException;
import edu.luc.comp433.exceptions.EntryNotFoundException;
import edu.luc.comp433.exceptions.NotRemovableException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerActivity {

    private CustomerService customerService;

    public CustomerActivity(CustomerService customerService) {
        this.customerService = customerService;
    }

    public CustomerRepresentation getCustomer(long id) {
        CustomerDto dto = customerService.getCustomer(id);
        if (dto == null) return null;
        return CustomerRepresentation.of(dto);
    }

    public CustomerRepresentation createCustomer(CustomerRequest customerRequest) throws DuplicatedEntryException {
        CustomerDto dto = customerRequest.toDto();
        dto = customerService.createCustomer(dto);
        return CustomerRepresentation.of(dto);
    }

    public List<CustomerRepresentation> listCustomers() {
        List<CustomerDto> dtos = customerService.listAll();
        List<CustomerRepresentation> result = new ArrayList<>();
        dtos.forEach(d -> result.add(CustomerRepresentation.of(d)));
        return result;
    }

    public CustomerRepresentation update(long id, CustomerRequest customerRequest) throws EntryNotFoundException, DuplicatedEntryException {
        CustomerDto dto = customerRequest.toDto();
        dto.setId(id);
        customerService.save(dto);
        return CustomerRepresentation.of(dto);
    }


    public void delete(long id) throws EntryNotFoundException, NotRemovableException {
        customerService.delete(id);
    }

    public List<AddressRepresentation> getAddresses(long id) throws EntryNotFoundException {
        List<AddressDto> dtos = customerService.listAddresses(id);
        List<AddressRepresentation> result = new ArrayList<>();
        dtos.forEach(d -> result.add(AddressRepresentation.of(d)));
        return result;
    }

    public AddressRepresentation addAddress(long id, AddressRequest addressRequest) throws EntryNotFoundException {
        AddressDto dto = addressRequest.toDto();
        dto = customerService.addAddress(id, dto);
        return AddressRepresentation.of(dto);
    }

    public void removeAddress(long id, long addressId) throws EntryNotFoundException, NotRemovableException {
        customerService.removeAddress(id, addressId);
    }

    public List<PaymentRepresentation> getPayments(long id) throws EntryNotFoundException {
        List<PaymentDto> dtos = customerService.listPayments(id);
        List<PaymentRepresentation> result = new ArrayList<>();
        dtos.forEach(d -> result.add(PaymentRepresentation.of(d)));
        return result;
    }

    public PaymentRepresentation addPayment(long id, PaymentRequest paymentRequest) throws EntryNotFoundException {
        PaymentDto dto = paymentRequest.toDto();
        dto = customerService.addPayment(id, dto);
        return PaymentRepresentation.of(dto);
    }

    public void removePayment(long id, long paymentId) throws EntryNotFoundException, NotRemovableException {
        customerService.removePayment(id, paymentId);
    }
}
