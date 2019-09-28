package edu.luc.comp433.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Column(length = 1000)
    private String description;

    private double listPrice;

    private long availabilityQuantity;

    @ManyToOne
    @JoinColumn(name = "seller_id",nullable = false)
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(
            mappedBy = "product",
            fetch = FetchType.LAZY
    )
    @OrderBy("date desc")
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(
            mappedBy = "product",
            fetch = FetchType.LAZY
    )
    private Set<OrderItem> orderItems = new HashSet<>();

    public void addReview(Review review) {
        this.reviews.add(review);
        review.setProduct(this);
    }

    public void removeReview(Review review) {
        this.reviews.remove(review);
        review.setProduct(null);
    }
}
