package utilities;

import java.io.UnsupportedEncodingException;

public class ConvertToUTF8 {

	String corruptedString;

	public ConvertToUTF8(String corruptedString) {
		super();
		this.corruptedString = corruptedString;
	}

	public String convertToUTF8() {
		try {
			// Convert the corrupted string to bytes using the ISO-8859-1 encoding
			byte[] isoBytes = this.corruptedString.getBytes("ISO-8859-1");

			// Create a new string from the ISO-8859-1 bytes using UTF-8 encoding
			return new String(isoBytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null; // Handle the exception or return an appropriate default value
		}
	}

}
