package edu.luc.comp433.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id",nullable = false)
    private Customer customer;

    private short star;

    private String comment;

    private Instant date;


}
