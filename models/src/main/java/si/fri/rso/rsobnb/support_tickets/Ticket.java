package si.fri.rso.rsobnb.support_tickets;

import org.eclipse.persistence.annotations.UuidGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "tickets")
@NamedQueries(value =
        {
                @NamedQuery(name = "Ticket.getAll", query = "SELECT o FROM tickets o"),
                @NamedQuery(name = "Ticket.findByUser", query = "SELECT o FROM tickets o WHERE o.userId = " +
                        ":userId")
        })
@UuidGenerator(name = "idGenerator")
public class Ticket {

    @Id
    @GeneratedValue(generator = "idGenerator")
    private String id;

    private String title;

    private String description;

    private Date submitted;

    private boolean solved;

    @Column(name = "user_id")
    private String userId;

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

    public boolean getSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }
}
