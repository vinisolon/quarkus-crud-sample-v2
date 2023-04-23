package application.controllers;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/test")
public class TestController {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
        return "It's working!";
    }
}
