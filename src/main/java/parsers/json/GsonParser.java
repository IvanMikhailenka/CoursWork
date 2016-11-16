package parsers.json;


import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created by 1 on 14.11.2016.
 */
/****
 *
 * Реализовать не удалось, прешел на Jackson
 *
 * *****/
public class GsonParser {

    public static void readJson() throws IOException {
        String str = "{\"message\":\"Hi\",\"place\":{\"name\":\"World!\"}}";
        String jsonSting = "{\n" +
                "  \"firstName\": \"Иван\",\n" +
                "  \"lastName\": \"Иванов\",\n" +
                "  \"address\": {\n" +
                "    \"streetAddress\": \"Московское ш., 101, кв.101\",\n" +
                "    \"city\": [ \"Ленинград\" , \"Minsk\"],\n" +
                "    \"postalCode\": 101101\n" +
                "  },\n" +
                "  \"phoneNumbers\": [\n" +
                "    \"812 123-1234\",\n" +
                "    \"916 123-4567\"\n" +
                "  ]\n" +
                "}";
        InputStream in = new ByteArrayInputStream(jsonSting.getBytes(Charset.forName("UTF-8")));
        JsonReader reader = new JsonReader(new InputStreamReader(in));
        while (reader.hasNext()) { // обходим все токены
            JsonToken jsonToken = reader.peek(); // получаем тип следующего токена
            if(jsonToken == JsonToken.BEGIN_ARRAY){
                reader.beginArray();
                System.out.println(reader.nextString());
            } if(jsonToken == JsonToken.END_ARRAY){
                reader.endArray();
            } if(jsonToken == JsonToken.BEGIN_OBJECT) { // если начало объекта
                reader.beginObject();
                System.out.print(reader.nextName() + ": ");
            } if(jsonToken == JsonToken.END_OBJECT) { // если конец объекта
                reader.endObject();
            } if(jsonToken == JsonToken.STRING) { // в случае если токен строковое знание - выводим на экран
                System.out.println(reader.nextString());
            } if(jsonToken == JsonToken.NAME) {
                System.out.print(reader.nextName() + ": ");
            } if(jsonToken == JsonToken.NUMBER){
                System.out.println(reader.nextDouble());
            }
        }
        reader.close();
    }
    public static void main(String[] args) throws IOException {
        System.out.println("readJson: ");
        readJson();
        System.out.println();
    }
}
