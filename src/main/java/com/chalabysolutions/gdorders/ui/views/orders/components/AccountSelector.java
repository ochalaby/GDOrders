package com.chalabysolutions.gdorders.ui.views.orders.components;

import com.chalabysolutions.gdorders.model.accounts.Account;
import com.chalabysolutions.gdorders.ui.views.orders.logic.OrdersPresenter;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class AccountSelector extends VerticalLayout {

    public AccountSelector(OrdersPresenter presenter) {
        setPadding(false);
        setSpacing(false);

        ComboBox<Account> accountSelect = new ComboBox<>();
        accountSelect.setItemLabelGenerator(Account::getName);
        accountSelect.setItems(presenter.getAccounts());
        accountSelect.setValue(presenter.getSelectedAccount());
        accountSelect.setPlaceholder("Selecteer account");
        accountSelect.setWidth("250px");

        accountSelect.addValueChangeListener(e -> presenter.onAccountSelected(e.getValue()));

        add(accountSelect);
    }
}
