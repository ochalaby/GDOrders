package com.chalabysolutions.gdorders.service;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import com.chalabysolutions.gdorders.ui.views.StatusLevel;
import com.chalabysolutions.gdorders.ui.views.StatusBar;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {

    private StatusBar status;

    private static final String CONFIG_DIR = "config";
    private static final String CONFIG_FILE = "gdorders.properties";

    private static final String KEY_EXPORT_DIR = "exportDir";
    private static final String KEY_ACCOUNTS_FILE = "accountsFile";
    private static final String KEY_MAPPING_FILE = "mappingFile";
    private static final String KEY_WAREHOUSE_CODE = "warehouseCode";

    private final Properties props = new Properties();
    private Path configPath;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(CONFIG_DIR));
            configPath = Paths.get(CONFIG_DIR, CONFIG_FILE);

            if (!Files.exists(configPath)) {
                createDefaultConfig();
            }

            try (InputStream in = Files.newInputStream(configPath)) {
                props.load(in);
            }

        } catch (Exception e) {
            throw new IllegalStateException("Kon configuratie niet laden", e);
        }
    }

    /** Ensure directory exists */
    public File ensureExportDir() {
        String dir = getExportDir();
        if (dir == null || dir.isBlank()) return null;
        File f = new File(dir);
        if (!f.exists()) f.mkdirs();
        return f;
    }

    private void createDefaultConfig() {
        props.setProperty(KEY_EXPORT_DIR, "");
        props.setProperty(KEY_ACCOUNTS_FILE, "");
        props.setProperty(KEY_MAPPING_FILE, "");
        props.setProperty(KEY_WAREHOUSE_CODE, "");

        save();
    }

    // --------- GETTERS ---------
    public String getExportDir() {
        return props.getProperty(KEY_EXPORT_DIR);
    }

    public String getAccountsFile() {
        return props.getProperty(KEY_ACCOUNTS_FILE);
    }

    public String getMappingFile() {
        return props.getProperty(KEY_MAPPING_FILE);
    }

    public String getWarehouseCode() {
        return props.getProperty(KEY_WAREHOUSE_CODE);
    }

    // --------- SETTERS ---------
    public void setExportDir(String path) {
        props.setProperty(KEY_EXPORT_DIR, path);
    }

    public void setAccountsFile(String path) {
        props.setProperty(KEY_ACCOUNTS_FILE, path);
    }

    public void setMappingFile(String path) {
        props.setProperty(KEY_MAPPING_FILE, path);
    }

    public void setWarehouseCode(String code) {
        props.setProperty(KEY_WAREHOUSE_CODE, code);
    }

    public void setStatusBar(StatusBar status) {
        this.status = status;
        init();
    }

    // --------- SAVE ---------
    public void save() {
        try (OutputStream out = Files.newOutputStream(configPath)) {
            props.store(out, "GD Orders Settings");
        } catch (Exception e) {
            status.show(StatusLevel.ERROR, "Kon instellingen niet opslaan: " + e.getMessage());
        }
    }
}




