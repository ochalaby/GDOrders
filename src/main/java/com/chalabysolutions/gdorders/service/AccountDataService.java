package com.chalabysolutions.gdorders.service;

import com.chalabysolutions.gdorders.io.XmlAccountReader;
import com.chalabysolutions.gdorders.model.accounts.Account;
import com.chalabysolutions.gdorders.model.accounts.AccountAddress;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Service
@VaadinSessionScope
public class AccountDataService {

    private final SettingsService settingsService;
    private final XmlAccountReader accountReader = new XmlAccountReader();

    @Setter
    private Account selectedAccount;
    private final List<Account> accounts = new ArrayList<>();
    private String errorMessage;

    public AccountDataService(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    public boolean loadFromSettings(){
        clear();
        String accountsFile = settingsService.getAccountsFile();
        if (accountsFile == null || accountsFile.isBlank()) {
            errorMessage = "Account XML file is niet geconfigureerd. Controller de settings.";
            return false;
        }

        File f = new File(accountsFile);
        if (!f.exists()) {
            errorMessage = "Kan geen accounts lezen uit de geconfigureerde Account XML file" + accountsFile + " Controleer de instellingen of de gecongigureerde file.";
            return false;
        }

        try (InputStream input = new FileInputStream(f)) {
            List<Account> loadedAccounts = accountReader.read(input);
            accounts.addAll(loadedAccounts);
            errorMessage = null;
            return true;
        } catch (Exception e) {
            errorMessage = "Fout bij inlezen accounts: " + e.getMessage();
            return false;
        }
    }

    public Optional<String> getErrorMessage() {
        return Optional.ofNullable(errorMessage);
    }

    public Optional<AccountAddress> findAddressById(String addressId) {
        if (addressId == null) {
            return Optional.empty();
        }

        return accounts.stream()
                .flatMap(a -> a.getAddresses().stream())
                .filter(address -> addressId.equals(address.getId()))
                .findFirst();
    }

    public void clear(){
        accounts.clear();
    }

    /** Lazy load helper: laad alleen als lijst leeg is */
    public boolean ensureAccountsLoaded() {
        if (accounts.isEmpty()) {
            return loadFromSettings();
        }
        return true;
    }
}

