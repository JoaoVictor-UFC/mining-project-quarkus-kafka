package org.br.mining.controller;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.br.mining.dto.ProposalDetailsDTO;
import org.br.mining.message.KafkaEvent;
import org.br.mining.service.ProposalService;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api/proposal")
@Authenticated
public class ProposalController {

    private final Logger logger = LoggerFactory.getLogger(KafkaEvent.class);

    @Inject
    JsonWebToken jwtToken;

    @Inject
    ProposalService proposalService;

    @GET
    @Path("/{id}")
    @RolesAllowed({"user", "manager"})
    public ProposalDetailsDTO findDetailsProposal(@PathParam("id") Long id){
        return proposalService.findFullProposal(id);
    }

    @POST
    @RolesAllowed("proposal-customer")
    public Response createProposal(ProposalDetailsDTO detailsDTO){
        logger.info("--- Receiving purchase proposal ---");

        try {
            proposalService.createNewProposal(detailsDTO);
            return Response.ok().build();

        }catch (Exception e){
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("manager")
    public Response removeProposal(@PathParam("id") Long id){

        try {
            proposalService.removeProposal(id);
            return Response.ok().build();
        }catch (Exception e){
            return Response.serverError().build();
        }
    }
}
