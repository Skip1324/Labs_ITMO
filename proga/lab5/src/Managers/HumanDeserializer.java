package Managers;

import Exceptions.IncorrectDataException;
import com.google.gson.*;

import java.lang.reflect.Type;

import data.Human;


/**
 * Type adapter of human for json deserialization.
 */
public class HumanDeserializer  implements JsonDeserializer<Human> {

        @Override
        public Human deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            Human human = new Human();
            try {
                human.setName(jsonObject.get("name").getAsString());
            } catch (IncorrectDataException e) {
                return null;
            }
            return human;
        }

    }


