package edu.luc.comp433.business;

import edu.luc.comp433.exceptions.InformationNotSufficientException;
import edu.luc.comp433.exceptions.QuantityNotSufficientException;
import edu.luc.comp433.model.*;
import edu.luc.comp433.persistence.AddressRepository;
import edu.luc.comp433.persistence.CustomerRepository;
import edu.luc.comp433.persistence.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private PaymentRepository paymentRepository;
    private AddressRepository addressRepository;
    private OrderService orderService;


    public CustomerServiceImpl(CustomerRepository customerRepository, PaymentRepository paymentRepository, AddressRepository addressRepository, OrderService orderService) {
        this.customerRepository = customerRepository;
        this.paymentRepository = paymentRepository;
        this.addressRepository = addressRepository;
        this.orderService = orderService;
    }

    @Transactional
    public void addCustomer(Customer customer) {
        this.customerRepository.save(customer);
    }

    @Transactional
    public void removeCustomer(Long id) {
        this.customerRepository.deleteById(id);
    }

    @Transactional
    public void updateCustomer(Customer customer) {
        this.customerRepository.save(customer);
    }

    @Override
    public void addPaymentOption(Customer customer, Payment payment) {
        customer.getPaymentOptions().add(payment);
        payment.setCustomer(customer);
        customerRepository.save(customer);
    }

    @Override
    public void removePaymentOption(Customer customer, Long id) {
        Payment p = paymentRepository.getOne(id);
        customer.getPaymentOptions().remove(p);
        customerRepository.save(customer);
    }

    @Override
    public void addAddress(Customer customer, Address address) {
        customer.getAddresses().add(address);
        customerRepository.save(customer);
    }

    @Override
    public void removeAddress(Customer customer, Long id) {
        Address a = addressRepository.getOne(id);
        customer.getAddresses().remove(a);
        customerRepository.save(customer);
    }

    @Override
    public Payment getDefaultPayment(Customer customer) {
        if (customer.getPaymentOptions().isEmpty()) return null;
        return customer.getPaymentOptions().get(0);
    }

    @Override
    public Address getDefaultAddress(Customer customer) {
        if (customer.getAddresses().isEmpty()) return null;
        return customer.getAddresses().get(0);
    }

    @Override
    public void makeOrder(Customer customer, Cart cart, Payment payment, Address address) throws InformationNotSufficientException, QuantityNotSufficientException {
        if (payment == null) payment = getDefaultPayment(customer);
        if (payment == null) throw new InformationNotSufficientException("No payment information available");
        if (address == null) address = getDefaultAddress(customer);
        if (address == null) throw new InformationNotSufficientException("No address information available");

        Order order = cart.convertToOrder();
        order.setCustomer(customer);
        order.setAddress(address);
        order.setPayment(payment);
        orderService.createOrder(order);

    }
}
