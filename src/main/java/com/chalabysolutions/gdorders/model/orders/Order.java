package com.chalabysolutions.gdorders.model.orders;

import com.chalabysolutions.gdorders.model.generic.ShippingMethod;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Order
{
    private String orderDate;
    private String deliveryDate;
    private String yourRef;
    private OrderedBy orderedBy;
    private OrderAccountContact orderAccountContact;
    private DeliverTo deliverTo;
    private DeliveryAddress deliveryAddress;
    private DeliveryAccountContact deliveryAccountContact;
    private InvoiceTo invoiceTo;
    private InvoiceAccountContact invoiceAccountContact;
    private Warehouse warehouse;
    private PaymentCondition paymentCondition;
    private ForeignAmount foreignAmount;
    private ShippingMethod shippingMethod;
    private SalesPerson salesPerson;
    private EntryDiscount entryDiscount;
    private List<OrderLine> orderLines;
    private String salesordernumber;
    private String status;

    public List<OrderLine> getOrderLines() {
        if (orderLines == null) {
            orderLines = new ArrayList<>();
        }
        return this.orderLines;
    }

}
