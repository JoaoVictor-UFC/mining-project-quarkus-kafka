package org.br.mining.service;

import org.br.mining.dto.OpportunityDTO;
import org.br.mining.dto.ProposalDTO;
import org.br.mining.dto.QuotationDTO;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface OpportunityService {

    void buildOpportunity(ProposalDTO proposal);

    void saveQuotation(QuotationDTO quotation);

    List<OpportunityDTO> generateOpportunityData();

    ByteArrayInputStream generateCSVOpportunityReport();
}
