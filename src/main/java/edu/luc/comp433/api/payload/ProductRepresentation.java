package edu.luc.comp433.api.payload;

import edu.luc.comp433.business.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "product")
public class ProductRepresentation {

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
