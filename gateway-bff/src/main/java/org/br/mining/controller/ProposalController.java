package org.br.mining.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.br.mining.dto.ProposalDetailsDTO;
import org.br.mining.service.ProposalService;

@ApplicationScoped
@Path("/api/trade")
public class ProposalController {

    @Inject
    ProposalService proposalService;

    @GET
    @Path("/{id}")
    @RolesAllowed({"user","manager"})
    public Response getProposalDetailsById(@PathParam("id") Long id){
        try {
            return Response.ok(proposalService.getProposalDetailsById(id),
                    MediaType.APPLICATION_JSON).build();
        } catch (ServerErrorException errorException){
            return Response.serverError().build();
        }
    }

    @POST
    @RolesAllowed("proposal-customer")
    public Response createNewProposal(ProposalDetailsDTO proposalDetails){
        int proposalRequestStatus = proposalService.createProposal(proposalDetails).getStatus();
        return Response.status(proposalRequestStatus).build();
    }

    @DELETE
    @Path("/remove/{id}")
    @RolesAllowed("manager")
    public Response removeProposal(@PathParam("id") Long id){
        int proposalRequestStatus = proposalService.removeProposal(id).getStatus();
        return Response.status(proposalRequestStatus).build();
    }
}
