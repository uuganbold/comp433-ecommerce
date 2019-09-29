package edu.luc.comp433.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "customer_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private Instant date = Instant.now();

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderItem> items = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(length = 12)
    private OrderStatus status = OrderStatus.Ordered;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    private long totalValue = 0;

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
        totalValue += item.getQuantity() * item.getUnitPrice();
    }

    public void removeItem(OrderItem item) {
        items.remove(item);
        item.setOrder(null);
        totalValue -= item.getQuantity() * item.getUnitPrice();
    }
}
