package dreamfactory.cardgame.cards;

import com.google.gson.*;

import java.lang.reflect.Type;

public class CardTypeAdapter implements JsonDeserializer<Card>, JsonSerializer<Card> {
    private final static String CLASS_FIELD = "class";

    @Override
    public JsonElement serialize(final Card card, final Type typeOfSrc,
                                 final JsonSerializationContext context) {
        JsonElement jsonElement = context.serialize(card);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        jsonObject.addProperty(CLASS_FIELD, card.getClass().getCanonicalName());
        return jsonObject;
    }

    @Override
    public Card deserialize(JsonElement json, Type typeOfT,
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
