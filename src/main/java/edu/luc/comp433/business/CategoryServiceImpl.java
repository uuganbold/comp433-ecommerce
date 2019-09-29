package edu.luc.comp433.business;

import edu.luc.comp433.model.Category;
import edu.luc.comp433.persistence.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void addCategory(Category category) {
        repository.save(category);
    }

    @Transactional
    public void removeCategory(Long id) {
        repository.deleteById(id);
    }
}
