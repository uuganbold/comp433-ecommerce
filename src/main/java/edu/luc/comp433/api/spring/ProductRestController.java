package edu.luc.comp433.api.spring;

import edu.luc.comp433.api.payload.ProductRepresentation;
import edu.luc.comp433.api.payload.ProductRequest;
import edu.luc.comp433.api.workflow.ProductActivity;
import edu.luc.comp433.api.ws.ProductWebService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductRestController implements ProductWebService {

    private ProductActivity productActivity;

    public ProductRestController(ProductActivity productActivity) {
        this.productActivity = productActivity;
    }

    @Override
    @GetMapping("/product/{id}")
    public ProductRepresentation getProduct(@PathVariable long id) {
        return productActivity.getProduct(id);
    }

    @Override
    @PostMapping(value = "/products", consumes = {"application/json"}, produces = {"application/hal+json"})
    public ProductRepresentation createProduct(@RequestBody @Validated ProductRequest ProductRequest) {
        return productActivity.createProduct(ProductRequest);
    }

    @Override
    @PutMapping(value = "/product/{id}", consumes = {"application/json"}, produces = {"application/hal+json"})
    public ProductRepresentation updateProduct(@PathVariable long id, @RequestBody @Validated ProductRequest ProductRequest) {
        return productActivity.update(id, ProductRequest);
    }

    @Override
    @GetMapping(value = "/products", produces = {"application/hal+json"})
    public List<ProductRepresentation> allProducts() {
        return productActivity.list();
    }

    @Override
    @GetMapping(value = "/products", produces = {"application/hal+json"}, params = {"q"})
    public List<ProductRepresentation> search(@RequestParam("q") String query) {
        return productActivity.search(query);
    }

    @Override
    @DeleteMapping("/product/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable long id) {
        productActivity.delete(id);
    }
}
