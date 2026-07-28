package in.nayak.foodiesapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;

import jakarta.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

@Configuration
public class ReadExternalFile {

    @Value("${file.upload-yml}")
    private String ymlFilePath;

    private Map<String, Object> data;

    @PostConstruct
    public void loadYaml() throws IOException {
        Yaml yaml = new Yaml();
        try (FileInputStream input = new FileInputStream(ymlFilePath)) {
            data = yaml.load(input);
        }
    }

    public String getImageFilePath() {
        return (String) ((Map<String, Object>) data.get("image")).get("path");
    }

    public String getEncryptedKey() {
        return (String) ((Map<String, Object>) data.get("encrypted")).get("key");
    }
}