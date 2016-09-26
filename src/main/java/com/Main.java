package com;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * http://localhost:8080/v1/test?msg=a
 * http://localhost:8080/v1/msg?data=a&key=aaa
 */
@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
public class Main extends Application<GCConfiguration> {

    @GET
    @Path("/msg")
    public Resp msg(@QueryParam("data")String data, @QueryParam("key")String key) {
        Msg msg = new Msg(data, key);
        if(msg.data==null || msg.data.isEmpty()) {
            throw new WebApplicationException("Message is empty", Response.Status.NOT_FOUND);
        }
        return new Resp(1, "ok:" + msg);
    }

    @GET
    @Path("/test")
    public Resp msg2(@QueryParam("msg") String msg) {
        return new Resp(0, "Test passed. Received: " + msg);
    }

    @Override
    public void run(GCConfiguration gcConfiguration, Environment environment) throws Exception {
        System.out.println("...");
        environment.jersey().register(this);
    }

    @Override
    public void initialize(Bootstrap<GCConfiguration> bootstrap) {
        bootstrap.addCommand(new MyCommand());
    }

    public static void main(String[] args) throws Exception {
        new Main().run(args);
    }
}
