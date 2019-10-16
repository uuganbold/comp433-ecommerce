package edu.luc.comp433.business;

import edu.luc.comp433.business.dto.*;
import edu.luc.comp433.exceptions.EntryNotFoundException;
import edu.luc.comp433.exceptions.InvalidStatusException;
import edu.luc.comp433.exceptions.QuantityNotSufficientException;
import edu.luc.comp433.model.*;
import edu.luc.comp433.persistence.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private ProductRepository productRepository;
    private CustomerRepository customerRepository;
    private AddressRepository addressRepository;
    private PaymentRepository paymentRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ProductRepository productRepository, CustomerRepository customerRepository, AddressRepository addressRepository, PaymentRepository paymentRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDTO getOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) return null;
        OrderDTO dto = OrderDTO.of(order)
                .setCustomer(CustomerDTO.of(order.getCustomer()))
                .setAddress(AddressDTO.of(order.getAddress()))
                .setPayment(PaymentDTO.of(order.getPayment()));
        order.getItems().forEach(i -> dto.addItem(OrderItemDTO.of(i).setProduct(ProductDTO.of(i.getProduct()))));
        return dto;
    }

    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO dto) throws QuantityNotSufficientException {
        Customer customer = customerRepository.findById(dto.getCustomer().getId())
                .orElseThrow(() -> new EntryNotFoundException("customer not found with this id:" + dto.getCustomer().getId()));
        Address address = customer.getAddress(dto.getAddress().getId());
        if (address == null)
            throw new EntryNotFoundException("Address not found with this id:" + dto.getAddress().getId());
        Payment payment = paymentRepository.findByIdAndCustomer(dto.getPayment().getId(), customer)
                .orElseThrow(() -> new EntryNotFoundException("Payment option not found with this id:" + dto.getPayment().getId()));
        Order o = dto.toEntity();
        o.setCustomer(customer);
        o.setAddress(address);
        o.setPayment(payment);
        o.setStatus(OrderStatus.Ordered);

        dto.getItems().forEach(i -> {
            Product p = productRepository.findById(i.getProduct().getId())
                    .orElseThrow(() -> new EntryNotFoundException("Product not found with this id:" + i.getProduct().getId()));
            if (p.getAvailableQuantity() < i.getQuantity())
                throw new QuantityNotSufficientException("This product does not have sufficient quantity:" + p.getId() + ", available:" + p.getAvailableQuantity());
            OrderItem item = i.toEntity();
            item.setUnitPrice(p.getListPrice());
            item.setProduct(p);
            p.decreaseQuantity(item.getQuantity());
            productRepository.save(p);
            o.addItem(item);
        });

        orderRepository.save(o);
        OrderDTO resultDTO = OrderDTO.of(o)
                .setAddress(AddressDTO.of(address))
                .setCustomer(CustomerDTO.of(customer))
                .setPayment(PaymentDTO.of(payment));
        o.getItems().forEach(i -> {
            orderItemRepository.save(i);
            resultDTO.addItem(OrderItemDTO.of(i).setProduct(ProductDTO.of(i.getProduct())));
        });
        return resultDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> list() {
        List<OrderDTO> list = new ArrayList<>();
        orderRepository.findAll().forEach(c -> list.add(OrderDTO.of(c)));
        return list;
    }

    @Override
    @Transactional
    public OrderDTO cancelOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntryNotFoundException("Order not found with this id:" + id));
        if (order.getStatus() == OrderStatus.Delivered) {
            throw new InvalidStatusException("This order has been delivered. Delivered orders cannot be cancelled");
        }
        order.setStatus(OrderStatus.Cancelled);
        order.getItems().forEach(i -> {
            i.getProduct().increaseQuantity(i.getQuantity());
            productRepository.save(i.getProduct());
        });
        order.setUpdateDate(Instant.now());
        orderRepository.save(order);
        return OrderDTO.of(order);
    }

    @Override
    public OrderDTO ship(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntryNotFoundException("Order not found with this id:" + id));
        if (order.getStatus() != OrderStatus.Ordered) {
            throw new InvalidStatusException("This order has been " + order.getStatus().name() + ". " + order.getStatus().name() + " orders cannot be shipped");
        }
        order.setStatus(OrderStatus.Shipped);
        order.setUpdateDate(Instant.now());
        orderRepository.save(order);
        return OrderDTO.of(order);
    }

    @Override
    public OrderDTO deliverOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntryNotFoundException("Order not found with this id:" + id));
        if (order.getStatus() != OrderStatus.Shipped) {
            throw new InvalidStatusException("This order has been " + order.getStatus().name() + ". " + order.getStatus().name() + " orders cannot be delivered");
        }
        order.setStatus(OrderStatus.Delivered);
        order.setUpdateDate(Instant.now());
        orderRepository.save(order);
        return OrderDTO.of(order);
    }
}
