package edu.luc.comp433.api.payload;

import edu.luc.comp433.business.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRepresentation extends RepresentationModel<ProductRepresentation> {

    private Long id;

    private String name;

    private String description;

    private double listPrice;

    private long availableQuantity;

    private CategoryRepresentation category;

    private SellerRepresentation seller;

    public static ProductRepresentation of(ProductDTO dto) {
        return new ProductRepresentation(dto.getId(), dto.getName(), dto.getDescription(), dto.getListPrice(), dto.getAvailableQuantity(), CategoryRepresentation.of(dto.getCategoryDTO()), SellerRepresentation.of(dto.getSellerDTO()));
    }
}
