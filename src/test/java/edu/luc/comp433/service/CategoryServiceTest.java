package edu.luc.comp433.service;

import edu.luc.comp433.business.CategoryService;
import edu.luc.comp433.business.CategoryServiceImpl;
import edu.luc.comp433.model.Category;
import edu.luc.comp433.persistence.CategoryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
public class CategoryServiceTest {

    @Configuration
    static class ContextConfiguration {

        @Bean
        public CategoryService categoryService() {
            return new CategoryServiceImpl(categoryRepository());
        }

        @Bean
        public CategoryRepository categoryRepository() {
            return mock(CategoryRepository.class);
        }
    }

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;


    @Test
    public void addCategory_givenCategory() {
        //Given
        Category cat = new Category();
        cat.setId(100L);
        cat.setName("TestCategory");

        when(categoryRepository.save(any(Category.class))).then(invocation -> {
            Object[] args = invocation.getArguments();
            //then
            Assert.assertEquals(args[0], cat);
            return args[0];
        });

        //when
        categoryService.addCategory(cat);
    }
}
