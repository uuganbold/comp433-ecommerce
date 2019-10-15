package edu.luc.comp433.business;

import edu.luc.comp433.business.dto.ProductDTO;
import edu.luc.comp433.exceptions.DuplicatedEntryException;
import edu.luc.comp433.exceptions.EntryNotFoundException;
import edu.luc.comp433.exceptions.NotRemovableException;

import java.util.List;

public interface ProductService {

    List<ProductDTO> search(String query);

    ProductDTO getProduct(long id);

    ProductDTO createProduct(ProductDTO productDTO) throws DuplicatedEntryException;

    ProductDTO save(ProductDTO dto) throws EntryNotFoundException, DuplicatedEntryException;

    List<ProductDTO> list();

    void delete(long id) throws EntryNotFoundException, NotRemovableException;
}
