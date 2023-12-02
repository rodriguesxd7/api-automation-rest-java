package hooks;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
public class UtilsRest {
    public static String readPost() throws IOException {
        String file;
        return file = new String(Files.readAllBytes(Paths.get("src/main/resources/postToAddNewProduct.json")));
    }
    public static String getProps(String prop) throws IOException {
        FileInputStream in = new FileInputStream("src/main/resources/config.properties");
        Properties props = new Properties();
        props.load(in);
        return props.getProperty(prop);
    }
    public static String getUrl() {
        String url = null;
        {
            try {
                url = getProps("base");
            } catch (IOException e) {
                e.getLocalizedMessage();
            }
        }
        return url;
    }
}