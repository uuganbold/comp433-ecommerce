package edu.luc.comp433.api.cxf;

import edu.luc.comp433.api.payload.ReviewRepresentation;
import edu.luc.comp433.api.payload.ReviewRequest;
import edu.luc.comp433.api.workflow.ReviewActivity;
import edu.luc.comp433.api.ws.ReviewWebService;
import org.apache.cxf.jaxrs.ext.ResponseStatus;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

public class ReviewResource implements ReviewWebService {


    private ReviewActivity reviewActivity;

    public ReviewResource(ReviewActivity reviewActivity) {
        this.reviewActivity = reviewActivity;
    }

    @Override
    @GET
    @Path("/review/{id}")
    @Produces("application/hal+json")
    public ReviewRepresentation getReview(@PathParam("id") long id) {
        return reviewActivity.getReview(id);
    }

    @Override
    @POST
    @Path("/reviews")
    @Consumes({"application/json"})
    @Produces("application/hal+json")
    public ReviewRepresentation createReview(ReviewRequest reviewRequest) {
        return reviewActivity.createReview(reviewRequest);
    }

    @Override
    @PUT
    @Path("/review/{id}")
    @Consumes({"application/json"})
    @Produces("application/hal+json")
    public ReviewRepresentation updateReview(@PathParam("id") long id, ReviewRequest reviewRequest) {
        return reviewActivity.update(id, reviewRequest);
    }

    @Override
    @GET
    @Path(value = "/reviews")
    @Produces("application/hal+json")
    public List<ReviewRepresentation> allReviews() {
        return reviewActivity.list();
    }


    @Override
    @DELETE
    @Path("/review/{id}")
    @ResponseStatus(Response.Status.NO_CONTENT)
    public void deleteReview(@PathParam("id") long id) {
        reviewActivity.delete(id);
    }
}
