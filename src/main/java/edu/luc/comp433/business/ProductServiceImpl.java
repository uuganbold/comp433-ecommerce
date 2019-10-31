package edu.luc.comp433.business;

import edu.luc.comp433.business.dto.CategoryDTO;
import edu.luc.comp433.business.dto.ProductDTO;
import edu.luc.comp433.business.dto.SellerDTO;
import edu.luc.comp433.exceptions.EntryNotFoundException;
import edu.luc.comp433.exceptions.NotRemovableException;
import edu.luc.comp433.model.Category;
import edu.luc.comp433.model.Product;
import edu.luc.comp433.model.Seller;
import edu.luc.comp433.persistence.CategoryRepository;
import edu.luc.comp433.persistence.ProductRepository;
import edu.luc.comp433.persistence.SellerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    private CategoryRepository categoryRepository;

    private SellerRepository sellerRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, SellerRepository sellerRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.sellerRepository = sellerRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> search(String query) {
        List<ProductDTO> list = new ArrayList<>();
        productRepository.findByNameContains(query.trim()).forEach(c -> list.add(ProductDTO.of(c)
                .setCategoryDTO(CategoryDTO.of(c.getCategory()))
                .setSellerDTO(SellerDTO.of(c.getSeller()))));
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getProduct(long id) {
            Product product = productRepository.findById(id).orElse(null);
            if (product == null) return null;
            else return ProductDTO.of(product)
                    .setCategoryDTO(CategoryDTO.of(product.getCategory()))
                    .setSellerDTO(SellerDTO.of(product.getSeller()));

    }


    @Override
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        Category c = categoryRepository.findById(productDTO.getCategoryDTO().getId())
                .orElseThrow(() -> new EntryNotFoundException("category not found with this id:" + productDTO.getCategoryDTO().getId()));
        Seller s = sellerRepository.findById(productDTO.getSellerDTO().getId())
                .orElseThrow(() -> new EntryNotFoundException("Seller not found with this id:" + productDTO.getSellerDTO().getId()));

        Product product = productDTO.toEntity();
        product.setSeller(s);
        product.setCategory(c);
        productRepository.save(product);
        return ProductDTO.of(product).setSellerDTO(SellerDTO.of(s)).setCategoryDTO(CategoryDTO.of(c));
    }

    @Override
    @Transactional
    public ProductDTO save(ProductDTO dto) throws EntryNotFoundException {
        Product product = productRepository.findById(dto.getId()).orElseThrow(() -> new EntryNotFoundException("Product not found with id:" + dto.getId()));
        product.setName(dto.getName());
        product.setAvailableQuantity(dto.getAvailableQuantity());
        product.setListPrice(dto.getListPrice());
        product.setDescription(dto.getDescription());
        Category c = categoryRepository.findById(dto.getCategoryDTO().getId())
                .orElseThrow(() -> new EntryNotFoundException("category not found with this id:" + dto.getCategoryDTO().getId()));
        product.setCategory(c);
        Seller s = sellerRepository.findById(dto.getSellerDTO().getId())
                .orElseThrow(() -> new EntryNotFoundException("Seller not found with this id:" + dto.getSellerDTO().getId()));
        product.setSeller(s);
        productRepository.save(product);
        return ProductDTO.of(product).setCategoryDTO(CategoryDTO.of(c)).setSellerDTO(SellerDTO.of(s));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> list() {
        List<ProductDTO> list = new ArrayList<>();
        productRepository.findAll().forEach(c -> list.add(ProductDTO.of(c).
                setCategoryDTO(CategoryDTO.of(c.getCategory()))
                .setSellerDTO(SellerDTO.of(c.getSeller()))));
        return list;
    }

    @Override
    @Transactional
    public void delete(long id) throws EntryNotFoundException, NotRemovableException {
        Product c = productRepository.findById(id).orElseThrow(() -> new EntryNotFoundException("Product not found with id:" + id));
        try {
            productRepository.delete(c);
        } catch (DataIntegrityViolationException e) {
            throw new NotRemovableException("This product not removable:" + id);
        }

    }
}
