package utilities;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import models.Interferencia;
import models.Subterranea;

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
    
    public static String convertInterferenciaToJson(Interferencia interferencia) {
        if (interferencia instanceof Subterranea) {
            Subterranea subterranea = (Subterranea) interferencia;
            //System.out.println("Interferencia is of type Subterranea.");
            return gson.toJson(subterranea); // Converts with all Subterranea attributes
        } else {
           // System.out.println("Interferencia is not of type Subterranea.");
            return gson.toJson(interferencia); // Converts as a generic Interferencia
        }
    }

    public static String convertHtmlContentToRawJson(String htmlContent) {
        return "\"" + htmlContent + "\"";
    }
}

