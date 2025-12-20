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
        return acc.getAddresses().stream()
                .filter(a -> mappingService.getAddressMapping().stream()
                        .noneMatch(m -> m.getInternalAddressId().equals(a.getId())))
                .toList();
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

    public void onSaveMapping(Account account, AccountAddress delivery, String customerId) {
        if (account == null || delivery == null || customerId == null || customerId.isBlank()) {
            status.show(StatusLevel.ERROR, "Alle velden zijn verplicht");
            return;
        }

        AddressMapping m = new AddressMapping();
        m.setAccountName(account.getName());
        m.setAccountCode(account.getCode());
        m.setDeliveryAddress(delivery.getAddressLine1()+ ", " + delivery.getPostalCode()
                + " " + delivery.getCity() + ", " + delivery.getCountry().getCode());
        m.setInternalAddressId(delivery.getId());
        m.setAccountAddressId(customerId);

        onAddressMappingAdded(m);
    }

    public void onAddressMappingAdded(AddressMapping m) {
        mappingService.addAddressMapping(m);
        mappingGrid.getListDataProvider().getItems().add(m);
        mappingGrid.getListDataProvider().refreshAll();
        status.show(StatusLevel.INFO, "Mapping opgeslagen!");
    }

    public void onAddressMappingDeleted(AddressMapping deleted) {
        List<AddressMapping> mappings = mappingService.getAddressMapping();
        AddressMapping existing = mappings.stream()
                .filter(m -> m.getInternalAddressId().equals(deleted.getInternalAddressId()))
                .findFirst()
                .orElse(null);

        if (existing != null) {
            mappingService.deleteAddressMapping(existing);
            mappingGrid.getListDataProvider().getItems().remove(deleted);
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
                m.setAccountAddressId(edited.getAccountAddressId());
                break;
            }
        }

        mappingService.save();
        mappingGrid.getDataProvider().refreshItem(edited);
        status.show(StatusLevel.INFO, "Adres ID aangepast");
    }
}
