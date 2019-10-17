package edu.luc.comp433.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Review {

    public Review(@NonNull Product product, @NonNull Customer customer, @NonNull @Size(min = 10, max = 300) String comment, @Max(5) @Min(1) short star) {
        product.addReview(this);
        customer.addReview(this);
        this.comment = comment;
        this.star = star;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true)
    private Customer customer;

    @Max(5)
    @Min(1)
    private short star = 1;

    @NonNull
    @NotBlank
    @Size(min = 10, max = 300)
    private String comment;

    @NotNull
    private Instant date;

}
