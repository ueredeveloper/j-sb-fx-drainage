package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import models.Usuario;

public class UsuarioService {

	private String localUrl;

	public UsuarioService(String localUrl) {
		this.localUrl = localUrl;
	}

	public Set<Usuario> listByUserName(String keyword) {

		try {
			URL apiUrl = new URL(localUrl + "/user/list-by-name?keyword=" + URLEncoder.encode(keyword, "UTF-8"));
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

	public Set<Usuario> listUsersByDocumentId(Long id) {

		try {
			URL apiUrl = new URL(localUrl + "/user/list-by-document-id?id=" + id);
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

	private Set<Usuario> handleSuccessResponse(HttpURLConnection connection) throws IOException {
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
		StringBuilder response = new StringBuilder();
		String line;

		while ((line = reader.readLine()) != null) {

			response.append(line);
		}

		reader.close();

		return new Gson().fromJson(response.toString(), new TypeToken<Set<Usuario>>() {
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
