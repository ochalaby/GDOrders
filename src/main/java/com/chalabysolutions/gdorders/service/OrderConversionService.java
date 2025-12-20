package com.chalabysolutions.gdorders.service;

import com.chalabysolutions.gdorders.model.accounts.Account;
import com.chalabysolutions.gdorders.model.generic.ShippingMethod;
import com.chalabysolutions.gdorders.model.orders.*;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
public class OrderConversionService {

    private final AccountDataService accountService;
    private final MappingDataService mappingService;
    private final SettingsService settingsService;

    public OrderConversionService(AccountDataService accountService, MappingDataService mappingService, SettingsService settingsService) {
        this.accountService = accountService;
        this.mappingService = mappingService;
        this.settingsService = settingsService;
    }

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

        Account selectedAccount = accountService.getSelectedAccount();

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
        if (c.getDeliveryAddress() != null) {

            // Find matching internal addressId
            String internalAddressId = mappingService.findInternalAddressId(c.getDeliveryAddress().getId());

            DeliveryAddress deliveryAddress = new DeliveryAddress();
            deliveryAddress.setId(internalAddressId);

            if (internalAddressId != null) {
                // If internal addressId exists, find matching address details
                accountService
                        .findAddressById(internalAddressId)
                        .ifPresent(internalAddress -> {
                            deliveryAddress.setAddressLine1(internalAddress.getAddressLine1());
                            deliveryAddress.setAddressLine2(internalAddress.getAddressLine2());
                            deliveryAddress.setPostalCode(internalAddress.getPostalCode());
                            deliveryAddress.setCity(internalAddress.getCity());
                            deliveryAddress.setState(internalAddress.getState());
                            deliveryAddress.setCountry(internalAddress.getCountry());
                        });
            }

            internal.setDeliveryAddress(deliveryAddress);
        }

        // Warehouse is taken from the settings
        Warehouse warehouse = new Warehouse();
        warehouse.setCode(settingsService.getWarehouseCode());
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
