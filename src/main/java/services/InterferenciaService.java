package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import models.Interferencia;
import models.InterferenciaTypeAdapter;
import models.Subterranea;

public class InterferenciaService {

	private String localUrl;

	public InterferenciaService(String localUrl) {
		this.localUrl = localUrl;
	}

	public ServiceResponse<?> save(Subterranea obj) {
		try {
			URL apiUrl = new URL(localUrl + "/interference/subterranean/create");
			HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);

			// Convert Documento object to JSON
			String jsonInputString = convertObjectToJson(obj);
			
			//System.out.println("salvar int " + jsonInputString);

			// Write JSON to request body
			try (OutputStream os = connection.getOutputStream();
					OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8")) {
				osw.write(jsonInputString);
				osw.flush();
			}

			int responseCode = connection.getResponseCode();

			String responseBody;
			if (responseCode == HttpURLConnection.HTTP_CREATED) {

				try (BufferedReader br = new BufferedReader(
						new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
					StringBuilder response = new StringBuilder();
					String responseLine;
					while ((responseLine = br.readLine()) != null) {
						response.append(responseLine);
					}
					responseBody = response.toString();
				}
			} else {
				System.out.println("ERROR");
				handleErrorResponse(connection);
				responseBody = readErrorStream(connection);
			}

			connection.disconnect();
			return new ServiceResponse<>(responseCode, responseBody);
		} catch (Exception e) {
			e.printStackTrace();

			// showAlert("Error saving document", AlertType.ERROR);
			return null; // Return null if an error occurs
		}
	}

	public ServiceResponse<?> update(Interferencia object) {
		try {
			URL apiUrl = new URL(localUrl + "/interference/subterranean/update?id=" + object.getId());
			HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
			connection.setRequestMethod("PUT");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);

			// Convert Documento object to JSON
			String jsonInputString = convertObjectToJson(object);
			
			System.out.println("editar int " + jsonInputString);

			// Write JSON to request body
			try (OutputStream os = connection.getOutputStream();
					OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8")) {
				osw.write(jsonInputString);
				osw.flush();
			}

			int responseCode = connection.getResponseCode();

			String responseBody;
			if (responseCode == HttpURLConnection.HTTP_OK) {

				try (BufferedReader br = new BufferedReader(
						new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
					StringBuilder response = new StringBuilder();
					String responseLine;
					while ((responseLine = br.readLine()) != null) {
						response.append(responseLine);
					}

					// System.out.println("edited res to string " + response.toString());
					responseBody = response.toString();
				}
			} else {
				handleErrorResponse(connection);
				responseBody = readErrorStream(connection);
			}

			connection.disconnect();
			return new ServiceResponse<String>(responseCode, responseBody);
		} catch (Exception e) {
			e.printStackTrace();
			return null; // Return null if an error occurs
		}
	}

	public Set<Interferencia> fetchByKeyword(String keyword) {

		try {
			URL apiUrl = new URL(
					localUrl + "/interference/list-by-keyword?keyword=" + URLEncoder.encode(keyword, "UTF-8"));
			HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
			connection.setRequestMethod("GET");

			int responseCode = connection.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {

				return handleSuccessResponse(connection);
			} else if (responseCode == HttpURLConnection.HTTP_CREATED) {

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

	public ServiceResponse<?> deleteById(Long id, Long tipoInterferenciaId) {

		try {
			URL apiUrl = new URL(localUrl + "/interference/delete?id=" + id); // Updated URL
			HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
			connection.setRequestMethod("DELETE");

			int responseCode = connection.getResponseCode();

			// Read the response body if needed
			InputStream inputStream = connection.getInputStream();
			String responseBody = new BufferedReader(new InputStreamReader(inputStream)).lines()
					.collect(Collectors.joining("\n"));

			connection.disconnect();

			return new ServiceResponse<>(responseCode, responseBody); // Change null to the actual response body if
																		// needed
		} catch (Exception e) {
			e.printStackTrace();
			// Handle the exception if needed
			return new ServiceResponse<>(-1, null); // You might want to use a different code for errors
		}
	}

	private Set<Interferencia> handleSuccessResponse(HttpURLConnection connection) throws IOException {
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
		StringBuilder response = new StringBuilder();
		String line;

		while ((line = reader.readLine()) != null) {
			response.append(line);
		}

		reader.close();

		String result = response.toString();
		Set<Interferencia> convertedList = new HashSet<>();

		if (result == null) {
			return convertedList; // Return an empty list if no results
		}

		String json = result.toString(); // Your JSON string

		// Parse the string as a JsonArray
		JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();
		
		System.out.println(jsonArray);

		Gson gson = new GsonBuilder().registerTypeAdapter(Interferencia.class, new InterferenciaTypeAdapter()).create();

		if (jsonArray != null) {
			Set<Interferencia> interferencias = gson.fromJson(jsonArray, new TypeToken<Set<Interferencia>>() {
			}.getType());

			// Post-process the objects if necessary
		    for (Interferencia interferencia : interferencias) {
		        if (interferencia instanceof Subterranea) {
		            Subterranea subterranea = (Subterranea) interferencia;
		           // System.out.println("Subterranea-specific attributes:");
		           // System.out.println("Caesb: " + subterranea.getCaesb());
		           // System.out.println("Nivel Estatico: " + subterranea.getNivelEstatico());
		           // System.out.println("Nivel Dinamico: " + subterranea.getNivelDinamico());
		           // System.out.println("Profundidade: " + subterranea.getProfundidade());
		           System.out.println("Vazão outorgável" + subterranea.getVazaoOutorgavel());
		            // Access other Subterranea-specific attributes if needed
		        } else {
		            System.out.println("Regular Interferencia");
		        }
		    }

			return interferencias;
		}

		return null;

		// System.out.println("response " + response.toString());
		// return new Gson().fromJson(response.toString(), new
		// TypeToken<Set<Interferencia>>() {
		// }.getType());
	}

	private void handleErrorResponse(HttpURLConnection connection) throws IOException {
		String responseMessage = connection.getResponseMessage();
		System.out.println("Error (method handleErrorRespose, Service Class: " + responseMessage);
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

	private String convertObjectToJson(Object object) {
		Gson gson = new Gson();
		return gson.toJson(object);
	}

}