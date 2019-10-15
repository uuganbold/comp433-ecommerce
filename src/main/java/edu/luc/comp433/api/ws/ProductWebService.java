package edu.luc.comp433.api.ws;

import edu.luc.comp433.api.payload.ProductRepresentation;
import edu.luc.comp433.api.payload.ProductRequest;

import javax.jws.WebService;
import java.util.List;

@WebService
public interface ProductWebService {

    ProductRepresentation getProduct(long id);

    ProductRepresentation createProduct(ProductRequest productRequest);

    List<ProductRepresentation> allProducts();

    ProductRepresentation updateProduct(long id, ProductRequest categoryRequest);

    void deleteProduct(long id);

    List<ProductRepresentation> search(String query);
}
