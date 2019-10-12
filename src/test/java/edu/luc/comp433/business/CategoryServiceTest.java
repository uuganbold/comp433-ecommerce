package edu.luc.comp433.business;

import edu.luc.comp433.business.dto.CategoryDTO;
import edu.luc.comp433.exceptions.DuplicatedEntryException;
import edu.luc.comp433.exceptions.EntryNotFoundException;
import edu.luc.comp433.exceptions.NotRemovableException;
import edu.luc.comp433.model.Category;
import edu.luc.comp433.persistence.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CategoryServiceTest {

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
    void shouldReturnNullWhenNotFound() {
        //Given
        long id = 134563456;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        //When
        CategoryDTO dto = categoryService.getCategory(id);

        //then
        assertNull(dto);
        reset(categoryRepository);
    }

    @Test
    void shouldReturnDtoWithSameContent() {
        //Given
        long id = 2;
        String name = "TestName";

        when(categoryRepository.findById(id)).then(invocation -> {
            Object[] args = invocation.getArguments();

            return Optional.of(new Category(id, name));
        });

        //when
        CategoryDTO dto = categoryService.getCategory(id);

        //then
        assertEquals(id, dto.getId());
        assertEquals(name, dto.getName());
        reset(categoryRepository);
    }

    @Test
    void shouldNotBeDuplicated() {
        //Given
        String message = "Category cannot be duplicated";
        when(categoryRepository.save(any(Category.class))).thenThrow(new DataIntegrityViolationException(message));

        //then
        assertThrows(DuplicatedEntryException.class, () -> {
            //when
            categoryService.createCategory(new CategoryDTO("SomeName"));
        });
        reset(categoryRepository);
    }

    @Test
    void shouldHaveId() throws DuplicatedEntryException {
        //given
        long id = 1324;
        String name = "Test name";
        CategoryDTO categoryDTO = new CategoryDTO(name);
        when(categoryRepository.save(any(Category.class))).then(invocation -> {
            Object[] args = invocation.getArguments();
            Category c = (Category) args[0];

            //then
            assertEquals(name, c.getName());
            c.setId(id);

            return c;
        });

        //when
        CategoryDTO dto = categoryService.createCategory(categoryDTO);

        //then
        assertEquals(id, categoryDTO.getId());
        assertEquals(categoryDTO, dto);
        reset(categoryRepository);
    }

    @Test
    void shouldMakeExceptionWhenNotFound() throws EntryNotFoundException, DuplicatedEntryException {
        //Given
        long id = 1;
        String name = "Test Category";
        CategoryDTO dto = new CategoryDTO(id, name);
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        //when then
        assertThrows(EntryNotFoundException.class, () -> {
            categoryService.save(dto);
        });
        reset(categoryRepository);
    }

    @Test
    void shouldMakeExceptionWhenDuplicatedName() throws EntryNotFoundException, DuplicatedEntryException {
        //given
        long id = 1;
        String name = "Test Category";
        CategoryDTO dto = new CategoryDTO(id, name);
        when(categoryRepository.findById(id)).thenReturn(Optional.of(new Category(id, name)));

        when(categoryRepository.save(any(Category.class))).thenThrow(DataIntegrityViolationException.class);

        //when
        assertThrows(DuplicatedEntryException.class, () -> categoryService.save(dto));
        reset(categoryRepository);
    }

    @Test
    void shouldGiveWhatGet() throws EntryNotFoundException, DuplicatedEntryException {
        //Given
        long id = 14536346;
        String name = "Test Category";
        CategoryDTO dto = new CategoryDTO(id, name);

        when(categoryRepository.findById(id)).thenReturn(Optional.of(new Category(id, name)));

        when(categoryRepository.save(any(Category.class))).then(invocation -> {
            Category c = (Category) invocation.getArguments()[0];

            //then
            assertEquals(id, c.getId());
            assertEquals(name, c.getName());
            return c;
        });

        //when
        categoryService.save(dto);
        reset(categoryRepository);
    }

    @Test
    void shouldThrowErrorWhenNotFound() {
        //given
        long id = 17686789;

        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        //then
        assertThrows(EntryNotFoundException.class, () -> {
            //when
            categoryService.delete(id);
        });
        reset(categoryRepository);

    }

    @Test
    void shouldGiveWhatGetWhenDeleting() throws EntryNotFoundException, NotRemovableException {
        //given
        long id = 132;
        Category c = new Category(id, "TestName");

        when(categoryRepository.findById(id)).thenReturn(Optional.of(c));

        ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
        doNothing().when(categoryRepository).delete(captor.capture());

        //when
        categoryService.delete(id);


        //then
        assertEquals(c, captor.getValue());
        reset(categoryRepository);
    }
}
