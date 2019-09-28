package edu.luc.comp433.persistence;

import edu.luc.comp433.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
