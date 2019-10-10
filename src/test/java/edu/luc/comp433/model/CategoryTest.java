package edu.luc.comp433.model;

import edu.luc.comp433.persistence.CategoryRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryTest extends DomainModelTest {

    @Autowired
    private CategoryRepository categoryRepository;


    @Test
    @Order(1)
    public void emptyTable_addElement_Expect1Row() {
        Category c = new Category("Category Name");
        categoryRepository.save(c);
        assertEquals(categoryRepository.count(), 1);
    }

    @Test
    @Order(2)
    public void oneRow_delete_zero() {
        Category c = categoryRepository.findAll().get(0);
        categoryRepository.delete(c);
        assertEquals(categoryRepository.count(), 0);

    }
}
