package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import models.UnidadeHidrografica;

public class UnidadeHidrograficaService {

	private String urlService;

	public UnidadeHidrograficaService(String urlService) {
		this.urlService = urlService;
	}

	public Set<UnidadeHidrografica> findByPoint(String lat, String lng) {

		try {
			URL apiUrl = new URL(urlService + "//hydrographic-unit/find-by-point?lat=" + lat + "&" + "lng=" + lng);
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
			// System.out.println("serv save e print ");

			// showAlert("Erro na busca de algum documento", AlertType.ERROR);
		}
		return null;
	}

	public Set<UnidadeHidrografica> listAll() {

		try {
			URL apiUrl = new URL(urlService + "/hydrographic-unit/list-all");
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
			// System.out.println("serv save e print ");

			// showAlert("Erro na busca de algum documento", AlertType.ERROR);
		}
		return null;
	}

	private Set<UnidadeHidrografica> handleSuccessResponse(HttpURLConnection connection) throws IOException {
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
		StringBuilder response = new StringBuilder();
		String line;

		while ((line = reader.readLine()) != null) {

			response.append(line);
		}

		reader.close();

		return new Gson().fromJson(response.toString(), new TypeToken<Set<UnidadeHidrografica>>() {
		}.getType());
	}

	private void handleErrorResponse(HttpURLConnection connection) throws IOException {
		String responseMessage = connection.getResponseMessage();
		System.out.println("Bacia Hidrográfica, Error: " + responseMessage);
	}

	private String readErrorStream(HttpURLConnection connection) throws IOException {
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
			StringBuilder response = new StringBuilder();
			String responseLine;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine);
			}
			return response.toString();
		}
	}

}
