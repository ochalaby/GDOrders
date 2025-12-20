package com.chalabysolutions.gdorders.ui.views.mapping.components;

import com.chalabysolutions.gdorders.model.accounts.Account;
import com.chalabysolutions.gdorders.model.accounts.AccountAddress;
import com.chalabysolutions.gdorders.ui.views.mapping.logic.MappingPresenter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;

public class MappingForm extends HorizontalLayout {

    ComboBox<Account> accountBox = new ComboBox<>();

    public MappingForm(MappingPresenter presenter, MappingFormState formState) {

        accountBox.setLabel("Account");
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
        if (formState.getSelectedAccountId() != null) {
            presenter.findAccountById(formState.getSelectedAccountId())
                    .ifPresent(acc -> {
                        accountBox.setValue(acc);
                        deliveryAddressBox.setItems(presenter.getAvailableAddresses(acc));
                    });
        }

        if (formState.getSelectedAddressId() != null && accountBox.getValue() != null) {
            Account acc = accountBox.getValue();
            acc.getAddresses().stream()
                    .filter(a -> a.getId().equals(formState.getSelectedAddressId()))
                    .findFirst()
                    .ifPresent(deliveryAddressBox::setValue);
        }

        if (formState.getCustomerAddressId() != null) {
            customerAddressId.setValue(formState.getCustomerAddressId());
        }

        accountBox.addValueChangeListener(e -> {
            Account selected = e.getValue();
            formState.setSelectedAccountId(
                    selected != null ? selected.getId() : null
            );

            if (selected != null) {
                deliveryAddressBox.setItems(presenter.getAvailableAddresses(selected));
                deliveryAddressBox.clear();
            }
        });

        deliveryAddressBox.addValueChangeListener(e ->
                formState.setSelectedAddressId(
                        e.getValue() != null ? e.getValue().getId() : null
                )
        );

        customerAddressId.addValueChangeListener(e ->
                formState.setCustomerAddressId(e.getValue())
        );

        Button saveBtn = new Button("Mapping opslaan", click -> {
            presenter.onSaveMapping(formState.getSelectedAccountId(), formState.getSelectedAddressId(), formState.getCustomerAddressId());

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

    public void setAccounts(List<Account> accounts) {
        accountBox.setItems(accounts);
    }

}
