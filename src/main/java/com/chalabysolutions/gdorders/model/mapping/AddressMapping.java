package com.chalabysolutions.gdorders.model.mapping;

import lombok.Getter;
import lombok.Setter;

public class AddressMapping {
    public String accountName;
    public String accountCode;
    public String deliveryAddress;
    public String internalAddressId;

    @Setter
    @Getter
    private String accountAddressId; // private!

}
