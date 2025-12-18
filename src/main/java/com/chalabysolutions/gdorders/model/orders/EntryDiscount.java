package com.chalabysolutions.gdorders.model.orders;

import lombok.Data;

@Data
public class EntryDiscount
{
    private String amountInclVAT;
    private String amountExclVAT;
    private String percentage;
}
