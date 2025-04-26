package utilities;

import java.io.InputStream;
import java.util.Properties;

public class VersionUtil {

    public static String getVersion() {
        try (InputStream input = VersionUtil.class.getResourceAsStream(
                "/META-INF/maven/com.ueredeveloper/j-sb-fx-drainage/pom.properties")) {

            if (input == null) return "Dev Version";

            Properties props = new Properties();
            props.load(input);
            return props.getProperty("version", "Unknown");
        } catch (Exception e) {
            return "Unknown";
        }
    }
}
