package edu.luc.comp433.business;

import edu.luc.comp433.model.OrderItem;
import edu.luc.comp433.model.Product;
import edu.luc.comp433.model.Seller;

public interface SellerService {

    void addSeller(Seller seller);

    void removeSeller(Long id);

    void addProduct(Seller seller, Product product);

    void notifySales(Seller seller, OrderItem item);
}
