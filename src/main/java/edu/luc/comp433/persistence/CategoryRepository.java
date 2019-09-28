package edu.luc.comp433.persistence;

import edu.luc.comp433.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {
}
