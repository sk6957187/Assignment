package com.nayak.oauth.config;

import com.nayak.oauth.entity.GoogleOauth;
import com.nayak.oauth.entity.MetaDataEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Component
public class ReadExternalConfig {

    @Value("${configuration.metadata}")
    private String metadataPath;

    public MetaDataEntity readMetadata() throws IOException {

        String content = Files.readString(Path.of(metadataPath));

        Yaml yaml = new Yaml();

        Map<String, Object> data = yaml.load(content);
        Map<String, Object> person = (Map<String, Object>) data.get("person");
        MetaDataEntity personEntity = new MetaDataEntity();
        personEntity.setName((String) person.get("name"));
        personEntity.setVillage((String) person.get("village"));

        return personEntity;
    }

    public GoogleOauth readGoogleOauth() throws IOException {
        String content = Files.readString(Path.of(metadataPath));
        Yaml yaml = new Yaml();
        Map<String, Object> data = yaml.load(content);
        Map<String, Object> google = (Map<String, Object>) data.get("googleOauth");

        GoogleOauth oauth = new GoogleOauth();

        oauth.setClientId((String) google.get("clientId"));
        oauth.setClientSecret((String) google.get("clientSecret"));
        oauth.setScope((List<String>) google.get("scope"));

        return oauth;
    }
}