package org.br.mining.client;

import com.google.gson.Gson;
import jakarta.enterprise.context.ApplicationScoped;
import org.br.mining.dto.CurrencyPriceDTO;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
@ApplicationScoped
public class CurrencyPriceClient {

    @ConfigProperty(name = "awesomeapi.api.url")
    String apiUrl;
    public CurrencyPriceDTO getPriceByPair(String pair){

        try {

            URL url = new URL(apiUrl + pair);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            if (connection.getResponseCode() != 200)
                throw new RuntimeException("HTTP error code : " + connection.getResponseCode());

            BufferedReader response = new BufferedReader(new InputStreamReader((connection.getInputStream())));
            String jsonEmString = convertJsonString(response);
            Gson gson = new Gson();

            return gson.fromJson(jsonEmString, CurrencyPriceDTO.class);

        }catch (Exception e){
            throw new RuntimeException("ERROR: " + e);
        }
    }

    public static String convertJsonString(BufferedReader bufferReader) throws IOException {
        String resposta;
        StringBuilder jsonString = new StringBuilder();
        while ((resposta = bufferReader.readLine()) != null) {
            jsonString.append(resposta);
        }
        return jsonString.toString();
    }
}

