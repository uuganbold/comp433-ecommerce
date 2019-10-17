package edu.luc.comp433.api.payload;

import edu.luc.comp433.business.dto.OrderItemDTO;
import edu.luc.comp433.business.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "item")
public class OrderItemRequest {

    @NotNull
    private Long productId;

    @NotNull
    private Long quantity;

    public OrderItemDTO toDTO() {
        return new OrderItemDTO(quantity).setProduct(new ProductDTO(productId));
    }
}
