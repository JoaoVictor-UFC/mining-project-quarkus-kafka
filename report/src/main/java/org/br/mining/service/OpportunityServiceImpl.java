package org.br.mining.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.br.mining.dto.OpportunityDTO;
import org.br.mining.dto.ProposalDTO;
import org.br.mining.dto.QuotationDTO;

import java.util.List;

@ApplicationScoped
public class OpportunityServiceImpl implements OpportunityService{
    @Override
    public void buildOpportunity(ProposalDTO proposal) {

    }

    @Override
    public void saveQuotation(QuotationDTO quotation) {

    }

    @Override
    public List<OpportunityDTO> generateOpportunityData() {
        return null;
    }
}
