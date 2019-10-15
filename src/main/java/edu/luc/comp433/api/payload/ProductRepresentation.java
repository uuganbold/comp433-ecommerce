package edu.luc.comp433.api.payload;

import edu.luc.comp433.business.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRepresentation {

    @NotNull
    private Long id;

    @NotNull
    @NonNull
    @Size(min = 3, max = 50)
    private String name;

    @Size(min = 10, max = 1000)
    private String description;

    private double listPrice;

    private long availableQuantity;

    private CategoryRepresentation category;

    private SellerRepresentation seller;

    public static ProductRepresentation of(ProductDTO dto) {
        return new ProductRepresentation(dto.getId(), dto.getName(), dto.getDescription(), dto.getListPrice(), dto.getAvailableQuantity(), CategoryRepresentation.of(dto.getCategoryDTO()), SellerRepresentation.of(dto.getSellerDTO()));
    }
}
