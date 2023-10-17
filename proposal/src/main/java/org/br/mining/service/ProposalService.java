package org.br.mining.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.br.mining.dto.ProposalDetailsDTO;

//@ApplicationScoped
public interface ProposalService {

    ProposalDetailsDTO findFullProposal(Long id);

    void createNewProposal(ProposalDetailsDTO detailsDTO);

    void removeProposal(Long id);
}
