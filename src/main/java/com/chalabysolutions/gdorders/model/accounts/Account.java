package com.chalabysolutions.gdorders.model.accounts;

import com.chalabysolutions.gdorders.model.generic.ShippingMethod;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Account
{
    private String name;
    private String phone;
    private String phoneExt;
    private String fax;
    private String email;
    private String homePage;
    private String isSupplier;
    private String canDropShip;
    private String isBlocked;
    private String isReseller;
    private String isSales;
    private String isPurchase;
    private String showRemarkForSales;
    private List<AccountAddress> addresses;
    private String vatNumber;
    private String vatLiability;
    private String chamberOfCommerce;
    private String chamberOfCommerceEstablishment;
    private String glnNumber;
    private SalesCurrency salesCurrency;
    private PurchaseCurrency purchaseCurrency;
    private CreditLine creditLine;
    private Discount discount;
    private ShippingMethod shippingMethod;
    private String accountClassifications;
    private String isMailing;
    private String isCompetitor;
    private String startDate;
    private IntraStat intraStat;
    private String invoicingMethod;
    private String automaticProcessProposedEntry;
    private String deliveryAdvice;
    private String isAnonymised;
    private String peppolIdentifier;
    private String code;
    private String searchcode;
    private String status;
    private String id;

    public List<AccountAddress> getAddresses() {
        if (addresses == null) {
            addresses = new ArrayList<AccountAddress>();
        }
        return this.addresses;
    }
}
