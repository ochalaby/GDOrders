package com.chalabysolutions.gdorders.model.orders;

import com.chalabysolutions.gdorders.model.generic.Currency;
import lombok.Data;

@Data
public class UnitPrice
{
    private Currency currency;
    private String value;
    private VAT vat;
    private String vatPercentage;
}
