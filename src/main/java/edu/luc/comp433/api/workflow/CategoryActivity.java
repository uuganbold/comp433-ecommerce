package edu.luc.comp433.api.workflow;

import edu.luc.comp433.api.payload.CategoryRepresentation;
import edu.luc.comp433.api.payload.CategoryRequest;
import edu.luc.comp433.business.CategoryService;
import edu.luc.comp433.business.dto.CategoryDTO;
import edu.luc.comp433.exceptions.DuplicatedEntryException;
import edu.luc.comp433.exceptions.EntryNotFoundException;
import edu.luc.comp433.exceptions.NotRemovableException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryActivity {

    private CategoryService categoryService;

    public CategoryActivity(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public CategoryRepresentation getCategory(long id) {
        CategoryDTO dto = categoryService.getCategory(id);
        if (dto == null) return null;
        return new CategoryRepresentation(dto.getId(), dto.getName());
    }

    public CategoryRepresentation createCategory(CategoryRequest categoryRequest) throws DuplicatedEntryException {
        CategoryDTO dto = categoryService.createCategory(new CategoryDTO(categoryRequest.getName()));
        return new CategoryRepresentation(dto.getId(), dto.getName());
    }

    public CategoryRepresentation update(long id, CategoryRequest categoryRequest) throws EntryNotFoundException, DuplicatedEntryException {
        CategoryDTO dto = new CategoryDTO(id, categoryRequest.getName());
        categoryService.save(dto);
        return new CategoryRepresentation(dto.getId(), dto.getName());
    }

    public List<CategoryRepresentation> list() {
        List<CategoryDTO> list = categoryService.list();
        List<CategoryRepresentation> result = new ArrayList<>();
        list.forEach(dto -> result.add(new CategoryRepresentation(dto.getId(), dto.getName())));
        return result;
    }

    public void delete(long id) throws EntryNotFoundException, NotRemovableException {
        categoryService.delete(id);
    }
}
