package otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import otus.model.Measurement;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.List;

public class ResourcesFileLoader implements Loader {
    private final URL filePath;

    public ResourcesFileLoader(String fileName) {
        filePath = getClass().getClassLoader().getResource(fileName);
    }

    @Override
    public List<Measurement> load() {
        FileReader fileReader;
        try {
            fileReader = new FileReader(filePath.getFile());
        } catch (FileNotFoundException e) {
            throw new FileProcessException(e.getMessage());
        }
        return jsonArrayToObj(fileReader);
    }

    private List<Measurement> jsonArrayToObj(FileReader jsonArray) {
        Gson gson = new Gson();
        return gson.fromJson(jsonArray, new TypeToken<List<Measurement>>(){}.getType());
    }
}
