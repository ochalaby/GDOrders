package com.chalabysolutions.gdorders.ui.views.mapping.components;

import com.chalabysolutions.gdorders.model.accounts.Account;
import com.chalabysolutions.gdorders.model.accounts.AccountAddress;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Component
@Data
public class MappingFormState {
    private Account selectedAccount;
    private AccountAddress selectedAddress;
    private String customerAddressId;

    public void clear() {
        selectedAccount = null;
        selectedAddress = null;
        customerAddressId = null;
    }
}
