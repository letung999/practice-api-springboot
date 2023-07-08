package com.example.practice_api.utils;

import com.google.common.io.Resources;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class MockData {

    // generic Method
    public static <T> List<T> getData(String fileName, Class<T[]> object) throws IOException {
        InputStream inputStream = Resources.getResource(fileName).openStream();
        String json = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        T[] result = new Gson().fromJson(json, object);
        return Arrays.asList(result);
    }
}
