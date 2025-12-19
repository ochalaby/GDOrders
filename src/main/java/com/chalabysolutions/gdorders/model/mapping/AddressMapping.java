package com.chalabysolutions.gdorders.model.mapping;

import lombok.Data;

@Data
public class AddressMapping {
    private String accountName;
    private String accountCode;
    private String deliveryAddress;
    private String internalAddressId;
    private String accountAddressId;

}
