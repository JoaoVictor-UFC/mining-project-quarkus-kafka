package org.br.mining.message;

import jakarta.enterprise.context.ApplicationScoped;
import org.br.mining.dto.ProposalDTO;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class KafkaEvent {

    private final Logger logger = LoggerFactory.getLogger(KafkaEvent.class);

    @Channel("proposal")
    Emitter<ProposalDTO> proposalRequestEmitter;

    public void sendNewKafkaEvent(ProposalDTO proposalDTO){
        logger.info("--- Sending new proposal for Kafka topic ---");
        proposalRequestEmitter.send(proposalDTO).toCompletableFuture().join();
    }

}
