package com.chalabysolutions.gdorders.service;

import com.chalabysolutions.gdorders.model.accounts.Account;
import com.chalabysolutions.gdorders.model.accounts.AccountAddress;
import com.chalabysolutions.gdorders.model.mapping.AddressMapping;
import com.chalabysolutions.gdorders.model.mapping.DisplayInfo;
import com.chalabysolutions.gdorders.model.mapping.MappingConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final MappingConfig config = new MappingConfig();

    private String errorMessage;

    public MappingDataService(SettingsService settingsService) {
        this.settingsService = settingsService;
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
                MappingConfig loaded = mapper.readValue(in, MappingConfig.class);
                config.getAddressMapping().clear();
                config.getAddressMapping().addAll(loaded.getAddressMapping());

                errorMessage = null;
                return true;
            }

        } catch (Exception e) {
            errorMessage = "Kon mapping configuratie niet laden: " + e.getMessage();
            return false;
        }
    }

    public void refreshDisplayInfo(AccountDataService accountService) {
        for (AddressMapping mapping : config.getAddressMapping()) {
            // Zoek het interne account/adres op basis van internalAddressId
            String internalAddressId = mapping.getInternalAddressId();

            accountService.findAddressRowById(internalAddressId).ifPresent(addressRow -> {
                // Update display info
                mapping.setDisplayInfo(createNewDisplayInfo(addressRow.getAccount(), addressRow.getAddress()));
            });
        }

        // Opslaan na update
        save();
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

    private AddressMapping createNewAddressMapping(Account account, AccountAddress delivery, String customerId) {
        AddressMapping mapping = new AddressMapping();
        DisplayInfo info = createNewDisplayInfo(account, delivery);
        mapping.setDisplayInfo(info);
        mapping.setInternalAddressId(delivery.getId());
        mapping.setExternalAddressId(customerId);
        return mapping;
    }

    private DisplayInfo createNewDisplayInfo(Account account, AccountAddress delivery) {
        DisplayInfo info = new DisplayInfo();
        info.setAccountName(account.getName());
        info.setAccountCode(account.getCode());
        info.setDeliveryAddress(delivery.getAddressLine1()+ ", " + delivery.getPostalCode()
                + " " + delivery.getCity() + ", " + delivery.getCountry().getCode());
        return info;
    }

    public void addAddressMapping(Account account, AccountAddress delivery, String customerId) {
        AddressMapping m = createNewAddressMapping(account, delivery, customerId);
        config.getAddressMapping().add(m);
        save();
    }

    public void deleteAddressMapping(AddressMapping m) {
        config.getAddressMapping().remove(m);
        save();
    }

    public String findInternalAddressId(String customerAddressId) {
        return config.getAddressMapping().stream()
                .filter(m -> m.getExternalAddressId().equals(customerAddressId))
                .map(AddressMapping::getInternalAddressId)
                .findFirst()
                .orElse(null);
    }

}