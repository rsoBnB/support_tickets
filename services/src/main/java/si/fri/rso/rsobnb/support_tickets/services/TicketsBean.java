package si.fri.rso.rsobnb.support_tickets.services;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.rsobnb.support_tickets.Ticket;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class TicketsBean {

    private Logger log = Logger.getLogger(TicketsBean.class.getName());

    @Inject
    private EntityManager em;

    public List<Ticket> getTickets(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery())
                .defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, Ticket.class, queryParameters);

    }

    public Ticket getTicket(String ticketId) {

        Ticket ticket = em.find(Ticket.class, ticketId);

        if (ticket == null) {
            throw new NotFoundException();
        }

        return ticket;
    }

    public Ticket createTicket(Ticket ticket) {

        try {
            beginTx();
            em.persist(ticket);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return ticket;
    }

    public Ticket putTicket(String ticketId, Ticket ticket) {

        Ticket c = em.find(Ticket.class, ticketId);

        if (c == null) {
            return null;
        }

        try {
            beginTx();
            ticket.setId(c.getId());
            ticket = em.merge(ticket);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return ticket;
    }

    public Ticket setTicketStatus(String ticketId, boolean status) {

        Ticket ticket = em.find(Ticket.class, ticketId);

        if (ticket == null) {
            throw new NotFoundException();
        }

        try {
            beginTx();
            ticket.setSolved(status);
            ticket = em.merge(ticket);
            commitTx();
        } catch (Exception e) {
            rollbackTx();
        }

        return ticket;
    }

    public boolean deleteTicket(String ticketId) {

        Ticket ticket = em.find(Ticket.class, ticketId);

        if (ticket != null) {
            try {
                beginTx();
                em.remove(ticket);
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
