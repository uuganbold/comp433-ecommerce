package edu.luc.comp433.business;

import edu.luc.comp433.business.dto.AddressDto;
import edu.luc.comp433.business.dto.CustomerDto;
import edu.luc.comp433.business.dto.PaymentDto;
import edu.luc.comp433.exceptions.DuplicatedEntryException;
import edu.luc.comp433.exceptions.EntryNotFoundException;
import edu.luc.comp433.exceptions.NotRemovableException;
import edu.luc.comp433.model.Address;
import edu.luc.comp433.model.Customer;
import edu.luc.comp433.model.Payment;
import edu.luc.comp433.persistence.AddressRepository;
import edu.luc.comp433.persistence.CustomerRepository;
import edu.luc.comp433.persistence.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    private AddressRepository addressRepository;

    private PaymentRepository paymentRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, AddressRepository addressRepository, PaymentRepository paymentRepository) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public CustomerDto getCustomer(long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) return null;
        return CustomerDto.of(customer);
    }

    @Override
    public CustomerDto createCustomer(CustomerDto dto) throws DuplicatedEntryException {
        Customer s = dto.toEntity();
        try {
            customerRepository.save(s);
        } catch (DataIntegrityViolationException dive) {
            throw new DuplicatedEntryException("Customers cannot have same email address", dive);
        }
        dto.setId(s.getId());
        return dto;
    }

    @Override
    public List<CustomerDto> listAll() {
        List<CustomerDto> dtos = new ArrayList<>();
        customerRepository.findAll().forEach(s -> dtos.add(CustomerDto.of(s)));
        return dtos;
    }

    @Override
    public void save(CustomerDto dto) throws EntryNotFoundException, DuplicatedEntryException {
        Customer c = customerRepository.findById(dto.getId()).orElseThrow(() -> new EntryNotFoundException("Customer not found with id:" + dto.getId()));
        c.setFirstName(dto.getFirstName());
        c.setLastName(dto.getLastName());
        c.setPhonenumber(dto.getPhonenumber());
        c.setEmail(dto.getEmail());
        try {
            customerRepository.save(c);
        } catch (DataIntegrityViolationException dive) {
            throw new DuplicatedEntryException("Customers cannot have same email address:" + dto.getEmail(), dive);
        }

    }

    @Override
    public void delete(long id) throws EntryNotFoundException, NotRemovableException {
        Customer c = customerRepository.findById(id).orElseThrow(() -> new EntryNotFoundException("Customer not found with id:" + id));
        try {
            customerRepository.delete(c);
        } catch (DataIntegrityViolationException e) {
            throw new NotRemovableException("This customer not removable:" + id);
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressDto> listAddresses(long id) throws EntryNotFoundException {
        Customer c = customerRepository.findById(id).orElseThrow(() -> new EntryNotFoundException("Customer not found with id:" + id));
        List<AddressDto> dtos = new ArrayList<>();
        c.getAddresses().forEach(a -> dtos.add(AddressDto.of(a)));
        return dtos;
    }

    @Override
    @Transactional
    public AddressDto addAddress(long id, AddressDto dto) throws EntryNotFoundException {
        Customer c = customerRepository.findById(id).orElseThrow(() -> new EntryNotFoundException("Customer not found with id:" + id));
        Address a = dto.toEntity();
        addressRepository.save(a);
        c.addAddress(a);
        customerRepository.save(c);
        dto.setId(a.getId());
        return dto;
    }

    @Override
    @Transactional
    public void removeAddress(long id, long addressId) throws EntryNotFoundException, NotRemovableException {
        Customer c = customerRepository.findById(id).orElseThrow(() -> new EntryNotFoundException("Customer not found with id:" + id));
        Address a;
        if ((a = c.getAddress(addressId)) == null)
            throw new EntryNotFoundException("This customer (" + id + ") does have this address: " + addressId);
        try {
            c.removeAddress(a);
            customerRepository.save(c);
        } catch (DataIntegrityViolationException dive) {
            throw new NotRemovableException("This address not removable:" + addressId);
        }

    }


    @Override
    @Transactional(readOnly = true)
    public List<PaymentDto> listPayments(long id) throws EntryNotFoundException {
        Customer c = customerRepository.findById(id).orElseThrow(() -> new EntryNotFoundException("Customer not found with id:" + id));
        List<PaymentDto> dtos = new ArrayList<>();
        c.getPaymentOptions().forEach(a -> dtos.add(PaymentDto.of(a)));
        return dtos;
    }

    @Override
    @Transactional
    public PaymentDto addPayment(long id, PaymentDto dto) throws EntryNotFoundException {
        Customer c = customerRepository.findById(id).orElseThrow(() -> new EntryNotFoundException("Customer not found with id:" + id));
        Address a = c.getAddress(dto.getBillingAddress().getId());
        if (a == null) throw new EntryNotFoundException("Address not found with id:" + dto.getBillingAddress().getId());
        Payment payment = dto.toEntity();
        payment.setBillingAddress(a);
        c.addPaymentOption(payment);
        customerRepository.save(c);
        return PaymentDto.of(payment);
    }

    @Override
    @Transactional
    public void removePayment(long id, long paymentId) throws EntryNotFoundException, NotRemovableException {
        Customer c = customerRepository.findById(id).orElseThrow(() -> new EntryNotFoundException("Customer not found with id:" + id));
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new EntryNotFoundException("Payment method not found with id:" + paymentId));
        if (!payment.getCustomer().equals(c))
            throw new EntryNotFoundException("This customer(" + id + ") does not have that payment method:" + paymentId);
        try {
            c.removePaymentOption(payment);
            customerRepository.save(c);
        } catch (DataIntegrityViolationException dive) {
            throw new NotRemovableException("This payment method not removable:" + paymentId);
        }
    }
}
