package edu.luc.comp433.api.spring;

import edu.luc.comp433.api.payload.ReviewRepresentation;
import edu.luc.comp433.api.payload.ReviewRequest;
import edu.luc.comp433.api.workflow.ReviewActivity;
import edu.luc.comp433.api.ws.ReviewWebService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ReviewRestController implements ReviewWebService {

    private ReviewActivity reviewActivity;

    public ReviewRestController(ReviewActivity reviewActivity) {
        this.reviewActivity = reviewActivity;
    }

    @Override
    @GetMapping(value = "/review/{id}", produces = {"application/hal+json"})
    public ReviewRepresentation getReview(@PathVariable long id) {
        return withLinks(reviewActivity.getReview(id));
    }

    @Override
    @PostMapping(value = "/reviews", consumes = {"application/json"}, produces = {"application/hal+json"})
    public ReviewRepresentation createReview(@RequestBody @Validated ReviewRequest ReviewRequest) {
        return withLinks(reviewActivity.createReview(ReviewRequest));
    }

    @Override
    @PutMapping(value = "/review/{id}", consumes = {"application/json"}, produces = {"application/hal+json"})
    public ReviewRepresentation updateReview(@PathVariable long id, @RequestBody @Validated ReviewRequest reviewRequest) {
        return withLinks(reviewActivity.update(id, reviewRequest));
    }

    @Override
    @GetMapping(value = "/reviews", produces = {"application/hal+json"})
    public List<ReviewRepresentation> allReviews() {
        return withLinks(reviewActivity.list());
    }

    @Override
    @DeleteMapping("/review/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@PathVariable long id) {
        reviewActivity.delete(id);
    }

    protected ReviewRepresentation withLinks(ReviewRepresentation review) {
        review.add(linkTo(methodOn(ReviewRestController.class).getReview(review.getId())).withSelfRel());
        review.add(linkTo(methodOn(ReviewRestController.class).allReviews()).withRel("all"));
        review.add(linkTo(methodOn(ProductRestController.class).getProduct(review.getProduct().getId())).withRel("product"));
        review.add(linkTo(methodOn(CustomerRestController.class).getCustomer(review.getCustomer().getId())).withRel("customer"));
        return review;
    }

    protected List<ReviewRepresentation> withLinks(List<ReviewRepresentation> reviews) {
        reviews.forEach(this::withLinks);
        return reviews;
    }
}
