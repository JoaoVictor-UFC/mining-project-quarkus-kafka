package org.br.mining.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.br.mining.dto.ProposalDTO;
import org.br.mining.dto.ProposalDetailsDTO;
import org.br.mining.entity.ProposalEntity;
import org.br.mining.message.KafkaEvent;
import org.br.mining.repository.ProposalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@ApplicationScoped
public class ProposalServiceImpl implements ProposalService{

    private final Logger logger = LoggerFactory.getLogger(KafkaEvent.class);

    @Inject
    ProposalRepository repository;

    @Inject
    KafkaEvent kafkaMessages;

    @Override
    public ProposalDetailsDTO findFullProposal(Long id) {
        ProposalEntity proposal = repository.findById(id);

        return ProposalDetailsDTO
                .builder()
                .proposalId(proposal.getId())
                .proposalValidityDays(proposal.getProposalValidityDays())
                .country(proposal.getCountry())
                .priceTonne(proposal.getPriceTonne())
                .customer(proposal.getCustomer())
                .tonnes(proposal.getTonnes())
                .build();
    }

    @Override
    @Transactional
    public void createNewProposal(ProposalDetailsDTO detailsDTO) {

        ProposalDTO proposalDTO = buildAndSaveNewProposal(detailsDTO);
        kafkaMessages.sendNewKafkaEvent(proposalDTO);

    }

    @Override
    @Transactional
    public void removeProposal(Long id) {
        repository.deleteById(id);
    }

    private ProposalDTO buildAndSaveNewProposal(ProposalDetailsDTO detailsDTO){

        try {

            ProposalEntity proposal = new ProposalEntity();

            proposal.setCreated(LocalDateTime.now());
            proposal.setProposalValidityDays(detailsDTO.getProposalValidityDays());
            proposal.setCountry(detailsDTO.getCountry());
            proposal.setCustomer(detailsDTO.getCustomer());
            proposal.setPriceTonne(detailsDTO.getPriceTonne());
            proposal.setTonnes(detailsDTO.getTonnes());

            repository.persist(proposal);
            return ProposalDTO
                    .builder()
                    .proposalId(repository.findByCustomer(proposal.getCustomer()).get().getId())
                    .priceTonne(proposal.getPriceTonne())
                    .customer(proposal.getCustomer())
                    .build();

        }catch (Exception e){
            logger.error(e.getMessage());
            throw new RuntimeException();
        }
    }
}
