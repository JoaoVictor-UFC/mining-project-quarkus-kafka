package org.br.mining.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.br.mining.dto.CurrencyPriceDTO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@ApplicationScoped
@RegisterRestClient(baseUri = "https://economia.awesomeapi.com.br")
public interface CurrencyPriceClient {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/last/{pair}")
    CurrencyPriceDTO getPriceByPair(@PathParam("pair") String pair);
}
