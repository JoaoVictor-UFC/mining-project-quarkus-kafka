package org.br.mining.controller;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.ServerErrorException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.br.mining.dto.OpportunityDTO;
import org.br.mining.service.OpportunityService;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.time.LocalDate;
import java.util.List;

@Path("/api/opportunity")
@Authenticated
public class OpportunityController {

    private final OpportunityService opportunityService;

    @Inject
    JsonWebToken jwtToken;

    @Inject
    public OpportunityController(OpportunityService opportunityService) {
        this.opportunityService = opportunityService;
    }

    @GET
    @Path("/data")
    @RolesAllowed({"user", "manager"})
    public List<OpportunityDTO> generateReportInfos(){
        return opportunityService.generateOpportunityData();
    }
}
