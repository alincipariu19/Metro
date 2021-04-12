package webshop.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.testng.annotations.DataProvider;
import webshop.base.BaseTest;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;

public class DataProviders extends BaseTest {

    public static String env;

    public static JsonObject readJSON(String dataFile) throws FileNotFoundException {
        FileReader reader = new FileReader(dataFile);
        JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();

        return jsonObject;
    }

    @DataProvider(name = "countryDataProvider")
    public Object[] countryDataProvider() throws FileNotFoundException {
        return new Object[][]{
                {"de"},
                {"at"}
        };
    }

    public static String getRandomProductCode() {
        Random rand = new Random();

        JsonObject products = null;
        try {
            products = readJSON("src/test/java/webshop/data/jsons/products.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JsonObject productsList = (JsonObject) products.get("products");

        int random = rand.nextInt(productsList.size()) + 1;
        return String.valueOf(productsList.get("product_" + String.valueOf(random))).replace("\"", "");
    }
}