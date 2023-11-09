package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class HTMLFileLoader {
	
	
    public static String loadHTMLResourceToString(String resourcePath) throws IOException {
        InputStream inputStream = HTMLFileLoader.class.getResourceAsStream(resourcePath);
        if (inputStream != null) {
            try (Scanner scanner = new Scanner(inputStream, "UTF-8")) {
                scanner.useDelimiter("\\A");
                return scanner.hasNext() ? scanner.next() : "";
            }
        } else {
            throw new IOException("HTML resource not found: " + resourcePath);
        }
    }
}
