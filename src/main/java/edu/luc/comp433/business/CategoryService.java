package edu.luc.comp433.business;

import edu.luc.comp433.model.Category;

public interface CategoryService {

    void addCategory(Category category);

    void removeCategory(Long id);
}
