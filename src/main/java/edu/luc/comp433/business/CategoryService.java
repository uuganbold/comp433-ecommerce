package edu.luc.comp433.business;

import edu.luc.comp433.business.dto.CategoryDTO;
import edu.luc.comp433.exceptions.DuplicatedEntryException;
import edu.luc.comp433.exceptions.EntryNotFoundException;
import edu.luc.comp433.exceptions.NotRemovableException;

import java.util.List;

public interface CategoryService {

    CategoryDTO getCategory(long id);

    CategoryDTO createCategory(CategoryDTO categoryDTO) throws DuplicatedEntryException;

    void save(CategoryDTO dto) throws EntryNotFoundException, DuplicatedEntryException;

    List<CategoryDTO> list();

    void delete(long id) throws EntryNotFoundException, NotRemovableException;
}
