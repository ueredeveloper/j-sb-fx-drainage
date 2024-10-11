package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import models.Documento;
import models.Template;
import utilities.JsonConverter;

public class TemplateService {
	

	private String localUrl;

	public TemplateService(String localUrl) {
		this.localUrl = localUrl;
	}

	public ServiceResponse<?> save(Template object) {
	    try {
	        URL apiUrl = new URL(localUrl + "/template/create");
	        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Content-Type", "application/json");
	        connection.setDoOutput(true);
	       
	        // Convert Template object to JSON
	        String jsonInputString = JsonConverter.convertObjectToJson(object);
	       // System.out.println("Request JSON: " + jsonInputString);

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
	            System.out.println("HttpURLConnection != HTTP_CREATED, Error occurred");
	            handleErrorResponse(connection);
	            responseBody = readErrorStream(connection);
	        }

	        System.out.println("Response body: " + responseBody);
	        connection.disconnect();

	        // Check if response body is a JSON object
	        if (responseBody.trim().startsWith("{")) {
	            Template responseObject = new Gson().fromJson(responseBody, Template.class);
	            //System.out.println("Parsed response object: " + responseObject);
	            return new ServiceResponse<>(responseCode, responseObject);
	        } else {
	          //  System.out.println("Unexpected response format: " + responseBody);
	            return new ServiceResponse<>(responseCode, responseBody);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("Exception occurred");
	        return null; // Return null if an error occurs
	    }
	}

	public ServiceResponse<?> update(Template object) {
		try {
			URL apiUrl = new URL(localUrl + "/template/update?id=" + object.getId());
			HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
			connection.setRequestMethod("PUT");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);

			// Convert object to JSON
			String jsonInputString = JsonConverter.convertObjectToJson(object);

			System.out.println(jsonInputString);

			// Write JSON to request body
			try (OutputStream os = connection.getOutputStream();
					OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8")) {
				osw.write(jsonInputString);
				osw.flush();
			}

			int responseCode = connection.getResponseCode();

			System.out.println("edição res code " + responseCode);

			String responseBody;
			if (responseCode == HttpURLConnection.HTTP_OK) {

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

	public Set<Template> listTemplatesByParams(String typeOfDocument, String typeOfGrand, String subtypeOfGrant) throws UnsupportedEncodingException {
		
		System.out.println("search  template if not has");
		String url = localUrl + "/template/list-templates-by-params?"
				+ "tipoDocumento="
				+ URLEncoder.encode(typeOfDocument, "UTF-8")
				+ "&tipoOutorga="
				+ URLEncoder.encode(typeOfGrand, "UTF-8")
				+ "&subtipoOutorga="
				+ URLEncoder.encode(subtypeOfGrant, "UTF-8");
		
		System.out.println(url);

		try {
			URL apiUrl = new URL(url);
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

	private Set<Template> handleSuccessResponse(HttpURLConnection connection) throws IOException {
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
		StringBuilder response = new StringBuilder();
		String line;

		while ((line = reader.readLine()) != null) {
			response.append(line);
		}

		reader.close();
		
		System.out.println("templates " + response.toString());

		return new Gson().fromJson(response.toString(), new TypeToken<Set<Template>>() {
		}.getType());
	}

	private void handleErrorResponse(HttpURLConnection connection) throws IOException {
		String responseMessage = connection.getResponseMessage();
		System.out.println("Error: " + responseMessage);
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
