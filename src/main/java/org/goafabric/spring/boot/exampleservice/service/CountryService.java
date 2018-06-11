package org.goafabric.spring.boot.exampleservice.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by andreas.mautsch on 08.06.2018.
 */

@Path(CountryService.RESOURCE)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface CountryService {
    String RESOURCE = "/countries";

    @Path("/")
    //@Path("{orderId}")
    @GET
    void getCountry();
}
