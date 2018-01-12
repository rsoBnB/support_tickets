package si.fri.rso.rsobnb.reviews;

import org.eclipse.persistence.annotations.UuidGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "reviews")
@NamedQueries(value =
        {
                @NamedQuery(name = "Review.getAll", query = "SELECT o FROM reviews o"),
                @NamedQuery(name = "Review.findByUser", query = "SELECT o FROM reviews o WHERE o.userId = " +
                        ":userId")
        })
@UuidGenerator(name = "idGenerator")
public class Review {

    @Id
    @GeneratedValue(generator = "idGenerator")
    private String id;

    private String title;

    private String description;

    private Date submitted;

    private double stars;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "realestate_id")
    private String realEstateId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Date submitted) {
        this.submitted = submitted;
    }

}
