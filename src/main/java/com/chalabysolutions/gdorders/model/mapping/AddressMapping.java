package com.chalabysolutions.gdorders.model.mapping;

import lombok.Data;

@Data
public class AddressMapping {
    private String externalAddressId;
    private String internalAddressId;

    private DisplayInfo displayInfo;

}
