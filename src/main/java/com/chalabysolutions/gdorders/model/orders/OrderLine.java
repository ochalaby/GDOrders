package com.chalabysolutions.gdorders.model.orders;

import lombok.Data;

@Data
public class OrderLine
{
    private String description;
    private Item item;
    private String quantity;
    private String deliveryDate;
    private Unit unit;
    private UnitPrice unitPrice;
    private NetPrice netPrice;
    private String costPriceFC;
    private String margin;
    private ForeignAmount foreignAmount;
    private String discountPercentage;
    private String useDropShipment;
    private String line;
}
