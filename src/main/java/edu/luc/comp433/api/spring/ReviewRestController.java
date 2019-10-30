package edu.luc.comp433.api.spring;

import edu.luc.comp433.api.payload.ReviewRepresentation;
import edu.luc.comp433.api.payload.ReviewRequest;
import edu.luc.comp433.api.workflow.ReviewActivity;
import edu.luc.comp433.api.ws.ReviewWebService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewRestController implements ReviewWebService {

    private ReviewActivity reviewActivity;

    public ReviewRestController(ReviewActivity reviewActivity) {
        this.reviewActivity = reviewActivity;
    }

    @Override
    @GetMapping("/review/{id}")
    public ReviewRepresentation getReview(@PathVariable long id) {
        return reviewActivity.getReview(id);
    }

    @Override
    @PostMapping(value = "/reviews", consumes = {"application/json"}, produces = {"application/hal+json"})
    public ReviewRepresentation createReview(@RequestBody @Validated ReviewRequest ReviewRequest) {
        return reviewActivity.createReview(ReviewRequest);
    }

    @Override
    @PutMapping(value = "/review/{id}", consumes = {"application/json"}, produces = {"application/hal+json"})
    public ReviewRepresentation updateReview(@PathVariable long id, @RequestBody @Validated ReviewRequest reviewRequest) {
        return reviewActivity.update(id, reviewRequest);
    }

    @Override
    @GetMapping(value = "/reviews", produces = {"application/hal+json"})
    public List<ReviewRepresentation> allReviews() {
        return reviewActivity.list();
    }

    @Override
    @DeleteMapping("/review/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@PathVariable long id) {
        reviewActivity.delete(id);
    }
}
