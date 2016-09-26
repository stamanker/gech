package com;

import com.firebase.geofire.GeoLocation;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * diagram: https://www.draw.io/?state=%7B%22ids%22:%5B%220B7DbFi6P344BbEpnbDNFWnBNaTA%22%5D,%22action%22:%22open%22,%22userId%22:%22105295493174936805218%22%7D#G0B7DbFi6P344BbEpnbDNFWnBNaTA
 * http://localhost:8080/api/v1/test?msg=a
 * http://localhost:8080/api/v1/msg?data=a&key=aaa
 */
@Path("/api/v1")
@Produces(MediaType.APPLICATION_JSON)
public class Main extends Application<Conf> {

    private static final Logger log = LogManager.getLogger(Main.class);

    GeoDB geoDB;

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
    @Path("/location")
    public Resp location(@QueryParam("key") String key, @QueryParam("lat") String lat, @QueryParam("lon") String lon) {
        if (key==null || key.isEmpty()) {
            throw new WebApplicationException("Key required", Response.Status.BAD_REQUEST);
        }
        try {
            double latitude  = Double.parseDouble(lat);
            double longitude = Double.parseDouble(lon);
            if (!GeoLocation.coordinatesValid(latitude, longitude)) {
                throw new IllegalArgumentException("Not a valid geo location: " + latitude + ", " + longitude);
            }
        } catch (Exception e) {
            throw new WebApplicationException("Not a valid geo location: " + lat + ", " + lon, Response.Status.BAD_REQUEST);
        }
        return new Resp(1, "test...");
    }

    @GET
    @Path("/test")
    public Resp msg2(@QueryParam("msg") String msg) {
        return new Resp(0, "Test passed. Received: " + msg);
    }

    @Override
    public void run(Conf conf, Environment environment) throws Exception {
        log.debug("conf = [" + conf + "], environment = [" + environment + "]");
        environment.jersey().register(this);
        geoDB = new GeoDB(conf.dbUrl, conf.filePath).init();
    }

    @Override
    public void initialize(Bootstrap<Conf> bootstrap) {
        bootstrap.addCommand(new MyCommand());
    }

    public static void main(String[] args) throws Exception {
        LogManager.getLogger("MAIN").info("Starting...");
        new Main().run(args);
    }
}
