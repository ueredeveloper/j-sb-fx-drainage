package services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import models.Documento;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class DocumentService {
	private String localUrl;

	public DocumentService(String localUrl) {
		this.localUrl = localUrl;
	}

	public List<Documento> fetchByParam(String keyword) {
		try {
			URL apiUrl = new URL(localUrl + "/documento/pesquisa?keyword=" + URLEncoder.encode(keyword, "UTF-8"));
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
			showAlert("Erro na busca de algum documento", AlertType.ERROR);
		}
		return null;
	}

	public void saveDocument(Documento documento) {
		try {
			URL apiUrl = new URL(localUrl + "/documento");
			HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);

			// Convert Documento object to JSON
			String jsonInputString = convertObjectToJson(documento);

			// Write JSON to request body
			try (OutputStream os = connection.getOutputStream();
					OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8")) {
				osw.write(jsonInputString);
				osw.flush();
			}

			int responseCode = connection.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_CREATED) {
				showAlert("Document saved successfully", AlertType.INFORMATION);
			} else {
				handleErrorResponse(connection);
			}

			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			showAlert("Error saving document", AlertType.ERROR);
		}
	}

	public void deleteById (int id) {
		try {
			URL apiUrl = new URL(localUrl + "/documento/?id=" + id);
			HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
			connection.setRequestMethod("DELETE");

			int responseCode = connection.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				showAlert("Sucesso ao deletar um documento", AlertType.INFORMATION);
			} else {
				handleErrorResponse(connection);
			}

			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			showAlert("Erro ao deletar um documento!", AlertType.ERROR);
		}

	}

	private String convertObjectToJson(Object object) {
		Gson gson = new Gson();
		return gson.toJson(object);
	}

	private void showAlert(String message, AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setTitle("Alert");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
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

		return new Gson().fromJson(response.toString(), new TypeToken<List<Documento>>() {
		}.getType());
	}

	private void handleErrorResponse(HttpURLConnection connection) throws IOException {
		String responseMessage = connection.getResponseMessage();
		System.out.println("Error: " + responseMessage);
	}


}
