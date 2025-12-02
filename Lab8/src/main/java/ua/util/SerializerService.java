package ua.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SerializerService {

    private static final ObjectMapper jsonMapper = new ObjectMapper();
    private static final ObjectMapper yamlMapper = new YAMLMapper();

    public static <T> void saveToJson(String path, List<T> list) {
        try {
            jsonMapper.writerWithDefaultPrettyPrinter().writeValue(new File(path), list);
            Utils.getLogger().info("Saved JSON to " + path);
        } catch (IOException e) {
            Utils.getLogger().severe("JSON save error: " + e.getMessage());
            throw new DataSerializationException("Error saving JSON", e);
        }
    }

    public static <T> void saveToYaml(String path, List<T> list) {
        try {
            yamlMapper.writerWithDefaultPrettyPrinter().writeValue(new File(path), list);
            Utils.getLogger().info("Saved YAML to " + path);
        } catch (IOException e) {
            throw new DataSerializationException("Error saving YAML", e);
        }
    }

    public static <T> List<T> loadJson(String path, Class<T> clazz) {
        try {
            return jsonMapper.readValue(
                    new File(path),
                    jsonMapper.getTypeFactory().constructCollectionType(List.class, clazz)
            );
        } catch (IOException e) {
            throw new DataSerializationException("Error loading JSON", e);
        }
    }

    public static <T> List<T> loadYaml(String path, Class<T> clazz) {
        try {
            return yamlMapper.readValue(
                    new File(path),
                    yamlMapper.getTypeFactory().constructCollectionType(List.class, clazz)
            );
        } catch (IOException e) {
            throw new DataSerializationException("Error loading YAML", e);
        }
    }
}
