package edu.luc.comp433.business;

import edu.luc.comp433.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> search(String query);
}
