package edu.luc.comp433.api.spring;

import edu.luc.comp433.api.payload.ProductRepresentation;
import edu.luc.comp433.api.payload.ProductRequest;
import edu.luc.comp433.api.workflow.ProductActivity;
import edu.luc.comp433.api.ws.ProductWebService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ProductRestController implements ProductWebService {

    private ProductActivity productActivity;

    public ProductRestController(ProductActivity productActivity) {
        this.productActivity = productActivity;
    }

    @Override
    @GetMapping(value = "/product/{id}", produces = "application/hal+json")
    public ProductRepresentation getProduct(@PathVariable long id) {
        return withLinks(productActivity.getProduct(id));
    }

    @Override
    @PostMapping(value = "/products", consumes = {"application/json"}, produces = {"application/hal+json"})
    public ProductRepresentation createProduct(@RequestBody @Validated ProductRequest ProductRequest) {
        return withLinks(productActivity.createProduct(ProductRequest));
    }

    @Override
    @PutMapping(value = "/product/{id}", consumes = {"application/json"}, produces = {"application/hal+json"})
    public ProductRepresentation updateProduct(@PathVariable long id, @RequestBody @Validated ProductRequest ProductRequest) {
        return withLinks(productActivity.update(id, ProductRequest));
    }

    @Override
    @GetMapping(value = "/products", produces = {"application/hal+json"})
    public List<ProductRepresentation> allProducts() {
        return withLinks(productActivity.list());
    }

    @Override
    @GetMapping(value = "/products", produces = {"application/hal+json"}, params = {"q"})
    public List<ProductRepresentation> search(@RequestParam("q") String query) {
        return withLinks(productActivity.search(query));
    }

    @Override
    @DeleteMapping("/product/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable long id) {
        productActivity.delete(id);
    }

    protected ProductRepresentation withLinks(ProductRepresentation productRepresentation) {
        productRepresentation.add(linkTo(methodOn(ProductRestController.class).getProduct(productRepresentation.getId())).withSelfRel());
        productRepresentation.add(linkTo(methodOn(ProductRestController.class).allProducts()).withRel("all"));
        productRepresentation.add(linkTo(methodOn(OrderRestController.class).allOrders()).withRel("orders"));
        productRepresentation.add(linkTo(methodOn(CategoryRestController.class).getCategory(productRepresentation.getCategory().getId())).withRel("category"));
        productRepresentation.add(linkTo(methodOn(SellerRestController.class).getSeller(productRepresentation.getSeller().getId())).withRel("seller"));
        return productRepresentation;
    }

    protected List<ProductRepresentation> withLinks(List<ProductRepresentation> list) {
        list.forEach(this::withLinks);
        return list;
    }
}
