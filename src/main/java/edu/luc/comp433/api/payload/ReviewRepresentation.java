package edu.luc.comp433.api.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import edu.luc.comp433.business.dto.ReviewDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewRepresentation extends RepresentationModel<ReviewRepresentation> {

    private long id;

    private short star = 1;

    private String comment;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant date;

    private ProductRepresentation product;

    private CustomerRepresentation customer;

    public ReviewRepresentation(long id, short star, String comment, Instant date) {
        this.id = id;
        this.star = star;
        this.comment = comment;
        this.date = date;
    }

    public static ReviewRepresentation of(ReviewDTO dto) {
        ReviewRepresentation result = new ReviewRepresentation(dto.getId(), dto.getStar(), dto.getComment(), dto.getDate());
        if (dto.getProductDTO() != null) result.setProduct(ProductRepresentation.of(dto.getProductDTO()));
        if (dto.getCustomerDTO() != null) result.setCustomer(CustomerRepresentation.of(dto.getCustomerDTO()));
        return result;
    }
}
