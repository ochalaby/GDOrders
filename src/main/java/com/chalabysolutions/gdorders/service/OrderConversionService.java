package com.chalabysolutions.gdorders.service;

import com.chalabysolutions.gdorders.model.accounts.Account;
import com.chalabysolutions.gdorders.model.generic.ShippingMethod;
import com.chalabysolutions.gdorders.model.orders.*;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
public class OrderConversionService {

    private Account selectedAccount;
    private String warehouseCode;
    private MappingService mappingService;

    public Order convert(Order c) {
        Order internal = new Order();
        internal.setOrderDate(c.getOrderDate());
        internal.setDeliveryDate(c.getDeliveryDate());

        // Use SalesOrderNumber as YourRef and if exist add original YourRef
        String yourRef = c.getSalesordernumber();
        if (!c.getYourRef().isEmpty()){
            yourRef += "_" + c.getYourRef();
        }
        internal.setYourRef(yourRef);

        OrderedBy orderedBy = new OrderedBy();
        orderedBy.setId(selectedAccount.getId());
        orderedBy.setCode(selectedAccount.getCode());
        orderedBy.setName(selectedAccount.getName());
        internal.setOrderedBy(orderedBy);

        DeliverTo deliverTo = new DeliverTo();
        deliverTo.setId(selectedAccount.getId());
        deliverTo.setCode(selectedAccount.getCode());
        deliverTo.setName(selectedAccount.getName());
        internal.setDeliverTo(deliverTo);

        InvoiceTo invoiceTo = new InvoiceTo();
        invoiceTo.setId(selectedAccount.getId());
        invoiceTo.setCode(selectedAccount.getCode());
        invoiceTo.setName(selectedAccount.getName());
        internal.setInvoiceTo(invoiceTo);

        // Set the deliveryAddress ID (based on account lookup)
        DeliveryAddress deliveryAddress = new DeliveryAddress();

        if (mappingService != null) {
            String customerDeliveryId = c.getDeliveryAddress().getId();
            String mappedAddressId = mappingService.findInternalAddressId(customerDeliveryId);

            if (mappedAddressId != null) {
                deliveryAddress.setId(mappedAddressId);
            }
        }

        internal.setDeliveryAddress(deliveryAddress);

        // Warehouse is taken from the settings
        Warehouse warehouse = new Warehouse();
        warehouse.setCode(warehouseCode);
        warehouse.setDescription(c.getWarehouse().getDescription());
        internal.setWarehouse(warehouse);

        // Shipping method is always 'DDP' for now
        //TODO: Get shipping method from account list (look up based on selected account)
        ShippingMethod shippingMethod = new ShippingMethod();
        shippingMethod.setCode("DDP");
        internal.setShippingMethod(shippingMethod);

        // kopieer orderlines
        List<OrderLine> newLines = new ArrayList<>();
        for (OrderLine cl : c.getOrderLines()) {
            OrderLine nl = new OrderLine();
            nl.setLine(cl.getLine());
            nl.setItem(cl.getItem());
            nl.setDescription(cl.getDescription());
            nl.setQuantity(cl.getQuantity());
            nl.setDeliveryDate(cl.getDeliveryDate());
            newLines.add(nl);
        }
        internal.setOrderLines(newLines);

        return internal;
    }
}
