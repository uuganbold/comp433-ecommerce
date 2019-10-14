package edu.luc.comp433.business;

import edu.luc.comp433.business.dto.AddressDto;
import edu.luc.comp433.business.dto.CustomerDto;
import edu.luc.comp433.business.dto.PaymentDto;
import edu.luc.comp433.exceptions.DuplicatedEntryException;
import edu.luc.comp433.exceptions.EntryNotFoundException;
import edu.luc.comp433.exceptions.NotRemovableException;

import java.util.List;

public interface CustomerService {

    //void makeOrder(Customer customer, Cart cart, Payment payment, Address address) throws InformationNotSufficientException, QuantityNotSufficientException;

    CustomerDto getCustomer(long id);

    CustomerDto createCustomer(CustomerDto dto) throws DuplicatedEntryException;

    List<CustomerDto> listAll();

    void save(CustomerDto dto) throws EntryNotFoundException, DuplicatedEntryException;

    void delete(long id) throws EntryNotFoundException, NotRemovableException;

    List<AddressDto> listAddresses(long id) throws EntryNotFoundException;

    AddressDto addAddress(long id, AddressDto dto) throws EntryNotFoundException;

    void removeAddress(long id, long addressId) throws EntryNotFoundException, NotRemovableException;

    List<PaymentDto> listPayments(long id) throws EntryNotFoundException;

    PaymentDto addPayment(long id, PaymentDto dto) throws EntryNotFoundException;

    void removePayment(long id, long paymentId) throws EntryNotFoundException, NotRemovableException;

}
