package com.chalabysolutions.gdorders.model.orders;

import com.chalabysolutions.gdorders.model.generic.Currency;
import lombok.Data;

@Data
public class ForeignAmount
{
    private Currency currency;
    private String value;
    private String rate;
    private String vatBaseAmount;
    private String vatAmount;
    private PaymentDiscountAmount paymentDiscountAmount;
}
