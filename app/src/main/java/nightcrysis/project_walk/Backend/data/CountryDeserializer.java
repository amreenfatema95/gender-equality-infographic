/**
 * Used to help with gson builder making all the country objects from the json get request
 */
package nightcrysis.project_walk.Backend.data;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class CountryDeserializer implements JsonDeserializer<Country>{

    @Override
    public Country deserialize(JsonElement jsonElement, Type type,
                               JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        JsonObject jO = jsonElement.getAsJsonObject();
        String name = jO.get("name").getAsString();

        return new Country(name);
    }

}
