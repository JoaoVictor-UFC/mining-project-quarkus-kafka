package org.br.mining.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.br.mining.dto.ProposalDetailsDTO;
import org.br.mining.message.KafkaEvent;
import org.br.mining.service.ProposalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api/proposal")
public class ProposalController {

    private final Logger logger = LoggerFactory.getLogger(KafkaEvent.class);

    @Inject
    ProposalService proposalService;

    @GET
    @Path("/{id}")
    public ProposalDetailsDTO findDetailsProposal(@PathParam("id") Long id){
        return proposalService.findFullProposal(id);
    }

    @POST
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
    public Response removeProposal(@PathParam("id") Long id){

        try {
            proposalService.removeProposal(id);
            return Response.ok().build();
        }catch (Exception e){
            return Response.serverError().build();
        }
    }
}
