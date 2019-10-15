package edu.luc.comp433.api.workflow;

import edu.luc.comp433.api.payload.ReviewRepresentation;
import edu.luc.comp433.api.payload.ReviewRequest;
import edu.luc.comp433.business.ReviewService;
import edu.luc.comp433.business.dto.ReviewDTO;
import edu.luc.comp433.exceptions.EntryNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReviewActivity {

    private ReviewService reviewService;

    public ReviewActivity(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    public ReviewRepresentation getReview(long id) {
        ReviewDTO dto = reviewService.getReview(id);
        if (dto == null) throw new EntryNotFoundException("Review not found with this id:" + id);
        return ReviewRepresentation.of(dto);
    }

    public ReviewRepresentation createReview(ReviewRequest reviewRequest) {
        ReviewDTO dto = reviewService.createReview(reviewRequest.toDTO());
        return ReviewRepresentation.of(dto);
    }

    public ReviewRepresentation update(long id, ReviewRequest reviewRequest) {
        ReviewDTO dto = reviewRequest.toDTO();
        dto.setId(id);
        dto = reviewService.save(dto);
        return ReviewRepresentation.of(dto);
    }

    public List<ReviewRepresentation> list() {
        List<ReviewDTO> list = reviewService.list();
        List<ReviewRepresentation> result = new ArrayList<>();
        list.forEach(dto -> result.add(ReviewRepresentation.of(dto)));
        return result;
    }

    public void delete(long id) {
        reviewService.delete(id);
    }
}
