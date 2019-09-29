package edu.luc.comp433.business;

import edu.luc.comp433.exceptions.InformationNotSufficientException;
import edu.luc.comp433.exceptions.QuantityNotSufficientException;
import edu.luc.comp433.model.Address;
import edu.luc.comp433.model.Cart;
import edu.luc.comp433.model.Customer;
import edu.luc.comp433.model.Payment;

public interface CustomerService {

    void addCustomer(Customer customer);

    void removeCustomer(Long id);

    void updateCustomer(Customer customer);

    void addPaymentOption(Customer customer, Payment payment);

    void removePaymentOption(Customer customer, Long id);

    void addAddress(Customer customer, Address address);

    void removeAddress(Customer customer, Long id);

    Payment getDefaultPayment(Customer customer);

    Address getDefaultAddress(Customer customer);

    void makeOrder(Customer customer, Cart cart, Payment payment, Address address) throws InformationNotSufficientException, QuantityNotSufficientException;
}
