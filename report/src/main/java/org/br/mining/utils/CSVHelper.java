package org.br.mining.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.br.mining.dto.OpportunityDTO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class CSVHelper {

    public static ByteArrayInputStream OpportunitiesToCSV(List<OpportunityDTO> opportunities) {
        //Todo: Fix that part to something not deprecated
        final CSVFormat format = CSVFormat.DEFAULT.withHeader("ID Proposta", "Cliente", "Preco por tonelada", "Melhor cotacao de Moeda");

        try (
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(outputStream), format)){

            for (OpportunityDTO dto : opportunities) {
                List<String> data = Arrays.asList(String.
                        valueOf(dto.getProposalId()), dto.getCustomer(), String.valueOf(dto.getPriceTonne()),
                        String.valueOf(dto.getLastDollarQuotation()));
                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();

            return new ByteArrayInputStream(outputStream.toByteArray());
        }catch (IOException e) {
            throw new RuntimeException("Fail to import data to CSV file: "
            + e.getMessage());
        }
    }
}
