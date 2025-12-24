package com.chalabysolutions.gdorders.model.generic;

import lombok.Data;

@Data
public class Address
{
    private String id;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String postalCode;
    private String city;
    private State state;
    private Country country;

    public String getFormattedAddress()
    {
        String formattedAddress = addressLine1 + ", " + postalCode + " " + city;
        if (country != null){
            formattedAddress += ", " + country.getCode();
        }
        return formattedAddress;
    }
}
