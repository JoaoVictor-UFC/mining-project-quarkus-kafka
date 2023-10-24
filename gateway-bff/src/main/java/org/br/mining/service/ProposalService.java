package org.br.mining.service;

import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.br.mining.dto.ProposalDetailsDTO;

public interface ProposalService {

    ProposalDetailsDTO getProposalDetailsById(@PathParam("id") Long proposalId);

    Response createProposal(ProposalDetailsDTO proposalDetails);

    Response removeProposal(Long id);

}
