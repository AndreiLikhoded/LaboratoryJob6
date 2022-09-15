package readService;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.java.labjob.Candidate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ReadFileService {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Path path = Paths.get("data/candidates.json");

    public static List<Candidate> readFileString() {
        String json;
        try {
            json = Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return gson.fromJson(json, new TypeToken<List<Candidate>>() {
        }.getType());
    }

    public static void main(String[] args) {
        ReadFileService.readFileString();
    }
}
