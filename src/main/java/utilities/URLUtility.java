package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class URLUtility {
	private static String urlService;

	private static boolean isURLReachable(String urlString) {
		try {
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000); // Timeout in milliseconds

			int responseCode = connection.getResponseCode();
			return (200 <= responseCode && responseCode <= 399); // HTTP success range
		} catch (IOException e) {
			return false; // URL is not reachable
		}
	}

	static {
		try {
			Properties prop = new Properties();
			InputStream inputStream = URLUtility.class.getClassLoader().getResourceAsStream("config.properties");
			prop.load(inputStream);
			String localURL = prop.getProperty("local.url");
			String remoteURL = prop.getProperty("remote.url");

			if (isURLReachable(localURL)) {
				urlService = localURL;
			} else {
				urlService = remoteURL;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getURLService() {
		return urlService;
	}
}
