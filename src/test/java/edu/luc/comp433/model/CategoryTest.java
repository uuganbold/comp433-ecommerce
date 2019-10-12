package edu.luc.comp433.model;

import edu.luc.comp433.persistence.CategoryRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryTest extends DomainModelTest {

    @Autowired
    private CategoryRepository categoryRepository;


    @Test
    @Order(1)
    void emptyTable_addElement_Expect1Row() {
        //given
        long count = categoryRepository.count();
        Category c = new Category("Category Name");

        //when
        categoryRepository.save(c);

        //then
        assertEquals(count + 1, categoryRepository.count());
    }

    @Test
    @Order(2)
    void oneRow_delete_zero() {
        //given
        long count = categoryRepository.count();
        Category c = categoryRepository.findAll().get(0);

        //when
        categoryRepository.delete(c);

        //then
        assertEquals(count - 1, categoryRepository.count());

    }

    @Test
    @Order(3)
    void shouldNotAllowDuplicatedName() {
        //given
        String name = "TestName";
        Category c = new Category(name);
        categoryRepository.save(c);

        //when
        Category c1 = new Category(name);
        assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(c1));

    }
}
