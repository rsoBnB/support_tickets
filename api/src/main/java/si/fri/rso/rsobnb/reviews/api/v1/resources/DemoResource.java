package si.fri.rso.rsobnb.reviews.api.v1.resources;

import com.kumuluz.ee.common.runtime.EeRuntime;
import com.kumuluz.ee.logs.cdi.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import si.fri.rso.rsobnb.reviews.api.v1.configuration.RestProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@Path("demo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@Log
public class DemoResource {

    private Logger log = Logger.getLogger(DemoResource.class.getName());

    @Inject
    private RestProperties restProperties;

    @GET
    @Path("instanceid")
    public Response getInstanceId() {

        String instanceId =
                "{\"instanceId\" : \"" + EeRuntime.getInstance().getInstanceId() + "\"}";

        return Response.ok(instanceId).build();
    }

    @POST
    @Path("healthy")
    public Response setHealth(Boolean healthy) {
        restProperties.setHealthy(healthy);
        log.info("Setting health to " + healthy);
        return Response.ok().build();
    }

    @POST
    @Path("load")
    public Response loadOrder(Integer n) {

        for (int i = 1; i <= n; i++) {
            fibonacci(i);
        }

        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("info")
    public Response info() {

        JSONObject json = new JSONObject();

        JSONArray clani = new JSONArray();
        clani.put("jm1234");

        JSONArray mikrostoritve = new JSONArray();
        mikrostoritve.put("http://35.189.96.119:8081/v1/orders");

        JSONArray github = new JSONArray();
        github.put("https://github.com/jmezna/rso-customers");

        JSONArray travis = new JSONArray();
        travis.put("https://travis-ci.org/jmezna/rso-customers");

        JSONArray dockerhub = new JSONArray();
        dockerhub.put("https://hub.docker.com/r/jmezna/rso-customers");

        json.put("clani", clani);
        json.put("opis_projekta", "Nas projekt implementira aplikacijo za oddajo nepremicnin.");
        json.put("mikrostoritve", mikrostoritve);
        json.put("github", github);
        json.put("travis", travis);
        json.put("dockerhub", dockerhub);

        return Response.ok(json.toString()).build();
    }

    private long fibonacci(int n) {
        if (n <= 1) return n;
        else return fibonacci(n - 1) + fibonacci(n - 2);
    }
}
