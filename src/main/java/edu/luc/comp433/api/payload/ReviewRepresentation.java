package edu.luc.comp433.api.payload;

import edu.luc.comp433.business.dto.ReviewDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "review")
public class ReviewRepresentation {

    private long id;

    private short star = 1;

    private String comment;

    private Instant date;

    private ProductRepresentation product;

    private CustomerRepresentation customer;

    public static ReviewRepresentation of(ReviewDTO dto) {
        return new ReviewRepresentation(dto.getId(), dto.getStar(), dto.getComment(), dto.getDate(), ProductRepresentation.of(dto.getProductDTO()), CustomerRepresentation.of(dto.getCustomerDTO()));
    }
}
