package edu.luc.comp433.api.spring;

import edu.luc.comp433.api.payload.CategoryRepresentation;
import edu.luc.comp433.api.payload.CategoryRequest;
import edu.luc.comp433.api.workflow.CategoryActivity;
import edu.luc.comp433.api.ws.CategoryWebService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        return categoryActivity.getCategory(id);
    }

    @Override
    @PostMapping(value = "/categories", consumes = {"text/xml", "application/json"}, produces = {"text/xml", "application/json"})
    public CategoryRepresentation createCategory(@RequestBody CategoryRequest categoryRequest) {
            return categoryActivity.createCategory(categoryRequest);
    }

    @Override
    @PutMapping(value = "/category/{id}", consumes = {"text/xml", "application/json"}, produces = {"text/xml", "application/json"})
    public CategoryRepresentation updateCategory(@PathVariable long id, @RequestBody CategoryRequest categoryRequest) {
            return categoryActivity.update(id, categoryRequest);
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
            categoryActivity.delete(id);
    }
}
