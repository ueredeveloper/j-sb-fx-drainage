package utilities;

import com.google.gson.Gson;

public class JsonConverter {
	
	private static final Gson gson = new Gson();

	public static String convertObjectToJson(Object object) {
		return gson.toJson(object);
	}
	
	 public static String convertHtmlContentToRawJson(String htmlContent) {
	        // Wrap the HTML content in quotes to treat it as a raw JSON string
	        return "\"" + htmlContent + "\"";
	    }

}
