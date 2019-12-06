package edu.luc.comp433.api.cxf;

import edu.luc.comp433.api.cxf.linkbuilder.LinkBuilder;
import edu.luc.comp433.api.payload.ReviewRepresentation;
import edu.luc.comp433.api.payload.ReviewRequest;
import edu.luc.comp433.api.workflow.ReviewActivity;
import edu.luc.comp433.api.ws.ReviewWebService;
import org.apache.cxf.jaxrs.ext.ResponseStatus;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@CrossOriginResourceSharing(
        allowAllOrigins = true,
        maxAge = 200
)
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
        return withLinks(reviewActivity.getReview(id));
    }

    @Override
    @POST
    @Path("/reviews")
    @Consumes({"application/json"})
    @Produces("application/hal+json")
    public ReviewRepresentation createReview(ReviewRequest reviewRequest) {
        return withLinks(reviewActivity.createReview(reviewRequest));
    }

    @Override
    @PUT
    @Path("/review/{id}")
    @Consumes({"application/json"})
    @Produces("application/hal+json")
    public ReviewRepresentation updateReview(@PathParam("id") long id, ReviewRequest reviewRequest) {
        return withLinks(reviewActivity.update(id, reviewRequest));
    }

    @Override
    @GET
    @Path(value = "/reviews")
    @Produces("application/hal+json")
    public List<ReviewRepresentation> allReviews() {
        return withLinks(reviewActivity.list());
    }


    @Override
    @DELETE
    @Path("/review/{id}")
    @ResponseStatus(Response.Status.NO_CONTENT)
    public void deleteReview(@PathParam("id") long id) {
        reviewActivity.delete(id);
    }

    @Context
    private UriInfo uriInfo;

    protected ReviewRepresentation withLinks(ReviewRepresentation review) {
        review.add(LinkBuilder.get(uriInfo).linkTo(ReviewResource.class, "getReview").withSelfRel().build(review.getId()));
        review.add(LinkBuilder.get(uriInfo).linkTo(ReviewResource.class, "allReviews").withRel("all").build());
        review.add(LinkBuilder.get(uriInfo).linkTo(ProductResource.class, "getProduct").withRel("product").build(review.getProduct().getId()));
        review.add(LinkBuilder.get(uriInfo).linkTo(CustomerResource.class, "getCustomer").withRel("customer").build(review.getCustomer().getId()));
        return review;
    }

    protected List<ReviewRepresentation> withLinks(List<ReviewRepresentation> reviews) {
        reviews.forEach(this::withLinks);
        return reviews;
    }
}
