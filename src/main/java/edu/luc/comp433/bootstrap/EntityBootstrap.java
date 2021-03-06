package edu.luc.comp433.bootstrap;

import edu.luc.comp433.model.Address;
import edu.luc.comp433.model.Category;
import edu.luc.comp433.model.Product;
import edu.luc.comp433.model.Seller;
import edu.luc.comp433.persistence.CategoryRepository;
import edu.luc.comp433.persistence.ProductRepository;
import edu.luc.comp433.persistence.SellerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class EntityBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private long start = 0;

    private final CategoryRepository categoryRepository;

    private final SellerRepository sellerRepository;

    private final ProductRepository productRepository;

    public EntityBootstrap(CategoryRepository categoryRepository, SellerRepository sellerRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ++start;
        if (start > 1) return;
        Category cat1 = new Category("Clothes");
        Category cat2 = new Category("Books");
        Category cat3 = new Category("Electronics");
        List<Category> categories = new ArrayList<>();
        categories.add(cat1);
        categories.add(cat2);
        categories.add(cat3);
        categoryRepository.saveAll(categories);

        Seller seller1 = new Seller();
        seller1.setName("Levi Strauss");
        Address address1 = new Address(null, "United States", "1155 Battery Street", null, "San Francisco", "CA", 94111, "1-866-290-6064");
        seller1.getAddresses().add(address1);

        Seller seller2 = new Seller();
        seller2.setName("Dell");
        seller2.setWebsite("http://www.dell.com");
        seller2.setEmail("support@dell.com");
        Address address2 = new Address(null, "United States", "1 Dell Way", null, "Round Rock", "TX", 78682, "1-866-931-3355");
        seller2.getAddresses().add(address2);


        List<Seller> sellers = new ArrayList<>();
        sellers.add(seller1);
        sellers.add(seller2);
        sellerRepository.saveAll(sellers);

        Product product1 = new Product();
        product1.setName("Signature by Levi Strauss & Co. Gold Label Women's Totally Shaping Pull-on Skinny Jeans");
        product1.setDescription("The Signature by Levi Strauss & Co.™ Women’s Shaping Pull-On Skinny jeans makes even relaxing look stylish. Prioritizing fashion and function just the same, this skinny fit pair is made of ultra-comfortable denim that stretches to gently hug and enhance your silhouette. On top of that, we’ve added a pull-on waistband that’s been reengineered to host a tummy slimming panel inside. Body contouring skinny jeans with infinite amounts of comfort — sounds pretty on point to us.");
        product1.setAvailableQuantity(1000);
        product1.setListPrice(29.99);
        product1.setCategory(cat1);
        product1.setSeller(seller1);

        Product product2 = new Product();
        product2.setName("New Dell Latitude 3190 Laptop - w/Free pre-Installed Office Professional Software/Windows 10 Pro");
        product2.setDescription("Brand new in the box... Dell Latitude 3190 Laptop with pre-installed 2010 Microsoft Office Professional Plus (Full Version not a trial). Intel Processor, 8Gb RAM, 128GB SSD Hard Drive, 11.6 inch LCD screen, Windows 10 Pro, built in wireless internet card and built in webcam");
        product2.setAvailableQuantity(210);
        product2.setListPrice(499.99);
        product2.setCategory(cat3);
        product2.setSeller(seller2);

        productRepository.save(product1);
        productRepository.save(product2);
    }
}
