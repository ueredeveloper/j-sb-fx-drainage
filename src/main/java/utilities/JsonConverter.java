package utilities;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonConverter {

    private static final Gson gson = new Gson();

    public static String convertObjectToJson(Object object) {
        return gson.toJson(object);
    }

    public static boolean isInterferenciaSubterranea(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

        if (jsonObject.has("tipoInterferencia")) {
            JsonObject tipoInterferencia = jsonObject.getAsJsonObject("tipoInterferencia");
            if (tipoInterferencia.has("descricao")) {
                return "Subterrânea".equalsIgnoreCase(tipoInterferencia.get("descricao").getAsString());
            }
        }
        return false;
    }

    public static String convertHtmlContentToRawJson(String htmlContent) {
        return "\"" + htmlContent + "\"";
    }
}

