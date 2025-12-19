package com.chalabysolutions.gdorders.service;

import com.chalabysolutions.gdorders.model.mapping.AddressMapping;
import com.chalabysolutions.gdorders.model.mapping.MappingConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class MappingDataService {

    private final SettingsService settingsService;
    private final ObjectMapper mapper = new ObjectMapper();
    private Path configPath;

    private MappingConfig config = new MappingConfig();

    private String errorMessage;

    public MappingDataService(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @PostConstruct
    public void init() {
        loadFromSettings();
    }

    public boolean loadFromSettings(){
        try {
            String fileUri = settingsService.getMappingFile();
            configPath = Paths.get(fileUri);

            if (!Files.exists(configPath)) {
                errorMessage = "Mapping bestand bestaat niet: " + fileUri + "Controleer de instellingen.";
                return false;
            }

            // Bestaat de directory?
            Path parentDir = configPath.getParent();
            if (parentDir == null || !Files.isDirectory(parentDir)) {
                errorMessage = "De directory van het mappingbestand bestaat niet: " + parentDir;
                return false;
            }

            // Bestand bestaat → laden
            try (InputStream in = Files.newInputStream(configPath)) {
                config = mapper.readValue(in, MappingConfig.class);
                errorMessage = null;
                return true;
            }

        } catch (Exception e) {
            errorMessage = "Kon mapping configuratie niet laden: " + e.getMessage();
            return false;
        }
    }

    public void save() {
        try {
            if (configPath == null || !Files.exists(configPath)) {
                errorMessage = "Kan mapping niet opslaan: bestand bestaat niet: " + configPath;
            }

            mapper.writerWithDefaultPrettyPrinter().writeValue(configPath.toFile(), config);
            errorMessage = null;
        } catch (Exception e) {
            errorMessage = "Kon mapping configuratie niet opslaan: " + e.getMessage();
        }
    }

    public Optional<String> getErrorMessage() {
        return Optional.ofNullable(errorMessage);
    }

    public List<AddressMapping> getAddressMapping() {
        return config.getAddressMapping();
    }

    public void addAddressMapping(AddressMapping m) {
        config.getAddressMapping().add(m);
        save();
    }

    public void deleteAddressMapping(AddressMapping m) {
        config.getAddressMapping().remove(m);
        save();
    }

    public String findInternalAddressId(String customerAddressId) {
        return config.getAddressMapping().stream()
                .filter(m -> m.getAccountAddressId().equals(customerAddressId))
                .map(AddressMapping::getInternalAddressId)
                .findFirst()
                .orElse(null);
    }

    public AddressMapping findMapping(String customerAddressId) {
        return config.getAddressMapping().stream()
                .filter(m -> m.getAccountAddressId().equals(customerAddressId))
                .findFirst()
                .orElse(null);
    }

}