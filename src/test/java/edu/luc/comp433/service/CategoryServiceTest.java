package edu.luc.comp433.service;


import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
public class CategoryServiceTest {



   /* @Configuration
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
    }*/
}
