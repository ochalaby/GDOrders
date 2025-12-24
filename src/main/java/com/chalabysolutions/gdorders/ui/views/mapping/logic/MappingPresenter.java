package com.chalabysolutions.gdorders.ui.views.mapping.logic;

import com.chalabysolutions.gdorders.model.accounts.Account;
import com.chalabysolutions.gdorders.model.accounts.AccountAddress;
import com.chalabysolutions.gdorders.model.mapping.AddressMapping;
import com.chalabysolutions.gdorders.service.AccountDataService;
import com.chalabysolutions.gdorders.service.MappingDataService;
import com.chalabysolutions.gdorders.ui.views.StatusBar;
import com.chalabysolutions.gdorders.ui.views.StatusLevel;
import com.chalabysolutions.gdorders.ui.views.mapping.components.MappingForm;
import com.chalabysolutions.gdorders.ui.views.mapping.components.MappingGrid;

import java.util.List;
import java.util.Optional;

public class MappingPresenter {

    private final MappingDataService mappingService;
    private final AccountDataService accountService;

    private MappingForm mappingForm;
    private MappingGrid mappingGrid;
    private StatusBar status;

    public MappingPresenter(MappingDataService mappingService, AccountDataService accountService) {
        this.mappingService = mappingService;
        this.accountService = accountService;
    }

    public void setMappingForm(MappingForm mappingForm) {
        this.mappingForm = mappingForm;
    }

    public void setMappingGrid(MappingGrid mappingGrid) {
        this.mappingGrid = mappingGrid;
    }

    public void setStatusBar(StatusBar status) {
        this.status = status;
    }

    public List<Account> getAccounts() {
        return accountService.getAccounts();
    }

    public List<AddressMapping> getAddressMapping(){
        return mappingService.getAddressMapping();
    }

    public List<AccountAddress> getAvailableAddresses(Account acc) {
        return mappingService.getAvailableInternalAddresses(acc);
    }

    public void reloadMappings() {
        // MappingService opnieuw laden
        boolean loaded = mappingService.loadFromSettings();

        // Grid verversen
        if (mappingGrid != null) {
            if (loaded) {
                mappingGrid.getDataProvider().refreshAll();
            } else {
                mappingGrid.setItems(List.of()); // lege lijst
            }
        }

        if (mappingForm != null) {
            mappingForm.setEnabled(loaded); // disabled als niet geladen
        }

        // Status bijwerken
        updateStatus();
    }

    private void updateStatus() {
        if (status == null) return;

        status.clear(); // oude meldingen wissen
        mappingService.getErrorMessage()
                .ifPresentOrElse(
                        msg -> status.show(StatusLevel.ERROR, msg),
                        () -> status.show(StatusLevel.INFO, "Mappings geladen")
                );
    }

    public void onSaveMapping(String accountId, String addressId, String customerId) {
        if (accountId == null || addressId == null || customerId == null || customerId.isBlank()) {
            status.show(StatusLevel.ERROR, "Alle velden zijn verplicht");
            return;
        }

        Account account = findAccountById(accountId)
                .orElseThrow(() -> new IllegalStateException("Account niet gevonden: " + accountId));

        AccountAddress address = account.getAddresses().stream()
                .filter(a -> a.getId().equals(addressId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Adres niet gevonden: " + addressId));


        mappingService.addAddressMapping(account, address, customerId);
        mappingGrid.getListDataProvider().refreshAll();
        status.show(StatusLevel.INFO, "Mapping opgeslagen!");
    }

    public Optional<Account> findAccountById(String accountId) {
        accountService.ensureAccountsLoaded();
        return accountService.findAccountById(accountId);
    }


    public void onAddressMappingDeleted(AddressMapping deleted) {
        List<AddressMapping> mappings = mappingService.getAddressMapping();
        AddressMapping existing = mappings.stream()
                .filter(m -> m.getInternalAddressId().equals(deleted.getInternalAddressId()))
                .findFirst()
                .orElse(null);

        if (existing != null) {
            mappingService.deleteAddressMapping(existing);
            mappingGrid.getListDataProvider().refreshAll();
            status.show(StatusLevel.INFO, "Mapping verwijderd");
        } else {
            status.show(StatusLevel.ERROR, "Mapping niet gevonden in de data");
        }
    }

    public void onAddressMappingEdited(AddressMapping edited) {
        // Zoek het bestaande object in config.addressMapping op basis van unieke sleutel (bv. internalAddressId)
        List<AddressMapping> mappings = mappingService.getAddressMapping();
        for (AddressMapping m : mappings) {
            if (m.getInternalAddressId().equals(edited.getInternalAddressId())) {
                m.setExternalAddressId(edited.getExternalAddressId());
                break;
            }
        }

        mappingService.save();
        mappingGrid.getDataProvider().refreshItem(edited);
        status.show(StatusLevel.INFO, "Adres ID aangepast");
    }
}
