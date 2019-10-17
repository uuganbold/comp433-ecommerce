package edu.luc.comp433.business.dto;

import edu.luc.comp433.model.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

    private Long id;

    private short star = 1;

    private String comment;

    private Instant date;

    private ProductDTO productDTO;

    private CustomerDTO customerDTO;

    public ReviewDTO(Long id, short star, String comment, Instant date) {
        this.id = id;
        this.star = star;
        this.comment = comment;
        this.date = date;
    }

    public static ReviewDTO of(Review review) {
        return new ReviewDTO(review.getId(), review.getStar(), review.getComment(), review.getDate());
    }

    public ReviewDTO setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
        return this;
    }

    public ReviewDTO setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
        return this;
    }

    public Review toEntity() {
        Review p = new Review();
        p.setStar(star);
        p.setComment(comment);
        return p;
    }
}
