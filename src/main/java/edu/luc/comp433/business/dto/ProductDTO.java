package edu.luc.comp433.business.dto;

import edu.luc.comp433.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    @Size(min = 3, max = 50)
    private String name;

    @Size(min = 10, max = 1000)
    private String description;

    private double listPrice;

    private long availableQuantity;

    public static ProductDTO of(Product c) {
        return new ProductDTO(c.getId(), c.getName(), c.getDescription(), c.getListPrice(), c.getAvailableQuantity());
    }

    private CategoryDTO categoryDTO;

    private SellerDTO sellerDTO;

    public Product toEntity() {
        Product p = new Product();
        p.setId(id);
        p.setName(name);
        p.setDescription(description);
        p.setListPrice(listPrice);
        p.setAvailableQuantity(availableQuantity);
        return p;
    }

    public ProductDTO setCategoryDTO(CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
        return this;
    }

    public ProductDTO setSellerDTO(SellerDTO sellerDTO) {
        this.sellerDTO = sellerDTO;
        return this;
    }

    public ProductDTO(Long id, @Size(min = 3, max = 50) String name, @Size(min = 10, max = 1000) String description, double listPrice, long availableQuantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.listPrice = listPrice;
        this.availableQuantity = availableQuantity;
    }

    public ProductDTO(Long id) {
        this.id = id;
    }
}
