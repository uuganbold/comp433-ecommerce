package edu.luc.comp433.business;

import edu.luc.comp433.exceptions.QuantityNotSufficientException;
import edu.luc.comp433.model.Order;
import edu.luc.comp433.model.OrderItem;
import edu.luc.comp433.model.OrderStatus;
import edu.luc.comp433.persistence.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    private SellerService sellerService;

    private ProductService productService;

    public OrderServiceImpl(OrderRepository orderRepository,
                            SellerService sellerService,
                            ProductService productService
    ) {
        this.orderRepository = orderRepository;
        this.sellerService = sellerService;
        this.productService = productService;
    }

    @Override
    public void createOrder(Order order) throws QuantityNotSufficientException {
        for (OrderItem item : order.getItems()) {
            if (item.getQuantity() > item.getProduct().getAvailabilityQuantity()) {
                throw new QuantityNotSufficientException("The product (" + item.getProduct().getId() + ") does not have sufficient quantity.");
            }
        }
        orderRepository.save(order);
        for (OrderItem item : order.getItems()) {
            sellerService.notifySales(item.getProduct().getSeller(), item);
        }
    }

    @Override
    public void updateStatus(Long id, OrderStatus status) {
        Order o = orderRepository.getOne(id);
        o.setStatus(status);
        orderRepository.save(o);
    }

    @Override
    public void cancelOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
