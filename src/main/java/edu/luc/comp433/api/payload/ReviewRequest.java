package edu.luc.comp433.api.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.luc.comp433.business.dto.CustomerDTO;
import edu.luc.comp433.business.dto.ProductDTO;
import edu.luc.comp433.business.dto.ReviewDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewRequest {


    @NonNull
    private Long productId;

    @NonNull
    private Long customerId;

    @Max(5)
    @Min(1)
    private short star = 1;

    @NonNull
    @NotNull
    @Size(min = 10, max = 300)
    private String comment;

    public ReviewDTO toDTO() {
        return new ReviewDTO(null, star, comment, null)
                .setProductDTO(new ProductDTO(productId))
                .setCustomerDTO(new CustomerDTO(customerId));
    }
}
