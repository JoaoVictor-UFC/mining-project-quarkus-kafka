package org.br.mining.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.br.mining.client.ProposalRestClient;
import org.br.mining.dto.ProposalDetailsDTO;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class ProposalServiceImpl implements ProposalService{

    @Inject
    @RestClient
    ProposalRestClient proposalRestClient;

    @Override
    public ProposalDetailsDTO getProposalDetailsById(Long proposalId) {
        return proposalRestClient.getProposalDetailsById(proposalId);
    }

    @Override
    public Response createProposal(ProposalDetailsDTO proposalDetails) {
        return proposalRestClient.createProposal(proposalDetails);
    }

    @Override
    public Response removeProposal(Long id) {
        return proposalRestClient.removeProposal(id);
    }
}
