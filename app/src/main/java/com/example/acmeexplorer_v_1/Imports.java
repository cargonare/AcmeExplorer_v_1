package com.example.acmeexplorer_v_1;

import com.example.acmeexplorer_v_1.models.Trip;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

public class Imports {
    public static Type ArrayTrips = new TypeToken<ArrayList<Trip>>(){}.getType();

    public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);

    public static Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new LocalDateConverter())
            .create();
    public static class LocalDateConverter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
        public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(DateTimeFormatter.ISO_LOCAL_DATE.format(src));
        }

        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return DateTimeFormatter.ISO_LOCAL_DATE.parse(json.getAsString(), LocalDate::from);
        }
    }

}