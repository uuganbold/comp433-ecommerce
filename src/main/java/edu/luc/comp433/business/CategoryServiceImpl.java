package edu.luc.comp433.business;

import edu.luc.comp433.business.dto.CategoryDTO;
import edu.luc.comp433.exceptions.DuplicatedEntryException;
import edu.luc.comp433.exceptions.EntryNotFoundException;
import edu.luc.comp433.exceptions.NotRemovableException;
import edu.luc.comp433.model.Category;
import edu.luc.comp433.persistence.CategoryRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDTO getCategory(long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) return null;
        else return new CategoryDTO(category.getId(), category.getName());
    }


    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) throws DuplicatedEntryException {
        try {
            Category category = new Category(categoryDTO.getName());
            categoryRepository.save(category);
            categoryDTO.setId(category.getId());
            return categoryDTO;
        } catch (DataIntegrityViolationException dive) {
            throw new DuplicatedEntryException("Category cannot have same name", dive);
        }

    }

    @Override
    public void save(CategoryDTO dto) throws EntryNotFoundException, DuplicatedEntryException {
        Category c = categoryRepository.findById(dto.getId()).orElseThrow(() -> new EntryNotFoundException("Category not found with id:" + dto.getId()));
        c.setName(dto.getName());
        try {
            categoryRepository.save(c);
        } catch (DataIntegrityViolationException dive) {
            throw new DuplicatedEntryException("Category cannot have same name", dive);
        }

    }

    @Override
    public List<CategoryDTO> list() {
        List<CategoryDTO> list = new ArrayList<>();
        categoryRepository.findAll().forEach(c -> list.add(new CategoryDTO(c.getId(), c.getName())));
        return list;
    }

    @Override
    public void delete(long id) throws EntryNotFoundException, NotRemovableException {
        Category c = categoryRepository.findById(id).orElseThrow(() -> new EntryNotFoundException("Category not found with id:" + id));
        try {
            categoryRepository.delete(c);
        } catch (DataIntegrityViolationException e) {
            throw new NotRemovableException("This category not removable:" + id);
        }

    }
}
