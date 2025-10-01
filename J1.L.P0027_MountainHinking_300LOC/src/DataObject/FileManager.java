package DataObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;


public class FileManager {
    private String filename;

    public FileManager() {
    }

    public FileManager(String filename) {
        this.filename = filename;
    }

    public List<String> readDataFromFile() throws IOException {
        List<String> result;
        result = Files.readAllLines(new File(filename).toPath(), Charset.forName("utf-8"));
        return result;
    }

    public void saveDataToFile(String data) throws IOException {
        Files.writeString(new File(filename).toPath(), data, Charset.forName("utf-8"));
    }

    
}