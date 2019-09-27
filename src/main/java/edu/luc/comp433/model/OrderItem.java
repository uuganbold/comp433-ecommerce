package edu.luc.comp433.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private CustomerOrder customerOrder;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private double unitPrice;

    private long quantity;
}
