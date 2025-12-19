package com.chalabysolutions.gdorders.service;

import com.chalabysolutions.gdorders.model.accounts.Account;
import com.chalabysolutions.gdorders.model.generic.ShippingMethod;
import com.chalabysolutions.gdorders.model.mapping.AddressMapping;
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
        if (c.getYourRef()!= null && !c.getYourRef().isEmpty()){
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

        // Set the deliveryAddress (based on customerDeliveryAddressId lookup in mapping)
        DeliveryAddress deliveryAddress = new DeliveryAddress();

        if (c.getDeliveryAddress() != null && mappingService != null) {
            AddressMapping mappedAddress = mappingService.findMapping(c.getDeliveryAddress().getId());

            if (mappedAddress != null) {
                deliveryAddress.setId(mappedAddress.internalAddressId);
                deliveryAddress.setAddressLine1(mappedAddress.deliveryAddress);
            }
        }

        internal.setDeliveryAddress(deliveryAddress);

        // Warehouse is taken from the settings
        Warehouse warehouse = new Warehouse();
        warehouse.setCode(warehouseCode);
        if (c.getWarehouse() != null) {
            warehouse.setDescription(c.getWarehouse().getDescription());
        }
        internal.setWarehouse(warehouse);

        // Shipping method from account list (look up based on selected account)
        // If not existing, then use default 'DDP'
        ShippingMethod shippingMethod = new ShippingMethod();
        if (c.getShippingMethod() != null) {
            shippingMethod.setCode(selectedAccount.getShippingMethod().getCode());
        } else {
            shippingMethod.setCode("DDP");
        }
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
