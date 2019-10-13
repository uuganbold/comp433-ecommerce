package edu.luc.comp433.api.spring;

import edu.luc.comp433.api.payload.CategoryRepresentation;
import edu.luc.comp433.api.payload.CategoryRequest;
import edu.luc.comp433.api.workflow.CategoryActivity;
import edu.luc.comp433.api.ws.CategoryWebService;
import edu.luc.comp433.exceptions.DuplicatedEntryException;
import edu.luc.comp433.exceptions.EntryNotFoundException;
import edu.luc.comp433.exceptions.NotRemovableException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CategoryRestController implements CategoryWebService {

    private CategoryActivity categoryActivity;

    public CategoryRestController(CategoryActivity categoryActivity) {
        this.categoryActivity = categoryActivity;
    }

    @Override
    @GetMapping("/category/{id}")
    public CategoryRepresentation getCategory(@PathVariable long id) {
        CategoryRepresentation categoryRepresentation = categoryActivity.getCategory(id);
        if (categoryRepresentation == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
        return categoryRepresentation;
    }

    @Override
    @PostMapping(value = "/categories", consumes = {"text/xml", "application/json"})
    public CategoryRepresentation createCategory(@RequestBody CategoryRequest categoryRequest) {
        try {
            return categoryActivity.createCategory(categoryRequest);
        } catch (DuplicatedEntryException dee) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Duplicated Entry Not Allowed", dee);
        }
    }

    @Override
    @PutMapping(value = "/category/{id}", consumes = {"text/xml", "application/json"})
    public CategoryRepresentation updateCategory(@PathVariable long id, @RequestBody CategoryRequest categoryRequest) {
        try {
            return categoryActivity.update(id, categoryRequest);
        } catch (EntryNotFoundException enf) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, enf.getMessage());
        } catch (DuplicatedEntryException dee) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, dee.getMessage());
        }

    }

    @Override
    @GetMapping(value = "/categories", produces = {"text/xml", "application/json"})
    public List<CategoryRepresentation> allCategories() {
        return categoryActivity.list();
    }

    @Override
    @DeleteMapping("/category/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable long id) {
        try {
            categoryActivity.delete(id);
        } catch (EntryNotFoundException enf) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, enf.getMessage());
        } catch (NotRemovableException nre) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, nre.getMessage());
        }

    }
}
