package es.ucm.fdi.tieryourlikes.model.serializers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import es.ucm.fdi.tieryourlikes.AppConstants;
import es.ucm.fdi.tieryourlikes.model.Template;

public class TemplateSerializer implements JsonSerializer<Template> {
    @Override
    public JsonElement serialize(Template src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add(AppConstants.DB_ID_KEY, new JsonPrimitive(src.getId()));
        result.add(AppConstants.DB_TITLE_KEY, new JsonPrimitive(src.getTitle()));
        result.add(AppConstants.DB_CATEGORY_KEY, new JsonPrimitive(src.getCategory()));
        result.add(AppConstants.DB_CREATOR_USERNAME_KEY, new JsonPrimitive(src.getCreator_username()));
        result.add(AppConstants.DB_COVER_IMAGE_KEY, new JsonPrimitive(src.getCover()));
        result.add(AppConstants.DB_CREATION_TIME, new JsonPrimitive(src.getCreationTime()));

        JsonArray container = new JsonArray();
        JsonArray tier_rows = new JsonArray();

        for(String s : src.getContainer()){
            container.add(s);
        }
        for(String s : src.getTierRows()){
            tier_rows.add(s);
        }

        result.add(AppConstants.DB_CONTAINER_KEY, container);
        result.add(AppConstants.DB_TIER_ROWS_KEY, tier_rows);

        return result;
    }
}
