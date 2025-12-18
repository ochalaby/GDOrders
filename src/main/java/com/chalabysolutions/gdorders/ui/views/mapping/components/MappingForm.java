package com.chalabysolutions.gdorders.ui.views.mapping.components;

import com.chalabysolutions.gdorders.model.accounts.Account;
import com.chalabysolutions.gdorders.model.accounts.AccountAddress;
import com.chalabysolutions.gdorders.ui.views.mapping.logic.MappingPresenter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class MappingForm extends HorizontalLayout {


    public MappingForm(MappingPresenter presenter, MappingFormState formState) {

        ComboBox<Account> accountBox = new ComboBox<>("Account");
        accountBox.setItems(presenter.getAccounts());
        accountBox.setItemLabelGenerator(Account::getName);
        accountBox.setWidth("250px");

        ComboBox<AccountAddress> deliveryAddressBox = new ComboBox<>("Aflever adres");
        deliveryAddressBox.setItemLabelGenerator(a -> a.getAddressLine1()
                + ", " + a.getPostalCode() + " " + a.getCity()
                + ", " + a.getCountry().getCode()
                + " (" + a.getId() + ")");
        deliveryAddressBox.setWidth("450px");

        TextField customerAddressId = new TextField("Sales order adres ID");
        customerAddressId.setWidth("350px");

        // waarden uit state herstellen
        if (formState.getSelectedAccount() != null) {
            accountBox.setValue(formState.getSelectedAccount());
            deliveryAddressBox.setItems(formState.getSelectedAccount().getAddresses());
        }

        if (formState.getSelectedAddress() != null) {
            deliveryAddressBox.setValue(formState.getSelectedAddress());
        }

        if (formState.getCustomerAddressId() != null) {
            customerAddressId.setValue(formState.getCustomerAddressId());
        }

        // Wanneer user een account kiest → interne adressen vullen
        accountBox.addValueChangeListener(e -> {
            Account selectedAccount = e.getValue();
            formState.setSelectedAccount(selectedAccount);
            if (selectedAccount != null) {
                deliveryAddressBox.setItems(presenter.getAvailableAddresses(selectedAccount));
                deliveryAddressBox.clear();
            }
        });

        deliveryAddressBox.addValueChangeListener(e ->
                formState.setSelectedAddress(e.getValue())
        );

        customerAddressId.addValueChangeListener(e ->
                formState.setCustomerAddressId(e.getValue())
        );

        Button saveBtn = new Button("Mapping opslaan", click -> {
            presenter.onSaveMapping(formState.getSelectedAccount(), formState.getSelectedAddress(), formState.getCustomerAddressId());

            // clear velden
            accountBox.clear();
            deliveryAddressBox.clear();
            customerAddressId.clear();

            // clear state
            formState.clear();
        });

        add(accountBox, deliveryAddressBox, customerAddressId, saveBtn);
        setAlignItems(Alignment.END);
    }

}
