package edu.luc.comp433.business;

import edu.luc.comp433.model.OrderItem;
import edu.luc.comp433.model.Product;
import edu.luc.comp433.model.Seller;
import edu.luc.comp433.persistence.SellerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SellerServiceImpl implements SellerService {

    private SellerRepository sellerRepository;

    private ProductService productService;

    public SellerServiceImpl(SellerRepository sellerRepository, ProductService productService) {
        this.sellerRepository = sellerRepository;
        this.productService = productService;
    }


    public void addSeller(Seller seller) {
        this.sellerRepository.save(seller);
    }

    @Override
    public void removeSeller(Long id) {
        sellerRepository.deleteById(id);
    }

    @Override
    public void addProduct(Seller seller, Product product) {
        product.setSeller(seller);
        productService.addProduct(product);
    }

    @Override
    public void notifySales(Seller seller, OrderItem item) {
        //TODO make some notification.
    }
}
