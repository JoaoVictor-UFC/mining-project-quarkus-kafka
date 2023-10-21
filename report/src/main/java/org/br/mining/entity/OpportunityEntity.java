package org.br.mining.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "opportunity")
@Data
@NoArgsConstructor
public class OpportunityEntity {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime date;

    @Column(name = "proposal_id")
    private Long proposalId;

    private String customer;

    @Column(name = "price_tonne")
    private BigDecimal priceTonne;

    @Column(name = "last_currency_quotation")
    private BigDecimal lastDollarQuotation;

    public OpportunityEntity(LocalDateTime now, Long proposalId, String customer, BigDecimal priceTonne, BigDecimal currencyPrice) {
        this.date = now;
        this.proposalId = proposalId;
        this.customer = customer;
        this.priceTonne = priceTonne;
        this.lastDollarQuotation = currencyPrice;
    }
}
