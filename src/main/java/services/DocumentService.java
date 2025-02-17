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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import models.Documento;

public class DocumentService {
	
	private String localUrl;

	public DocumentService(String localUrl) {
		this.localUrl = localUrl;
	}
	

	public ServiceResponse<?> save(Documento documento) {
		
		try {
			URL apiUrl = new URL(localUrl + "/document/create");
			HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);

			// Convert Documento object to JSON
			String jsonInputString = convertObjectToJson(documento);
			
			//System.out.println("save doc");
			//System.out.println(jsonInputString);
			
			// Write JSON to request body
			try (OutputStream os = connection.getOutputStream();
					OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8")) {
				osw.write(jsonInputString);
				osw.flush();
			}

			int responseCode = connection.getResponseCode();
			
			//System.out.println(responseCode);

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
				handleErrorResponse(connection);
				responseBody = readErrorStream(connection);
			}

			connection.disconnect();
			//System.out.println("json resp salva doc");
			//System.out.println(responseBody);
			return new ServiceResponse<>(responseCode, responseBody);
		} catch (Exception e) {
			e.printStackTrace();
			// showAlert("Error saving document", AlertType.ERROR);
			return null; // Return null if an error occurs
		}
	}

	public ServiceResponse<?> update(Documento documento) {
		try {
			URL apiUrl = new URL(localUrl + "/document/update?id=" + documento.getId());
			HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
			connection.setRequestMethod("PUT");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);

			// Convert Documento object to JSON
			String jsonInputString = convertObjectToJson(documento);
			
			System.out.println("update doc");
			
			System.out.println(jsonInputString);

			// Write JSON to request body
			try (OutputStream os = connection.getOutputStream();
					OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8")) {
				osw.write(jsonInputString);
				osw.flush();
			}

			int responseCode = connection.getResponseCode();
			
			//System.out.println("update " + jsonInputString);

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

	
	public Set<Documento> fetchByParam(String keyword) {
		
		try {
			URL apiUrl = new URL(localUrl + "/document/list?keyword=" + URLEncoder.encode(keyword, "UTF-8"));
			HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
			connection.setRequestMethod("GET");

			int responseCode = connection.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				// System.out.println("HTTP OK");
				return handleSuccessResponse(connection);
			} else if (responseCode == HttpURLConnection.HTTP_CREATED) {
				// System.out.println("HTTP Created");
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
	
	public Set<Documento> fetchDocumentByUserId (Long userId) {
		
		try {
			URL apiUrl = new URL(localUrl + "/document/list-by-user-id?id=" + userId);
			HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
			connection.setRequestMethod("GET");

			int responseCode = connection.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				// System.out.println("HTTP OK");
				return handleSuccessResponse(connection);
			} else if (responseCode == HttpURLConnection.HTTP_CREATED) {
				// System.out.println("HTTP Created");
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

	public ServiceResponse<?> deleteById(Long id) {
		try {
			URL apiUrl = new URL(localUrl + "/document/delete?id=" + id); // Updated URL
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
	
	public ServiceResponse<?> deleteDocUserRelation (Long docId, Long usId) {
		try {
			URL apiUrl = new URL(localUrl + "/document/delete-user-doc-relation?docId=" + docId + "&" + "usId=" + usId); // Updated URL
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

	private String convertObjectToJson(Object object) {
		Gson gson = new Gson();
		return gson.toJson(object);
	}

	private Set<Documento> handleSuccessResponse(HttpURLConnection connection) throws IOException {
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
		StringBuilder response = new StringBuilder();
		String line;

		while ((line = reader.readLine()) != null) {

			response.append(line);
		}

		reader.close();
		
		System.out.println(response.toString());

		return new Gson().fromJson(response.toString(), new TypeToken<Set<Documento>>() {
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

}
