package dreamfactory.cardgame.api.actions;

import com.google.gson.*;

import java.lang.reflect.Type;

public class ActionTypeAdapter implements JsonDeserializer<Action>, JsonSerializer<Action> {
    private final static String CLASS_FIELD = "class";

    @Override
    public JsonElement serialize(final Action action, final Type typeOfSrc,
                                 final JsonSerializationContext context) {
        JsonElement jsonElement = context.serialize(action);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        jsonObject.addProperty(CLASS_FIELD, action.getClass().getCanonicalName());
        return jsonObject;
    }

    @Override
    public Action deserialize(JsonElement json, Type typeOfT,
                              JsonDeserializationContext context) throws JsonParseException {
        try {
            JsonObject obj = json.getAsJsonObject();
            String className = obj.get(CLASS_FIELD).getAsString();
            Class clazz = Class.forName(className);
            return context.deserialize(json, clazz);
        } catch (Exception e) {
            throw new JsonParseException(e.getMessage());
        }
    }

}
