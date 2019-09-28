package edu.luc.comp433.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Entity
@Data
public class CustomerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private Instant date;

    @OneToMany(mappedBy = "customerOrder")
    private Set<OrderItem> items;

    @Enumerated(EnumType.STRING)
    @Column(length = 12)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    private long totalValue;
}
