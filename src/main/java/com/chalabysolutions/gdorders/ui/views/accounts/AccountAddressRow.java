package com.chalabysolutions.gdorders.ui.views.accounts;

import com.chalabysolutions.gdorders.model.accounts.Account;
import com.chalabysolutions.gdorders.model.accounts.AccountAddress;

public class AccountAddressRow {
    private final Account account;
    private final AccountAddress address;

    public AccountAddressRow(Account account, AccountAddress address) {
        this.account = account;
        this.address = address;
    }

    public Account getAccount() {
        return account;
    }

    public AccountAddress getAddress() {
        return address;
    }
}
