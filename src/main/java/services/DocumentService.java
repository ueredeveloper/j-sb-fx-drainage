package services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.Documento;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DocumentService {
    private String localUrl;

    public DocumentService(String localUrl) {
        this.localUrl = localUrl;
    }

    public List<Documento> fetchDocuments() {
        try {
            URL apiUrl = new URL(localUrl + "/documento");
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("HTTP OK");
                return handleSuccessResponse(connection);
            } else if (responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("HTTP Created");
                return handleSuccessResponse(connection);
            } else {
                handleErrorResponse(connection);
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Documento> handleSuccessResponse(HttpURLConnection connection) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            response.append(line);
        }

        reader.close();

        return new Gson().fromJson(response.toString(), new TypeToken<List<Documento>>() {}.getType());
    }

    private void handleErrorResponse(HttpURLConnection connection) throws IOException {
        String responseMessage = connection.getResponseMessage();
        System.out.println("Error: " + responseMessage);
    }
}
