package com.chalabysolutions.gdorders.ui.views.accounts;

import com.chalabysolutions.gdorders.service.AccountDataService;
import com.chalabysolutions.gdorders.service.SettingsService;
import com.chalabysolutions.gdorders.ui.layout.MainLayout;
import com.chalabysolutions.gdorders.ui.views.accounts.components.AccountsGrid;
import com.chalabysolutions.gdorders.ui.views.accounts.logic.AccountsPresenter;
import com.chalabysolutions.gdorders.ui.views.orders.components.StatusBar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;

@Route(value = "accounts", layout = MainLayout.class)
@PageTitle("Accounts")
@PreserveOnRefresh
public class AccountsView extends VerticalLayout {

    public AccountsView(AccountDataService account) {
        setSizeFull();
        setPadding(true);

        AccountsPresenter p = new AccountsPresenter(account);

        AccountsGrid accountsGrid = new AccountsGrid();
        StatusBar status = new StatusBar();

        p.setStatusBar(status);
        p.setAccountsGrid(accountsGrid);

        add(accountsGrid, status);
        expand(accountsGrid);

        p.reloadAccounts();
    }
}
