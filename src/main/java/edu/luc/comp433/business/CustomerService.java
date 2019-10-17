package edu.luc.comp433.business;

import edu.luc.comp433.business.dto.AddressDTO;
import edu.luc.comp433.business.dto.CustomerDTO;
import edu.luc.comp433.business.dto.PaymentDTO;
import edu.luc.comp433.exceptions.DuplicatedEntryException;
import edu.luc.comp433.exceptions.EntryNotFoundException;
import edu.luc.comp433.exceptions.NotRemovableException;

import java.util.List;

public interface CustomerService {

    //void makeOrder(Customer customer, Cart cart, Payment payment, Address address) throws InformationNotSufficientException, QuantityNotSufficientException;

    CustomerDTO getCustomer(long id);

    CustomerDTO createCustomer(CustomerDTO dto) throws DuplicatedEntryException;

    List<CustomerDTO> listAll();

    void save(CustomerDTO dto) throws EntryNotFoundException, DuplicatedEntryException;

    void delete(long id) throws EntryNotFoundException, NotRemovableException;

    List<AddressDTO> listAddresses(long id) throws EntryNotFoundException;

    AddressDTO addAddress(long id, AddressDTO dto) throws EntryNotFoundException;

    void removeAddress(long id, long addressId) throws EntryNotFoundException, NotRemovableException;

    List<PaymentDTO> listPayments(long id) throws EntryNotFoundException;

    PaymentDTO addPayment(long id, PaymentDTO dto) throws EntryNotFoundException;

    void removePayment(long id, long paymentId) throws EntryNotFoundException, NotRemovableException;

}
