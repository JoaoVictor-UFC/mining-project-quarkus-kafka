package org.br.mining.scheduler;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.br.mining.message.KafkaEvents;
import org.br.mining.service.QuotationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class QuotationScheduler {

    private final Logger log = LoggerFactory.getLogger(QuotationScheduler.class);

    @Inject
    QuotationService service;

    @Transactional
    @Scheduled(every = "35s", identity = "task-job")
    void schedule(){
        log.info("--- Running Scheduler ---");
        service.getCurrencyPrice();
    }
}
