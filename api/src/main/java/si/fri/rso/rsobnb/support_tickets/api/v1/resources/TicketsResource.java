package si.fri.rso.rsobnb.support_tickets.api.v1.resources;

import com.kumuluz.ee.logs.cdi.Log;
import org.eclipse.microprofile.metrics.annotation.Metered;
import si.fri.rso.rsobnb.support_tickets.Ticket;
import si.fri.rso.rsobnb.support_tickets.services.TicketsBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@ApplicationScoped
@Path("/support_tickets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Log
public class TicketsResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private TicketsBean ticketsBean;

    @GET
    @Metered
    public Response getTickets() {

        List<Ticket> tickets = ticketsBean.getTickets(uriInfo);

        return Response.ok(tickets).build();
    }

    @GET
    @Path("/{ticketId}")
    public Response getTicket(@PathParam("ticketId") String ticketId) {

        Ticket ticket = ticketsBean.getTicket(ticketId);

        if (ticket == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(ticket).build();
    }

    @POST
    public Response createTicket(Ticket ticket) {

        if (ticket.getTitle() == null || ticket.getTitle().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            ticket = ticketsBean.createTicket(ticket);
        }

        if (ticket.getId() != null) {
            return Response.status(Response.Status.CREATED).entity(ticket).build();
        } else {
            return Response.status(Response.Status.CONFLICT).entity(ticket).build();
        }
    }

    @PUT
    @Path("{ticketId}")
    public Response putTicket(@PathParam("ticketId") String ticketId, Ticket ticket) {

        ticket = ticketsBean.putTicket(ticketId, ticket);

        if (ticket == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            if (ticket.getId() != null)
                return Response.status(Response.Status.OK).entity(ticket).build();
            else
                return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }

    @POST
    @Path("/solved/{ticketId}")
    public Response solveTicket(@PathParam("ticketId") String ticketId) {

        ticketsBean.setTicketStatus(ticketId, true);

        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("{ticketId}")
    public Response deleteTicket(@PathParam("ticketId") String ticketId) {

        boolean deleted = ticketsBean.deleteTicket(ticketId);

        if (deleted) {
            return Response.status(Response.Status.GONE).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
