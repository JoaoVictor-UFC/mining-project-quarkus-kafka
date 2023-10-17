package org.br.mining.message;

import jakarta.enterprise.context.ApplicationScoped;
import org.br.mining.dto.QuotationDTO;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class KafkaEvents {

    private final Logger log = LoggerFactory.getLogger(KafkaEvents.class);

    @Channel("quotation-channel")
    Emitter<QuotationDTO> quotationRequestEmitter;

    public void sendNewKafkaEvent(QuotationDTO dto){

        log.info("--- Submitting quote for Kafka topic ---");
        quotationRequestEmitter.send(dto).toCompletableFuture().join();
    }
}
