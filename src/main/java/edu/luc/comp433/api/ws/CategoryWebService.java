package edu.luc.comp433.api.ws;

import edu.luc.comp433.api.representation.CategoryRepresentation;
import edu.luc.comp433.api.representation.CategoryRequest;

import javax.jws.WebService;
import java.util.List;

@WebService
public interface CategoryWebService {

    CategoryRepresentation getCategory(long id);

    CategoryRepresentation createCategory(CategoryRequest categoryRequest);

    List<CategoryRepresentation> allCategories();

    CategoryRepresentation updateCategory(long id, CategoryRequest categoryRequest);

    void deleteCategory(long id);


}