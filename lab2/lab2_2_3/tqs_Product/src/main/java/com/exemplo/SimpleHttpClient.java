package com.exemplo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class SimpleHttpClient implements ISImpleHttpClient {

    @Override
    public String doHttpGet(String urlString) {
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("Erro na requisição HTTP: Código " + responseCode);
            }

            Scanner scanner = new Scanner(conn.getInputStream());
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

        } catch (IOException e) {
            throw new RuntimeException("Erro ao fazer requisição HTTP: " + e.getMessage(), e);
        }

        return response.toString();
    }
}
