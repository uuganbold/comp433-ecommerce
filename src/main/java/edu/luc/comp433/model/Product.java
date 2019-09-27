package edu.luc.comp433.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private double listPrice;

    private long availabilityQuantity;

    @ManyToOne
    @JoinColumn(name = "seller_id",nullable = false)
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product")
    @OrderBy("date desc")
    private Set<Review> reviews;

    @OneToMany(mappedBy = "product")
    private Set<OrderItem> orderItems;
}
