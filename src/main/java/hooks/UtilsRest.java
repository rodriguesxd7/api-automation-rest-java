package hooks;

import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
public class UtilsRest extends Hooks{
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

    public void validateResponse(Response response) {
        String[] resp = new String[]{"userId", "id", "title", "body"};

            JSONArray array = new JSONArray(response.asString());
            for (int i = 0; i < array.length(); i++) {
                JSONObject campos = array.getJSONObject(i);
                for (int j = 1; j < campos.length(); j++) {
                    if (campos.has(resp[j])) {
                        System.out.println("Campo " + resp[j] + " - retornado com sucesso - " + campos.get(resp[j]));
                        //test.get().info(MarkupHelper.createCodeBlock("Campo " + resp[j] + " - retornado com sucesso - " + campos.get(resp[j]), CodeLanguage.JSON));
                    }

                }
            }
        }

    }

