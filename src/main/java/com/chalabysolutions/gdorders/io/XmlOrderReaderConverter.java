package com.chalabysolutions.gdorders.io;

import com.chalabysolutions.gdorders.model.customerorders.EExact;
import com.chalabysolutions.gdorders.model.customerorders.SalesOrder;
import com.chalabysolutions.gdorders.model.customerorders.SalesOrderLine;
import com.chalabysolutions.gdorders.model.customerorders.SalesOrders;
import com.chalabysolutions.gdorders.model.generic.Country;
import com.chalabysolutions.gdorders.model.generic.Currency;
import com.chalabysolutions.gdorders.model.generic.ShippingMethod;
import com.chalabysolutions.gdorders.model.generic.State;
import com.chalabysolutions.gdorders.model.orders.*;

import java.util.ArrayList;
import java.util.List;

public class XmlOrderReaderConverter {

    public List<Order> convert(EExact in) {

        List<Order> result = new ArrayList<>();

        SalesOrders ordersWrapper = in.getSalesOrders();

        for (SalesOrder customerOrder : ordersWrapper.getSalesOrders()) {

            Order order = new Order();

            order.setSalesordernumber(customerOrder.getSalesordernumber());
            order.setStatus(customerOrder.getStatus());
            order.setOrderDate(customerOrder.getOrderDate());
            order.setDeliveryDate(customerOrder.getDeliveryDate());
            order.setYourRef(customerOrder.getYourRef());

            // Set order by
            if (customerOrder.getOrderedBy() != null) {
                OrderedBy orderedBy = new OrderedBy();
                orderedBy.setCode(customerOrder.getOrderedBy().getCode());
                orderedBy.setId(customerOrder.getOrderedBy().getID());
                orderedBy.setName(customerOrder.getOrderedBy().getName());
                order.setOrderedBy(orderedBy);
            }


            // Set order account contact
            if (customerOrder.getOrderAccountContact()  != null) {
                OrderAccountContact orderAccountContact = new OrderAccountContact();
                orderAccountContact.setId(customerOrder.getOrderAccountContact().getID());
                orderAccountContact.setLastName(customerOrder.getOrderAccountContact().getLastName());
                orderAccountContact.setMiddleName(customerOrder.getOrderAccountContact().getMiddleName());
                orderAccountContact.setFirstName(customerOrder.getOrderAccountContact().getFirstName());
                orderAccountContact.setInitials(customerOrder.getOrderAccountContact().getInitials());
                orderAccountContact.setFullName(customerOrder.getOrderAccountContact().getFullName());
                orderAccountContact.setEmail(customerOrder.getOrderAccountContact().getEmail());
                order.setOrderAccountContact(orderAccountContact);
            }


            // Set deliver to
            if (customerOrder.getDeliverTo() != null) {
                DeliverTo deliverTo = new DeliverTo();
                deliverTo.setCode(customerOrder.getDeliverTo().getCode());
                deliverTo.setId(customerOrder.getDeliverTo().getID());
                deliverTo.setName(customerOrder.getDeliverTo().getName());
                order.setDeliverTo(deliverTo);
            }


            // Set delivery address
            if (customerOrder.getDeliveryAddress()  != null) {
                DeliveryAddress deliveryAddress = new DeliveryAddress();
                deliveryAddress.setId(customerOrder.getDeliveryAddress().getID());
                deliveryAddress.setAddressLine1(customerOrder.getDeliveryAddress().getAddressLine1());
                deliveryAddress.setAddressLine2(customerOrder.getDeliveryAddress().getAddressLine2());
                deliveryAddress.setAddressLine3(customerOrder.getDeliveryAddress().getAddressLine3());
                deliveryAddress.setPostalCode(customerOrder.getDeliveryAddress().getPostalCode());
                deliveryAddress.setCity(customerOrder.getDeliveryAddress().getCity());

                if (customerOrder.getDeliveryAddress().getCountry() != null) {
                    Country internalCountry = new Country();
                    internalCountry.setCode(customerOrder.getDeliveryAddress().getCountry().getCode());
                    internalCountry.setValue(customerOrder.getDeliveryAddress().getCountry().getValue());
                    deliveryAddress.setCountry(internalCountry);
                }

                if (customerOrder.getDeliveryAddress().getState() != null) {
                    State state =  new State();
                    state.setCode(customerOrder.getDeliveryAddress().getState().getCode());
                    state.setValue(customerOrder.getDeliveryAddress().getState().getValue());
                    deliveryAddress.setState(state);
                }

                if (customerOrder.getDeliveryAddress().getContact() != null) {
                    Contact deliveryAddressContact = new Contact();
                    deliveryAddressContact.setId(customerOrder.getDeliveryAddress().getContact().getID());
                    deliveryAddressContact.setLastName(customerOrder.getDeliveryAddress().getContact().getLastName());
                    deliveryAddressContact.setMiddleName(customerOrder.getDeliveryAddress().getContact().getMiddleName());
                    deliveryAddressContact.setFirstName(customerOrder.getDeliveryAddress().getContact().getFirstName());
                    deliveryAddressContact.setInitials(customerOrder.getDeliveryAddress().getContact().getInitials());
                    deliveryAddressContact.setFullName(customerOrder.getDeliveryAddress().getContact().getFullName());
                    deliveryAddressContact.setEmail(customerOrder.getDeliveryAddress().getContact().getEmail());
                    deliveryAddress.setContact(deliveryAddressContact);
                }

                order.setDeliveryAddress(deliveryAddress);
            }


            // Set delivery account contact
            if (customerOrder.getDeliveryAccountContact() != null) {
                DeliveryAccountContact deliveryAccountContact = new DeliveryAccountContact();
                deliveryAccountContact.setId(customerOrder.getDeliveryAccountContact().getID());
                deliveryAccountContact.setLastName(customerOrder.getDeliveryAccountContact().getLastName());
                deliveryAccountContact.setMiddleName(customerOrder.getDeliveryAccountContact().getMiddleName());
                deliveryAccountContact.setFirstName(customerOrder.getDeliveryAccountContact().getFirstName());
                deliveryAccountContact.setInitials(customerOrder.getDeliveryAccountContact().getInitials());
                deliveryAccountContact.setFullName(customerOrder.getDeliveryAccountContact().getFullName());
                deliveryAccountContact.setEmail(customerOrder.getDeliveryAccountContact().getEmail());
                order.setDeliveryAccountContact(deliveryAccountContact);
            }


            // Set invoice to
            if (customerOrder.getInvoiceTo()  != null) {
                InvoiceTo invoiceTo = new InvoiceTo();
                invoiceTo.setId(customerOrder.getInvoiceTo().getID());
                invoiceTo.setCode(customerOrder.getInvoiceTo().getCode());
                invoiceTo.setName(customerOrder.getInvoiceTo().getName());
                order.setInvoiceTo(invoiceTo);
            }


            // Set delivery account contact
            if (customerOrder.getInvoiceAccountContact()  != null) {
                InvoiceAccountContact invoiceAccountContact = new InvoiceAccountContact();
                invoiceAccountContact.setId(customerOrder.getInvoiceAccountContact().getID());
                invoiceAccountContact.setLastName(customerOrder.getInvoiceAccountContact().getLastName());
                invoiceAccountContact.setMiddleName(customerOrder.getInvoiceAccountContact().getMiddleName());
                invoiceAccountContact.setFirstName(customerOrder.getInvoiceAccountContact().getFirstName());
                invoiceAccountContact.setInitials(customerOrder.getInvoiceAccountContact().getInitials());
                invoiceAccountContact.setFullName(customerOrder.getInvoiceAccountContact().getFullName());
                invoiceAccountContact.setEmail(customerOrder.getInvoiceAccountContact().getEmail());
                order.setInvoiceAccountContact(invoiceAccountContact);
            }


            // Set Warehouse
            if (customerOrder.getWarehouse() != null) {
                Warehouse warehouse = new Warehouse();
                warehouse.setCode(customerOrder.getWarehouse().getCode());
                warehouse.setDescription(customerOrder.getWarehouse().getDescription());
                order.setWarehouse(warehouse);
            }


            // Set payment condition
            if (customerOrder.getPaymentCondition() != null) {
                PaymentCondition paymentCondition = new PaymentCondition();
                paymentCondition.setCode(customerOrder.getPaymentCondition().getCode());
                paymentCondition.setDescription(customerOrder.getPaymentCondition().getDescription());
                order.setPaymentCondition(paymentCondition);
            }


            // Set order foreign amount
            if (customerOrder.getForeignAmount() != null) {
                ForeignAmount foreignAmount = new ForeignAmount();

                if (customerOrder.getForeignAmount().getCurrency() != null) {
                    Currency currency = new Currency();
                    currency.setCode(customerOrder.getForeignAmount().getCurrency().getCode());
                    currency.setValue(customerOrder.getForeignAmount().getCurrency().getValue());
                    foreignAmount.setCurrency(currency);
                }

                foreignAmount.setValue(customerOrder.getForeignAmount().getValue());
                foreignAmount.setRate(customerOrder.getForeignAmount().getRate());

                if (customerOrder.getForeignAmount().getPaymentDiscountAmount() != null) {
                    PaymentDiscountAmount paymentDiscountAmount = new PaymentDiscountAmount();
                    paymentDiscountAmount.setValue(customerOrder.getForeignAmount().getPaymentDiscountAmount().getValue());
                    foreignAmount.setPaymentDiscountAmount(paymentDiscountAmount);
                }

                order.setForeignAmount(foreignAmount);
            }


            // Set shipping method
            if (customerOrder.getShippingMethod() != null) {
                ShippingMethod shippingMethod = new ShippingMethod();
                shippingMethod.setCode(customerOrder.getShippingMethod().getCode());
                shippingMethod.setDescription(customerOrder.getShippingMethod().getDescription());
                order.setShippingMethod(shippingMethod);
            }


            // Set sales person
            if (customerOrder.getSalesPerson() != null) {
                SalesPerson salesPerson = new SalesPerson();
                salesPerson.setId(customerOrder.getSalesPerson().getId());
                salesPerson.setFullName(customerOrder.getSalesPerson().getFullName());
                order.setSalesPerson(salesPerson);
            }


            // Set Entry discount
            if (customerOrder.getEntryDiscount() != null) {
                EntryDiscount entryDiscount = new EntryDiscount();
                entryDiscount.setAmountInclVAT(customerOrder.getEntryDiscount().getAmountInclVAT());
                entryDiscount.setAmountExclVAT(customerOrder.getEntryDiscount().getAmountExclVAT());
                entryDiscount.setPercentage(customerOrder.getEntryDiscount().getPercentage());
                order.setEntryDiscount(entryDiscount);
            }


            // Order lines
            List<OrderLine> orderLines = new ArrayList<>();
            for (SalesOrderLine cSol : customerOrder.getSalesOrderLines()) {
                OrderLine orderLine = new OrderLine();

                orderLine.setLine(cSol.getLine());
                orderLine.setDescription(cSol.getDescription());
                orderLine.setQuantity(cSol.getQuantity());
                orderLine.setDeliveryDate(cSol.getDeliveryDate());
                orderLine.setCostPriceFC(cSol.getCostPriceFC());
                orderLine.setMargin(cSol.getMargin());
                orderLine.setDiscountPercentage(cSol.getDiscountPercentage());
                orderLine.setUseDropShipment(cSol.getUseDropShipment());

                // Set item
                if (cSol.getItem() != null) {
                    Item item = new Item();
                    item.setId(cSol.getItem().getID());
                    item.setCode(cSol.getItem().getCode());
                    item.setDescription(cSol.getItem().getDescription());
                    orderLine.setItem(item);
                }

                // Set unit
                if (cSol.getUnit() != null) {
                    Unit unit = new Unit();
                    unit.setCode(cSol.getUnit().getCode());
                    unit.setDescription(cSol.getUnit().getDescription());
                    orderLine.setUnit(unit);
                }

                // Set unit price
                if (cSol.getUnitPrice() != null) {
                    UnitPrice unitPrice = new UnitPrice();

                    if (cSol.getUnitPrice().getCurrency() != null) {
                        Currency unitCurrency = new Currency();
                        unitCurrency.setCode(cSol.getUnitPrice().getCurrency().getCode());
                        unitCurrency.setValue(cSol.getUnitPrice().getCurrency().getValue());
                        unitPrice.setCurrency(unitCurrency);
                    }

                    unitPrice.setValue(cSol.getUnitPrice().getValue());

                    if (cSol.getUnitPrice().getVAT() != null) {
                        VAT unitPriceVAT = new VAT();
                        unitPriceVAT.setCode(cSol.getUnitPrice().getVAT().getCode());
                        unitPriceVAT.setDescription(cSol.getUnitPrice().getVAT().getDescription());
                        unitPrice.setVat(unitPriceVAT);
                    }

                    unitPrice.setVatPercentage(cSol.getUnitPrice().getVATPercentage());
                    orderLine.setUnitPrice(unitPrice);
                }

                // Set net price
                if (cSol.getNetPrice() != null) {
                    NetPrice netPrice = new NetPrice();

                    if (cSol.getNetPrice().getCurrency() != null) {
                        Currency netCurrency = new Currency();
                        netCurrency.setCode(cSol.getNetPrice().getCurrency().getCode());
                        netPrice.setCurrency(netCurrency);
                    }

                    netPrice.setValue(cSol.getNetPrice().getValue());

                    if (cSol.getNetPrice().getVAT() != null) {
                        VAT netPriceVAT = new VAT();
                        netPriceVAT.setCode(cSol.getNetPrice().getVAT().getCode());
                        netPriceVAT.setDescription(cSol.getNetPrice().getVAT().getDescription());
                        netPrice.setVat(netPriceVAT);
                    }

                    netPrice.setVatPercentage(cSol.getNetPrice().getVATPercentage());
                    orderLine.setNetPrice(netPrice);
                }

                // Set order line foreign amount
                if (cSol.getForeignAmount() != null) {
                    ForeignAmount internalOLForeignAmount = new ForeignAmount();

                    if (cSol.getForeignAmount().getCurrency()  != null) {
                        Currency internalOLCurrency = new Currency();
                        internalOLCurrency.setCode(cSol.getForeignAmount().getCurrency().getCode());
                        internalOLCurrency.setValue(cSol.getForeignAmount().getCurrency().getValue());
                        internalOLForeignAmount.setCurrency(internalOLCurrency);
                    }

                    internalOLForeignAmount.setValue(cSol.getForeignAmount().getValue());
                    internalOLForeignAmount.setRate(cSol.getForeignAmount().getRate());
                    internalOLForeignAmount.setVatBaseAmount(cSol.getForeignAmount().getVATBaseAmount());
                    internalOLForeignAmount.setVatAmount(cSol.getForeignAmount().getVATAmount());

                    orderLine.setForeignAmount(internalOLForeignAmount);
                }

                orderLines.add(orderLine);
            }

            order.getOrderLines().addAll(orderLines);
            result.add(order);
        }

        return result;
    }
}

