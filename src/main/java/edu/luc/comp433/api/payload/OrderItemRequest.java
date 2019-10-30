package edu.luc.comp433.api.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.luc.comp433.business.dto.OrderItemDTO;
import edu.luc.comp433.business.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderItemRequest {

    @NotNull
    private Long productId;

    @NotNull
    private Long quantity;

    public OrderItemDTO toDTO() {
        return new OrderItemDTO(quantity).setProduct(new ProductDTO(productId));
    }
}
