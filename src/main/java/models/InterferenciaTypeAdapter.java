package models;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class InterferenciaTypeAdapter extends TypeAdapter<Interferencia> {

	private final Gson gson = new Gson();

	@Override
	public void write(JsonWriter out, Interferencia value) throws IOException {
		// Custom serialization if needed
		gson.toJson(value, Interferencia.class, out);
	}

	@Override
	public Interferencia read(JsonReader in) throws IOException {
		JsonObject jsonObject = JsonParser.parseReader(in).getAsJsonObject();

		// Check the 'tipoInterferencia' field to decide which class to instantiate
		JsonObject tipoInterferencia = jsonObject.getAsJsonObject("tipoInterferencia");
		String descricao = tipoInterferencia.get("descricao").getAsString();

		if ("Subterrânea".equals(descricao)) {
			// Deserialize as Subterranea
			return gson.fromJson(jsonObject, Subterranea.class);
		} else {
			// Deserialize as Interferencia
			return gson.fromJson(jsonObject, Interferencia.class);
		}
	}
}