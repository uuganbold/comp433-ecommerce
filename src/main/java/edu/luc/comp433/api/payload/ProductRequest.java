package edu.luc.comp433.api.payload;

import edu.luc.comp433.business.dto.CategoryDTO;
import edu.luc.comp433.business.dto.ProductDTO;
import edu.luc.comp433.business.dto.SellerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "product")
public class ProductRequest {

    @NotNull
    @NonNull
    @Size(min = 3, max = 50)
    private String name;

    @Size(min = 10, max = 1000)
    private String description;

    @NotNull
    private Double listPrice;

    @NotNull
    private Long availableQuantity;

    @NotNull
    private Long sellerId;

    @NotNull
    private Long categoryId;

    public ProductDTO toDTO() {
        return new ProductDTO(null, name, description, listPrice, availableQuantity)
                .setCategoryDTO(new CategoryDTO(categoryId))
                .setSellerDTO(new SellerDTO(sellerId));
    }
}
