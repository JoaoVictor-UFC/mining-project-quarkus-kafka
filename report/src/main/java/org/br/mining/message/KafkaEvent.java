package org.br.mining.message;

import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.br.mining.dto.ProposalDTO;
import org.br.mining.dto.QuotationDTO;
import org.br.mining.service.OpportunityService;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaEvent {

    private final Logger logger = LoggerFactory.getLogger(KafkaEvent.class);

    @Inject
    OpportunityService service;

    @Incoming("proposal")
    @Transactional
    public void receiveProposal(ProposalDTO proposal){
        logger.info("--- Receiving new proposal from kafka `proposal` topic ---");
        service.buildOpportunity(proposal);
    }

    @Incoming("quotation")
    @Blocking
    public void receiveQuotation(QuotationDTO quotation){
        logger.info("--- Receiving new quotation from kafka `quotation` topic ---");
        service.saveQuotation(quotation);
    }
}
