package edu.luc.comp433.model;

import edu.luc.comp433.persistence.CustomerRepository;
import edu.luc.comp433.persistence.ProductRepository;
import edu.luc.comp433.persistence.ReviewRepository;
import edu.luc.comp433.persistence.SellerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerTest extends DomainModelTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Test
    public void test_CustomerReview_relationship() {
        Customer customer = new Customer("FirstName", "LastName", "test@email.com");
        customerRepository.save(customer);
        Product p = new Product();


        Seller s = new Seller();
        p.setSeller(s);

        Review review = new Review(p, customer, "This is my comment", (short) 4);
        customer.addReview(review);
        p.addReview(review);

        sellerRepository.save(s);

        productRepository.save(p);

        Assertions.assertEquals(1, customerRepository.count());
        Assertions.assertEquals(1, reviewRepository.count());

        Customer cc = customerRepository.findById(1L).get();
        Assertions.assertEquals(cc, customer);

        customerRepository.delete(customer);
        Assertions.assertEquals(0, reviewRepository.count());
        Assertions.assertEquals(1, productRepository.count());
    }

}
