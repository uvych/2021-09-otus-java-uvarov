package otus.dataprocessor;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FileSerializer implements Serializer {
    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(mapToJson(data));
        } catch (IOException e) {
            throw new FileProcessException(e.getMessage());
        }
    }

    private String mapToJson(Map<String, Double> map) {
        Gson gson = new Gson();
        return gson.toJson(map);
    }
}
