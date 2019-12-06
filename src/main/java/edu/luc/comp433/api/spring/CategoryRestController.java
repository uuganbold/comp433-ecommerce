package edu.luc.comp433.api.spring;

import edu.luc.comp433.api.payload.CategoryRepresentation;
import edu.luc.comp433.api.payload.CategoryRequest;
import edu.luc.comp433.api.workflow.CategoryActivity;
import edu.luc.comp433.api.ws.CategoryWebService;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CategoryRestController implements CategoryWebService {

    private CategoryActivity categoryActivity;

    public CategoryRestController(CategoryActivity categoryActivity) {
        this.categoryActivity = categoryActivity;
    }

    @Override
    @GetMapping(value = "/category/{id}", produces = {"application/hal+json"})
    public CategoryRepresentation getCategory(@PathVariable long id) {
        CategoryRepresentation categoryRepresentation=categoryActivity.getCategory(id);
        return withLinks(categoryRepresentation);
    }

    @Override
    @PostMapping(value = "/categories", consumes = {"application/json"}, produces = {"application/hal+json"})
    public CategoryRepresentation createCategory(@RequestBody @Validated CategoryRequest categoryRequest) {
        CategoryRepresentation category = categoryActivity.createCategory(categoryRequest);
        return withLinks(category);
    }

    @Override
    @PutMapping(value = "/category/{id}", consumes = {"application/json"}, produces = {"application/hal+json"})
    public CategoryRepresentation updateCategory(@PathVariable long id, @RequestBody @Validated CategoryRequest categoryRequest) {
        CategoryRepresentation category = categoryActivity.update(id, categoryRequest);
        return withLinks(category);
    }

    @Override
    @GetMapping(value = "/categories", produces = {"application/hal+json"})
    public List<CategoryRepresentation> allCategories() {
        return withLinks(categoryActivity.list());
    }

    @Override
    @DeleteMapping("/category/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable long id) {
            categoryActivity.delete(id);
    }

    protected CategoryRepresentation withLinks(CategoryRepresentation category) {
        Link selfLink = linkTo(methodOn(CategoryRestController.class).getCategory(category.getId())).withSelfRel();
        category.add(selfLink);
        category.add(linkTo(methodOn(CategoryRestController.class).allCategories()).withRel("all"));
        return category;
    }

    protected List<CategoryRepresentation> withLinks(List<CategoryRepresentation> categories) {
        categories.forEach(this::withLinks);
        return categories;
    }


}
