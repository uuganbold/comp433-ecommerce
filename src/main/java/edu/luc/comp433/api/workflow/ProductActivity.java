package edu.luc.comp433.api.workflow;

import edu.luc.comp433.api.payload.ProductRepresentation;
import edu.luc.comp433.api.payload.ProductRequest;
import edu.luc.comp433.business.ProductService;
import edu.luc.comp433.business.dto.ProductDTO;
import edu.luc.comp433.exceptions.EntryNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductActivity {

    private ProductService productService;

    public ProductActivity(ProductService productService) {
        this.productService = productService;
    }

    public ProductRepresentation getProduct(long id) {
        ProductDTO dto = productService.getProduct(id);
        if (dto == null) throw new EntryNotFoundException("Product not found with this id:" + id);
        return ProductRepresentation.of(dto);
    }

    public ProductRepresentation createProduct(ProductRequest productRequest) {
        ProductDTO dto = productService.createProduct(productRequest.toDTO());
        return ProductRepresentation.of(dto);
    }

    public ProductRepresentation update(long id, ProductRequest productRequest) {
        ProductDTO dto = productRequest.toDTO();
        dto.setId(id);
        dto = productService.save(dto);
        return ProductRepresentation.of(dto);
    }

    public List<ProductRepresentation> list() {
        List<ProductDTO> list = productService.list();
        List<ProductRepresentation> result = new ArrayList<>();
        list.forEach(dto -> result.add(ProductRepresentation.of(dto)));
        return result;
    }

    public List<ProductRepresentation> search(String query) {
        List<ProductDTO> list = productService.search(query);
        List<ProductRepresentation> result = new ArrayList<>();
        list.forEach(dto -> result.add(ProductRepresentation.of(dto)));
        return result;
    }

    public void delete(long id) {
        productService.delete(id);
    }
}
