package com.chalabysolutions.gdorders.ui.views.accounts.logic;

import com.chalabysolutions.gdorders.io.XmlAccountReader;
import com.chalabysolutions.gdorders.model.accounts.Account;
import com.chalabysolutions.gdorders.service.*;
import com.chalabysolutions.gdorders.ui.views.StatusBar;
import com.chalabysolutions.gdorders.ui.views.StatusLevel;
import com.chalabysolutions.gdorders.ui.views.accounts.AccountAddressRow;
import com.chalabysolutions.gdorders.ui.views.accounts.components.AccountsGrid;

import java.util.List;
import java.util.stream.Collectors;

public class AccountsPresenter {

    private final AccountDataService accountService;
    private final XmlAccountReader accountReader = new XmlAccountReader();

    private AccountsGrid accountsGrid;
    private StatusBar status;

    public AccountsPresenter(AccountDataService accountService) {
        this.accountService = accountService;
    }

    public void setAccountsGrid(AccountsGrid accountsGrid) {
        this.accountsGrid = accountsGrid;
    }

    public void setStatusBar(StatusBar status) {
        this.status = status;
    }

    public List<Account> getAccounts() {
        return accountService.getAccounts();
    }

    public void reloadAccounts() {
        boolean loaded = accountService.loadFromSettings();

        if (accountsGrid != null) {
            if (loaded) {
                loadAccountGrid();
            } else {
                accountsGrid.setItems(List.of()); // lege lijst
            }
        }

        updateStatus();
    }

    private void updateStatus() {
        if (status == null) return;

        status.clear(); // oude meldingen wissen
        accountService.getErrorMessage()
                .ifPresentOrElse(
                        msg -> status.show(StatusLevel.ERROR, msg),
                        () -> status.show(StatusLevel.INFO, "Accounts geladen")
                );
    }

    private void loadAccountGrid() {
        List<AccountAddressRow> rows = accountService.getAccounts().stream()
                .flatMap(acc -> acc.getAddresses().stream()
                        .map(addr -> new AccountAddressRow(acc, addr)))
                .collect(Collectors.toList());
        accountsGrid.setItems(rows);
        accountsGrid.getDataProvider().refreshAll();
    }
}
