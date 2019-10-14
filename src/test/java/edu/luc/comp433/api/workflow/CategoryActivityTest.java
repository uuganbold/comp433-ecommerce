package edu.luc.comp433.api.workflow;

import edu.luc.comp433.api.payload.CategoryRepresentation;
import edu.luc.comp433.api.payload.CategoryRequest;
import edu.luc.comp433.business.CategoryService;
import edu.luc.comp433.business.dto.CategoryDTO;
import edu.luc.comp433.exceptions.DuplicatedEntryException;
import edu.luc.comp433.exceptions.EntryNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CategoryActivityTest {

    @Configuration
    static class ContextConfiguration {

        @Bean
        public CategoryActivity categoryActivity() {
            return new CategoryActivity(categoryService());
        }

        @Bean
        public CategoryService categoryService() {
            return mock(CategoryService.class);
        }
    }

    @Autowired
    private CategoryActivity categoryActivity;

    @Autowired
    private CategoryService categoryService;

    @Test
    void shouldThrownExceptionWhenCategoryNotFound() {
        //given
        long id = 1;
        when(categoryService.getCategory(id)).thenReturn(null);

        //when
        assertThrows(EntryNotFoundException.class, () -> categoryActivity.getCategory(id));

        reset(categoryService);
    }

    @Test
    void shouldReturnWhatGiven() {
        //given
        long id = 1;
        String name = "Test name";
        when(categoryService.getCategory(id)).thenReturn(new CategoryDTO(id, name));

        //when
        CategoryRepresentation categoryRepresentation = categoryActivity.getCategory(id);

        //then
        assertEquals(id, categoryRepresentation.getId());
        assertEquals(name, categoryRepresentation.getName());
        reset(categoryService);
    }

    @Test
    void shouldNotBeDuplicated() throws DuplicatedEntryException {
        //given
        when(categoryService.createCategory(any(CategoryDTO.class))).thenThrow(new DuplicatedEntryException("message"));

        //then
        assertThrows(DuplicatedEntryException.class, () -> categoryActivity.createCategory(new CategoryRequest("Test Id")));

        reset(categoryService);
    }

    @Test
    void shouldGiveWhatGet() throws DuplicatedEntryException {
        //given
        long id = 234;
        CategoryRequest categoryRequest = new CategoryRequest("Test Name");
        when(categoryService.createCategory(any(CategoryDTO.class))).then(invocation -> {
            CategoryDTO dto = (CategoryDTO) invocation.getArguments()[0];
            dto.setId(id);
            return dto;
        });

        //when
        CategoryRepresentation categoryRepresentation = categoryActivity.createCategory(categoryRequest);

        //then
        assertEquals(categoryRequest.getName(), categoryRepresentation.getName());
        assertEquals(id, categoryRepresentation.getId());
        reset(categoryService);
    }

    @Test
    void shouldGiveWhatGetWhenUpdate() throws EntryNotFoundException, DuplicatedEntryException {
        //given
        long id = 1;
        String name = "Some name";
        CategoryRequest request = new CategoryRequest(name);

        ArgumentCaptor valueCapture = ArgumentCaptor.forClass(CategoryDTO.class);
        doNothing().when(categoryService).save((CategoryDTO) valueCapture.capture());

        //when
        CategoryRepresentation representation = categoryActivity.update(id, request);

        //then
        assertEquals(id, ((CategoryDTO) valueCapture.getValue()).getId());
        assertEquals(name, ((CategoryDTO) valueCapture.getValue()).getName());
        assertEquals(id, representation.getId());
        assertEquals(name, representation.getName());
        reset(categoryService);
    }


}
