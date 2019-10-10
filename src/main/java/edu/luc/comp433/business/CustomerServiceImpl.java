package edu.luc.comp433.business;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

  /*  private CustomerRepository customerRepository;
    private PaymentRepository paymentRepository;
    private AddressRepository addressRepository;
    private OrderService orderService;


    public CustomerServiceImpl(CustomerRepository customerRepository, PaymentRepository paymentRepository, AddressRepository addressRepository, OrderService orderService) {
        this.customerRepository = customerRepository;
        this.paymentRepository = paymentRepository;
        this.addressRepository = addressRepository;
        this.orderService = orderService;
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

    }*/
}
