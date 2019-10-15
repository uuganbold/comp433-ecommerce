package edu.luc.comp433.business;

import edu.luc.comp433.business.dto.CustomerDTO;
import edu.luc.comp433.business.dto.ProductDTO;
import edu.luc.comp433.business.dto.ReviewDTO;
import edu.luc.comp433.exceptions.EntryNotFoundException;
import edu.luc.comp433.model.Customer;
import edu.luc.comp433.model.Product;
import edu.luc.comp433.model.Review;
import edu.luc.comp433.persistence.CustomerRepository;
import edu.luc.comp433.persistence.ProductRepository;
import edu.luc.comp433.persistence.ReviewRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;

    private CustomerRepository customerRepository;

    private ProductRepository productRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public ReviewDTO getReview(long id) {
        Review review = reviewRepository.findById(id).orElse(null);
        if (review == null) return null;
        else return ReviewDTO.of(review)
                .setCustomerDTO(CustomerDTO.of(review.getCustomer()))
                .setProductDTO(ProductDTO.of(review.getProduct()));
    }


    @Override
    @Transactional
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Customer customer = customerRepository.findById(reviewDTO.getCustomerDTO().getId())
                .orElseThrow(() -> new EntryNotFoundException("customer not found with this id:" + reviewDTO.getCustomerDTO().getId()));
        Product product = productRepository.findById(reviewDTO.getProductDTO().getId())
                .orElseThrow(() -> new EntryNotFoundException("Product not found with this id:" + reviewDTO.getProductDTO().getId()));

        Review review = reviewDTO.toEntity();
        review.setDate(Instant.now());
        review.setProduct(product);
        review.setCustomer(customer);
        reviewRepository.save(review);
        return ReviewDTO.of(review).setCustomerDTO(CustomerDTO.of(customer)).setProductDTO(ProductDTO.of(product));
    }

    @Override
    @Transactional
    public ReviewDTO save(ReviewDTO reviewDTO) throws EntryNotFoundException {
        Review review = reviewRepository.findById(reviewDTO.getId()).orElseThrow(() -> new EntryNotFoundException("Review not found with id:" + reviewDTO.getId()));
        review.setStar(reviewDTO.getStar());
        review.setComment(reviewDTO.getComment());
        Customer customer = customerRepository.findById(reviewDTO.getCustomerDTO().getId())
                .orElseThrow(() -> new EntryNotFoundException("Customer not found with this id:" + reviewDTO.getCustomerDTO().getId()));
        review.setCustomer(customer);
        Product product = productRepository.findById(reviewDTO.getProductDTO().getId())
                .orElseThrow(() -> new EntryNotFoundException("Product not found with this id:" + reviewDTO.getProductDTO().getId()));
        review.setProduct(product);
        reviewRepository.save(review);
        return ReviewDTO.of(review).setCustomerDTO(CustomerDTO.of(customer)).setProductDTO(ProductDTO.of(product));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDTO> list() {
        List<ReviewDTO> list = new ArrayList<>();
        reviewRepository.findAll().forEach(c -> list.add(ReviewDTO.of(c)));
        return list;
    }

    @Override
    @Transactional
    public void delete(long id) throws EntryNotFoundException {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new EntryNotFoundException("Review not found with id:" + id));
        reviewRepository.delete(review);
    }
}
