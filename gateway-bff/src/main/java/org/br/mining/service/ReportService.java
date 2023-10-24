package org.br.mining.service;

import org.br.mining.dto.OpportunityDTO;
import java.io.ByteArrayInputStream;
import java.util.List;

public interface ReportService {

    ByteArrayInputStream generateCSVOpportunityReport();

    List<OpportunityDTO> getOpportunitiesData();
}
