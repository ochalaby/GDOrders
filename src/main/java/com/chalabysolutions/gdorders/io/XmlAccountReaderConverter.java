package com.chalabysolutions.gdorders.io;

import com.chalabysolutions.gdorders.model.accounts.Account;
import com.chalabysolutions.gdorders.model.accounts.AccountAddress;
import com.chalabysolutions.gdorders.model.generic.Country;
import com.chalabysolutions.gdorders.model.generic.ShippingMethod;
import com.chalabysolutions.gdorders.model.generic.State;
import com.chalabysolutions.gdorders.model.internalaccounts.Accounts;
import com.chalabysolutions.gdorders.model.internalaccounts.EExact;

import java.util.ArrayList;
import java.util.List;

public class XmlAccountReaderConverter {

    public List<Account> convert(EExact in) {

        List<Account> result = new ArrayList<>();

        Accounts accountsWrapper = in.getAccounts();

        for (com.chalabysolutions.gdorders.model.internalaccounts.Account xmlAccount : accountsWrapper.getAccounts()) {

            Account account = new Account();

            account.setCode(xmlAccount.getCode());
            account.setSearchcode(xmlAccount.getSearchcode());
            account.setStatus(xmlAccount.getStatus());
            account.setId(xmlAccount.getID());
            account.setName(xmlAccount.getName());

            if (xmlAccount.getShippingMethod() != null) {
                ShippingMethod shippingMethod = new ShippingMethod();
                shippingMethod.setCode(xmlAccount.getShippingMethod().getCode());
                shippingMethod.setDescription(xmlAccount.getShippingMethod().getDescription());
                account.setShippingMethod(shippingMethod);
            }

            // Adresses
            List<AccountAddress> addresses = new ArrayList<>();
            for (com.chalabysolutions.gdorders.model.internalaccounts.Address xmlAddress : xmlAccount.getAddresses()) {
                AccountAddress address = new AccountAddress();

                address.setType(xmlAddress.getType());
                address.set_default(xmlAddress.getDefault());
                address.setId(xmlAddress.getID());

                address.setAddressLine1(xmlAddress.getAddressLine1());
                address.setAddressLine2(xmlAddress.getAddressLine2());
                address.setAddressLine3(xmlAddress.getAddressLine3());
                address.setPostalCode(xmlAddress.getPostalCode());
                address.setCity(xmlAddress.getCity());

                if (xmlAddress.getState() != null) {
                    State state = new State();
                    state.setCode(xmlAddress.getState().getCode());
                    state.setValue(xmlAddress.getState().getValue());
                    address.setState(state);
                }

                if (xmlAddress.getCountry() != null) {
                    Country country = new Country();
                    country.setCode(xmlAddress.getCountry().getCode());
                    country.setValue(xmlAddress.getCountry().getValue());
                    address.setCountry(country);
                }

                addresses.add(address);
            }

            account.getAddresses().addAll(addresses);
            result.add(account);
        }

        return result;
    }
}

