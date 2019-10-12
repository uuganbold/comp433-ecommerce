package edu.luc.comp433.business;

import edu.luc.comp433.model.Product;
import edu.luc.comp433.persistence.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> search(String query) {
        return productRepository.findByNameContains(query.trim());
    }

}
