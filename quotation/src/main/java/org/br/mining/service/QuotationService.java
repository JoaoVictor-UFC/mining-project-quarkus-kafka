package org.br.mining.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.br.mining.client.CurrencyPriceClient;
import org.br.mining.dto.CurrencyPriceDTO;
import org.br.mining.dto.QuotationDTO;
import org.br.mining.entity.QuotationEntity;
import org.br.mining.message.KafkaEvents;
import org.br.mining.repository.QuotationRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class QuotationService {

    private final Logger log = LoggerFactory.getLogger(QuotationService.class);

    public static final String USD_BRL = "USD-BRL";

    @Inject
    @RestClient
    CurrencyPriceClient currencyPriceClient;

    @Inject
    QuotationRepository repository;

    @Inject
    KafkaEvents events;

    public void getCurrencyPrice() {


        CurrencyPriceDTO currencyPriceInfo = currencyPriceClient.getPriceByPair(USD_BRL);

        if (updateCurrentInfoPrice(currencyPriceInfo)){
            events.sendNewKafkaEvent(QuotationDTO
                    .builder()
                    .currencyPrice(new BigDecimal(currencyPriceInfo.getUSDBRL().getBid()))
                    .date(LocalDateTime.now())
                    .build());
        }
    }

    private Boolean updateCurrentInfoPrice (CurrencyPriceDTO currencyPriceInfo){

        try {
            BigDecimal currentPrice = new BigDecimal(currencyPriceInfo.getUSDBRL().getBid());
            Boolean updatePrice = Boolean.FALSE;

            List<QuotationEntity> quotationList = repository.findAll().list();

            if (quotationList.isEmpty()){

                saveQuotation(currencyPriceInfo);
                updatePrice = Boolean.TRUE;
            } else {

                QuotationEntity lastDollarPrice = quotationList.get(quotationList.size() -1);

                if (currentPrice.floatValue() > lastDollarPrice.getCurrencyPrice().floatValue()){
                    updatePrice = Boolean.TRUE;
                    saveQuotation(currencyPriceInfo);
                }
            }

            return updatePrice;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private void saveQuotation(CurrencyPriceDTO dto){

        QuotationEntity quotation = new QuotationEntity();

        quotation.setDate(LocalDateTime.now());
        quotation.setCurrencyPrice(new BigDecimal(dto.getUSDBRL().getBid()));
        quotation.setPctChange(dto.getUSDBRL().getPctChange());
        quotation.setPair(USD_BRL);

        repository.persist(quotation);
    }

}
