package controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javafx.scene.control.Button;
import models.Documento;

public class MainFormController {

	private static final String url = "https://j-sb-drainage.ueredeveloper.repl.co";

	public Button button;

	public void handleOnClick() {
		System.out.println("Get list of documents ");

		try {
			// Create a URL object with the given URL
			URL apiUrl = new URL(url + "/documento");

			// Open a connection to the URL
			HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

			// Set the request method to GET
			connection.setRequestMethod("GET");

			// Get the response code
			int responseCode = connection.getResponseCode();

			// If the response code is 200 (HTTP_OK), read the response
			if (responseCode == HttpURLConnection.HTTP_OK) {
				
			} else if (responseCode == HttpURLConnection.HTTP_CREATED) {
				// Handle the 201 response code (resource created)
				System.out.println("Resource created successfully.");
				// You can also parse the JSON response if it's available in the response body
				// You can also parse the JSON response if it's available in the response body

				// Read and parse the JSON response in the body
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				System.out.println("reader");
				System.out.println(reader);
				StringBuilder response = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					System.out.println("line");
					System.out.println(line);
					response.append(line);
				}
				reader.close();

				Gson gson = new Gson();
				// JsonObject jsonObject =
				// JsonParser.parseString(response.toString()).getAsJsonObject();
				// System.out.println(jsonObject);
				List<Documento> documentos = gson.fromJson(response.toString(), new TypeToken<List<Documento>>() {
				}.getType());

				/*for (Documento documento : documentos) {
					System.out.println("doc_numero: " + documento.getDoc_numero());
					System.out.println("doc_processo: " + documento.getDoc_processo());
					System.out.println("doc_sei: " + documento.getDoc_sei());
					// System.out.println("doc_tipo: " + documento.getDoc_tipo().getDt_descricao());
				}*/

			} else {
				// System.out.println("Error: " + responseCode);
				String responseMessage = connection.getResponseMessage();
				System.out.println("Error: " + responseMessage);
			}

			// Close the connection
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
