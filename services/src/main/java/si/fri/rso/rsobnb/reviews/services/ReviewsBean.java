package si.fri.rso.rsobnb.reviews.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.rsobnb.reviews.Review;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class ReviewsBean {

    private Logger log = Logger.getLogger(ReviewsBean.class.getName());

    @Inject
    private EntityManager em;

    public List<Review> getReviews(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery())
                .defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, Review.class, queryParameters);

    }

    public Review getReview(String reviewId) {

        Review review = em.find(Review.class, reviewId);

        if (review == null) {
            throw new NotFoundException();
        }

        return review;
    }

    public Review createReview(Review review) {

        try {
            beginTx();
            em.persist(review);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return review;
    }

    public Review putReview(String reviewId, Review review) {

        Review c = em.find(Review.class, reviewId);

        if (c == null) {
            return null;
        }

        try {
            beginTx();
            review.setId(c.getId());
            review = em.merge(review);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return review;
    }

    /*
    public void setReviewStatus(String reviewId, String status) {

        Review review = em.find(Review.class, reviewId);

        if (review == null) {
            throw new NotFoundException();
        }

        try {
            beginTx();
            review.setStatus(status);
            review = em.merge(review);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

    }
    */

    public boolean deleteReview(String reviewId) {

        Review review = em.find(Review.class, reviewId);

        if (review != null) {
            try {
                beginTx();
                em.remove(review);
                commitTx();
            } catch (Exception e) {
                rollbackTx();
            }
        } else
            return false;

        return true;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive())
            em.getTransaction().begin();
    }

    private void commitTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().commit();
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive())
            em.getTransaction().rollback();
    }
}
