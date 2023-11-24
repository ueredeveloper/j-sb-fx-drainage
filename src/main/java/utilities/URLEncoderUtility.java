package utilities;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class URLEncoderUtility {

    public static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // Handle encoding exception
            e.printStackTrace();
            return null; // Or throw an exception or handle the error as needed
        }
    }
}