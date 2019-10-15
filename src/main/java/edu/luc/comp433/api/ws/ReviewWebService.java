package edu.luc.comp433.api.ws;

import edu.luc.comp433.api.payload.ReviewRepresentation;
import edu.luc.comp433.api.payload.ReviewRequest;

import javax.jws.WebService;
import java.util.List;

@WebService
public interface ReviewWebService {

    ReviewRepresentation getReview(long id);

    ReviewRepresentation createReview(ReviewRequest reviewRequest);

    List<ReviewRepresentation> allReviews();

    ReviewRepresentation updateReview(long id, ReviewRequest reviewRequest);

    void deleteReview(long id);
}
