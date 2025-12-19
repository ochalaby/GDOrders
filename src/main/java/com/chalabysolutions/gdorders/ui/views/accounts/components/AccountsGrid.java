package com.chalabysolutions.gdorders.ui.views.accounts.components;

import com.chalabysolutions.gdorders.ui.views.accounts.AccountAddressRow;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;

public class AccountsGrid extends Grid<AccountAddressRow> {

    public AccountsGrid() {
        super(AccountAddressRow.class, false);
        setHeight("500px");
        addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        addColumn(row -> row.getAccount().getCode()).setHeader("Account code").setWidth("120px").setFlexGrow(0).setResizable(true);
        addColumn(row -> row.getAccount().getName()).setHeader("Account Naam").setWidth("220px").setFlexGrow(0).setResizable(true);
        addColumn(row -> row.getAddress().getType()).setHeader("Type").setWidth("80px").setFlexGrow(0).setResizable(true);
        addColumn(row -> row.getAddress().getAddressLine1()).setHeader("Adres").setWidth("240px").setFlexGrow(0).setResizable(true);
        addColumn(row -> row.getAddress().getPostalCode()).setHeader("Postcode").setWidth("120px").setFlexGrow(0).setResizable(true);
        addColumn(row -> row.getAddress().getCity()).setHeader("Plaats").setWidth("150px").setFlexGrow(0).setResizable(true);
        addColumn(row -> row.getAddress().getCountry().getCode()).setHeader("Land").setWidth("120px").setFlexGrow(0).setResizable(true);
        addColumn(row -> row.getAddress().getId()).setHeader("Inter adres ID").setWidth("350px").setFlexGrow(0).setResizable(true);
    }
}
