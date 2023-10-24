package org.br.mining.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.br.mining.dto.OpportunityDTO;
import org.br.mining.dto.ProposalDTO;
import org.br.mining.dto.QuotationDTO;
import org.br.mining.entity.OpportunityEntity;
import org.br.mining.entity.QuotationEntity;
import org.br.mining.repository.OpportunityRepository;
import org.br.mining.repository.QuotationRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class OpportunityServiceImpl implements OpportunityService{

    private final QuotationRepository quotationRepository;
    private final OpportunityRepository opportunityRepository;

    @Inject
    public OpportunityServiceImpl(QuotationRepository quotationRepository, OpportunityRepository opportunityRepository) {
        this.quotationRepository = quotationRepository;
        this.opportunityRepository = opportunityRepository;
    }

    @Override
    @Transactional
    public void buildOpportunity(ProposalDTO proposal) {

        List<QuotationEntity> quotationEntities = quotationRepository.findAll().list();
        Collections.reverse(quotationEntities);

        OpportunityEntity opportunity = new OpportunityEntity(
                LocalDateTime.now(),
                proposal.getProposalId(),
                proposal.getCustomer(),
                proposal.getPriceTonne(),
                quotationEntities.get(0).getCurrencyPrice()
        );

        opportunityRepository.persist(opportunity);
    }

    @Override
    @Transactional
    public void saveQuotation(QuotationDTO quotation) {

        QuotationEntity entity = new QuotationEntity();
        entity.setDate(LocalDateTime.now());
        entity.setCurrencyPrice(quotation.getCurrencyPrice());

        quotationRepository.persist(entity);
    }

    @Override
    public List<OpportunityDTO> generateOpportunityData() {
        List<OpportunityDTO> opportunities = new ArrayList<>();

        opportunityRepository
                .findAll()
                .stream()
                .forEach(item ->
                        opportunities.add(OpportunityDTO.builder()
                                        .proposalId(item.getProposalId())
                                        .customer(item.getCustomer())
                                        .priceTonne(item.getPriceTonne())
                                        .lastDollarQuotation(item.getLastDollarQuotation())
                                .build()));

        return opportunities;
    }
}
