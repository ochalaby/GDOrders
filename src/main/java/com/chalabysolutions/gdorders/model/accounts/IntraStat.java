package com.chalabysolutions.gdorders.model.accounts;

import lombok.Data;

@Data
public class IntraStat
{
    private String system;
    private String transactionA;
    private String transactionB;
    private String transportMethod;
    private String deliveryTerm;
    private String area;
}
