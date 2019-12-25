package dev.fr13.utils;

import com.google.gson.Gson;

public class JsonParser {

    public static <T> T read(String json, Class<T> clazz) {
        final Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

    private JsonParser() {
        throw new UnsupportedOperationException();
    }
}
