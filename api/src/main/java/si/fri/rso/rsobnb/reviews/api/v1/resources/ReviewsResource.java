package si.fri.rso.rsobnb.reviews.api.v1.resources;

import com.kumuluz.ee.logs.cdi.Log;
import org.eclipse.microprofile.metrics.annotation.Metered;
import si.fri.rso.rsobnb.reviews.Review;
import si.fri.rso.rsobnb.reviews.services.ReviewsBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
@Path("/reviews")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log
public class ReviewsResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private ReviewsBean reviewsBean;

    @GET
    @Metered
    public Response getReviews() {

        List<Review> reviews = reviewsBean.getReviews(uriInfo);

        return Response.ok(reviews).build();
    }

    @GET
    @Path("/{reviewId}")
    public Response getReview(@PathParam("reviewId") String reviewId) {

        Review review = reviewsBean.getReview(reviewId);

        if (review == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(review).build();
    }

    @POST
    public Response createReview(Review review) {

        if (review.getTitle() == null || review.getTitle().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            review = reviewsBean.createReview(review);
        }

        if (review.getId() != null) {
            return Response.status(Response.Status.CREATED).entity(review).build();
        } else {
            return Response.status(Response.Status.CONFLICT).entity(review).build();
        }
    }

    @PUT
    @Path("{reviewId}")
    public Response putReview(@PathParam("reviewId") String reviewId, Review review) {

        review = reviewsBean.putReview(reviewId, review);

        if (review == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            if (review.getId() != null)
                return Response.status(Response.Status.OK).entity(review).build();
            else
                return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }

    @DELETE
    @Path("{reviewId}")
    public Response deleteReview(@PathParam("reviewId") String reviewId) {

        boolean deleted = reviewsBean.deleteReview(reviewId);

        if (deleted) {
            return Response.status(Response.Status.GONE).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
