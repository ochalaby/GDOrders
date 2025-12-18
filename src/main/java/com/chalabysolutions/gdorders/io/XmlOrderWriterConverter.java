package com.chalabysolutions.gdorders.io;

import com.chalabysolutions.gdorders.model.orders.Order;
import com.chalabysolutions.gdorders.model.orders.OrderLine;
import com.chalabysolutions.gdorders.model.internalorders.*;
import jakarta.xml.bind.JAXBException;

import java.util.ArrayList;
import java.util.List;

public class XmlOrderWriterConverter {

    public EExact convert(List<Order> orders) throws JAXBException {

        EExact result = new EExact();

        SalesOrders salesOrders = new SalesOrders();
        result.setSalesOrders(salesOrders);

        for (Order  order : orders) {

            SalesOrder internalOrder = new SalesOrder();

            internalOrder.setSalesordernumber(order.getSalesordernumber());
            internalOrder.setStatus(order.getStatus());
            internalOrder.setOrderDate(order.getOrderDate());
            internalOrder.setDeliveryDate(order.getDeliveryDate());
            internalOrder.setYourRef(order.getYourRef());

            // Set order by
            if (order.getOrderedBy() != null) {
                OrderedBy internalOrderedBy = new OrderedBy();
                internalOrderedBy.setCode(order.getOrderedBy().getCode());
                internalOrderedBy.setID(order.getOrderedBy().getId());
                internalOrderedBy.setName(order.getOrderedBy().getName());
                internalOrder.setOrderedBy(internalOrderedBy);
            }

            // Set order account contact
            if (order.getOrderAccountContact()  != null) {
                OrderAccountContact internalOrderAccount = new OrderAccountContact();
                internalOrderAccount.setID(order.getOrderAccountContact().getId());
                internalOrderAccount.setLastName(order.getOrderAccountContact().getLastName());
                internalOrderAccount.setMiddleName(order.getOrderAccountContact().getMiddleName());
                internalOrderAccount.setFirstName(order.getOrderAccountContact().getFirstName());
                internalOrderAccount.setInitials(order.getOrderAccountContact().getInitials());
                internalOrderAccount.setFullName(order.getOrderAccountContact().getFullName());
                internalOrderAccount.setEmail(order.getOrderAccountContact().getEmail());
                internalOrder.setOrderAccountContact(internalOrderAccount);
            }

            // Set deliver to
            if (order.getDeliverTo() != null) {
                DeliverTo internalDeliverTo = new DeliverTo();
                internalDeliverTo.setCode(order.getDeliverTo().getCode());
                internalDeliverTo.setID(order.getDeliverTo().getId());
                internalDeliverTo.setName(order.getDeliverTo().getName());
                internalOrder.setDeliverTo(internalDeliverTo);
            }


            // Set delivery address
            if (order.getDeliveryAddress()  != null) {
                DeliveryAddress internalDeliveryAddress = new DeliveryAddress();
                internalDeliveryAddress.setID(order.getDeliveryAddress().getId());
                internalDeliveryAddress.setAddressLine1Attr(order.getDeliveryAddress().getAddressLine1());
                internalDeliveryAddress.setAddressLine2Attr(order.getDeliveryAddress().getAddressLine2());
                internalDeliveryAddress.setAddressLine3Attr(order.getDeliveryAddress().getAddressLine3());
                internalDeliveryAddress.setPostalCodeAttr(order.getDeliveryAddress().getPostalCode());
                internalDeliveryAddress.setCityAttr(order.getDeliveryAddress().getCity());

                if (order.getDeliveryAddress().getState()  != null) {
                    internalDeliveryAddress.setStateCodeAttr(order.getDeliveryAddress().getState().getCode());
                }

                if (order.getDeliveryAddress().getCountry()  != null) {
                    internalDeliveryAddress.setCountryCodeAttr(order.getDeliveryAddress().getCountry().getValue());
                }

                internalDeliveryAddress.setAddressLine1(order.getDeliveryAddress().getAddressLine1());
                internalDeliveryAddress.setAddressLine2(order.getDeliveryAddress().getAddressLine2());
                internalDeliveryAddress.setAddressLine3(order.getDeliveryAddress().getAddressLine3());
                internalDeliveryAddress.setPostalCode(order.getDeliveryAddress().getPostalCode());
                internalDeliveryAddress.setCity(order.getDeliveryAddress().getCity());

                if (order.getDeliveryAddress().getCountry() != null) {
                    Country internalCountry = new Country();
                    internalCountry.setCode(order.getDeliveryAddress().getCountry().getCode());
                    internalCountry.setValue(order.getDeliveryAddress().getCountry().getValue());
                    internalDeliveryAddress.setCountry(internalCountry);
                }

                if (order.getDeliveryAddress().getContact() != null) {
                    Contact internalDeliveryAddressContact = new Contact();
                    internalDeliveryAddressContact.setID(order.getDeliveryAddress().getContact().getId());
                    internalDeliveryAddressContact.setLastName(order.getDeliveryAddress().getContact().getLastName());
                    internalDeliveryAddressContact.setMiddleName(order.getDeliveryAddress().getContact().getMiddleName());
                    internalDeliveryAddressContact.setFirstName(order.getDeliveryAddress().getContact().getFirstName());
                    internalDeliveryAddressContact.setInitials(order.getDeliveryAddress().getContact().getInitials());
                    internalDeliveryAddressContact.setFullName(order.getDeliveryAddress().getContact().getFullName());
                    internalDeliveryAddressContact.setEmail(order.getDeliveryAddress().getContact().getEmail());
                    internalDeliveryAddress.setContact(internalDeliveryAddressContact);
                }

                internalOrder.setDeliveryAddress(internalDeliveryAddress);
            }


            // Set delivery account contact
            if (order.getDeliveryAccountContact() != null) {
                DeliveryAccountContact internalDeliveryAccountContact = new DeliveryAccountContact();
                internalDeliveryAccountContact.setID(order.getDeliveryAccountContact().getId());
                internalDeliveryAccountContact.setLastName(order.getDeliveryAccountContact().getLastName());
                internalDeliveryAccountContact.setMiddleName(order.getDeliveryAccountContact().getMiddleName());
                internalDeliveryAccountContact.setFirstName(order.getDeliveryAccountContact().getFirstName());
                internalDeliveryAccountContact.setInitials(order.getDeliveryAccountContact().getInitials());
                internalDeliveryAccountContact.setFullName(order.getDeliveryAccountContact().getFullName());
                internalDeliveryAccountContact.setEmail(order.getDeliveryAccountContact().getEmail());
                internalOrder.setDeliveryAccountContact(internalDeliveryAccountContact);
            }


            // Set invoice to
            if (order.getInvoiceTo()  != null) {
                InvoiceTo internalInvoiceTo = new InvoiceTo();
                internalInvoiceTo.setID(order.getInvoiceTo().getId());
                internalInvoiceTo.setCode(order.getInvoiceTo().getCode());
                internalInvoiceTo.setName(order.getInvoiceTo().getName());
                internalOrder.setInvoiceTo(internalInvoiceTo);
            }


            // Set invoice account contact
            if (order.getInvoiceAccountContact()  != null) {
                InvoiceAccountContact internalInvoiceAccountContact = new InvoiceAccountContact();
                internalInvoiceAccountContact.setID(order.getInvoiceAccountContact().getId());
                internalInvoiceAccountContact.setLastName(order.getInvoiceAccountContact().getLastName());
                internalInvoiceAccountContact.setMiddleName(order.getInvoiceAccountContact().getMiddleName());
                internalInvoiceAccountContact.setFirstName(order.getInvoiceAccountContact().getFirstName());
                internalInvoiceAccountContact.setInitials(order.getInvoiceAccountContact().getInitials());
                internalInvoiceAccountContact.setFullName(order.getInvoiceAccountContact().getFullName());
                internalInvoiceAccountContact.setEmail(order.getInvoiceAccountContact().getEmail());
                internalOrder.setInvoiceAccountContact(internalInvoiceAccountContact);
            }


            // Set Warehouse
            if (order.getWarehouse() != null) {
                Warehouse internalWarehouse = new Warehouse();
                internalWarehouse.setCode(order.getWarehouse().getCode());
                internalWarehouse.setDescription(order.getWarehouse().getDescription());
                internalOrder.setWarehouse(internalWarehouse);
            }


            // Set payment condition
            if (order.getPaymentCondition() != null) {
                PaymentCondition internalPaymentCondition = new PaymentCondition();
                internalPaymentCondition.setCode(order.getPaymentCondition().getCode());
                internalPaymentCondition.setDescription(order.getPaymentCondition().getDescription());
                internalOrder.setPaymentCondition(internalPaymentCondition);
            }


            // Set order foreign amount
            if (order.getForeignAmount() != null) {
                ForeignAmount internalOrderForeignAmount = new ForeignAmount();

                if (order.getForeignAmount().getCurrency() != null) {
                    Currency internalCurrency = new Currency();
                    internalCurrency.setCode(order.getForeignAmount().getCurrency().getCode());
                    internalCurrency.setValue(order.getForeignAmount().getCurrency().getValue());
                    internalOrderForeignAmount.setCurrency(internalCurrency);
                }

                internalOrderForeignAmount.setValue(order.getForeignAmount().getValue());
                internalOrderForeignAmount.setRate(order.getForeignAmount().getRate());

                if (order.getForeignAmount().getPaymentDiscountAmount() != null) {
                    PaymentDiscountAmount internalPaymentDiscountAmount = new PaymentDiscountAmount();
                    internalPaymentDiscountAmount.setValue(order.getForeignAmount().getPaymentDiscountAmount().getValue());
                    internalOrderForeignAmount.setPaymentDiscountAmount(internalPaymentDiscountAmount);
                }

                internalOrder.setForeignAmount(internalOrderForeignAmount);
            }


            // Set shipping method
            if (order.getShippingMethod() != null) {
                ShippingMethod internalShippingMethod = new ShippingMethod();
                internalShippingMethod.setCode(order.getShippingMethod().getCode());
                internalShippingMethod.setDescription(order.getShippingMethod().getDescription());
                internalOrder.setShippingMethod(internalShippingMethod);
            }


            // Set sales person
            if (order.getSalesPerson() != null) {
                SalesPerson internalSalesPerson = new SalesPerson();
                internalSalesPerson.setId(order.getSalesPerson().getId());
                internalSalesPerson.setFullName(order.getSalesPerson().getFullName());
                internalOrder.setSalesPerson(internalSalesPerson);
            }


            // Set Entry discount
            if (order.getEntryDiscount() != null) {
                EntryDiscount internalEntryDiscount = new EntryDiscount();
                internalEntryDiscount.setAmountInclVAT(order.getEntryDiscount().getAmountInclVAT());
                internalEntryDiscount.setAmountExclVAT(order.getEntryDiscount().getAmountExclVAT());
                internalEntryDiscount.setPercentage(order.getEntryDiscount().getPercentage());
                internalOrder.setEntryDiscount(internalEntryDiscount);
            }


            // Order lines
            List<SalesOrderLine> internalOrderlinesList = new ArrayList<>();
            for (OrderLine orderLine : order.getOrderLines()) {
                SalesOrderLine iSol = new SalesOrderLine();

                iSol.setLine(orderLine.getLine());
                iSol.setDescription(orderLine.getDescription());
                iSol.setQuantity(orderLine.getQuantity());
                iSol.setDeliveryDate(orderLine.getDeliveryDate());
                iSol.setCostPriceFC(orderLine.getCostPriceFC());
                iSol.setMargin(orderLine.getMargin());
                iSol.setDiscountPercentage(orderLine.getDiscountPercentage());
                iSol.setUseDropShipment(orderLine.getUseDropShipment());

                // Set item
                if (orderLine.getItem() != null) {
                    Item internalItem = new Item();
                    internalItem.setID(orderLine.getItem().getId());
                    internalItem.setCode(orderLine.getItem().getCode());
                    internalItem.setDescription(orderLine.getItem().getDescription());
                    iSol.setItem(internalItem);
                }


                // Set unit
                if (orderLine.getUnit() != null) {
                    Unit internalUnit = new Unit();
                    internalUnit.setCode(orderLine.getUnit().getCode());
                    internalUnit.setDescription(orderLine.getUnit().getDescription());
                    iSol.setUnit(internalUnit);
                }


                // Set unit price
                if (orderLine.getUnitPrice() != null) {
                    UnitPrice internalUnitPrice = new UnitPrice();

                    if (orderLine.getUnitPrice().getCurrency() != null) {
                        Currency internalUnitCurrency = new Currency();
                        internalUnitCurrency.setCode(orderLine.getUnitPrice().getCurrency().getCode());
                        internalUnitCurrency.setValue(orderLine.getUnitPrice().getCurrency().getValue());
                        internalUnitPrice.setCurrency(internalUnitCurrency);
                    }

                    internalUnitPrice.setValue(orderLine.getUnitPrice().getValue());

                    if (orderLine.getUnitPrice().getVat() != null) {
                        VAT internalUnitPriceVAT = new VAT();
                        internalUnitPriceVAT.setCode(orderLine.getUnitPrice().getVat().getCode());
                        internalUnitPriceVAT.setDescription(orderLine.getUnitPrice().getVat().getDescription());
                        internalUnitPrice.setVAT(internalUnitPriceVAT);
                    }

                    internalUnitPrice.setVATPercentage(orderLine.getUnitPrice().getVatPercentage());
                    iSol.setUnitPrice(internalUnitPrice);
                }


                // Set net price
                if (orderLine.getNetPrice() != null) {
                    NetPrice internalNetPrice = new NetPrice();

                    if (orderLine.getNetPrice().getCurrency() != null) {
                        Currency internalNetCurrency = new Currency();
                        internalNetCurrency.setCode(orderLine.getNetPrice().getCurrency().getCode());
                        internalNetPrice.setCurrency(internalNetCurrency);
                    }

                    internalNetPrice.setValue(orderLine.getNetPrice().getValue());

                    if (orderLine.getNetPrice().getVat() != null) {
                        VAT internalNetPriceVAT = new VAT();
                        internalNetPriceVAT.setCode(orderLine.getNetPrice().getVat().getCode());
                        internalNetPriceVAT.setDescription(orderLine.getNetPrice().getVat().getDescription());
                        internalNetPrice.setVAT(internalNetPriceVAT);
                    }

                    internalNetPrice.setVATPercentage(orderLine.getNetPrice().getVatPercentage());
                    iSol.setNetPrice(internalNetPrice);
                }


                // Set order line foreign amount
                if (orderLine.getForeignAmount() != null) {
                    ForeignAmount internalOLForeignAmount = new ForeignAmount();

                    if (orderLine.getForeignAmount().getCurrency()  != null) {
                        Currency internalOLCurrency = new Currency();
                        internalOLCurrency.setCode(orderLine.getForeignAmount().getCurrency().getCode());
                        internalOLCurrency.setValue(orderLine.getForeignAmount().getCurrency().getValue());
                        internalOLForeignAmount.setCurrency(internalOLCurrency);
                    }

                    internalOLForeignAmount.setValue(orderLine.getForeignAmount().getValue());
                    internalOLForeignAmount.setRate(orderLine.getForeignAmount().getRate());
                    internalOLForeignAmount.setVATBaseAmount(orderLine.getForeignAmount().getVatBaseAmount());
                    internalOLForeignAmount.setVATAmount(orderLine.getForeignAmount().getVatAmount());

                    iSol.setForeignAmount(internalOLForeignAmount);
                }

                internalOrderlinesList.add(iSol);
            }

            internalOrder.getSalesOrderLines().addAll(internalOrderlinesList);
            result.getSalesOrders().getSalesOrders().add(internalOrder);
        }

        // Set topics
        Topics internalTopics = new Topics();
        Topic internalTopic = new Topic();
        internalTopic.setCount(String.valueOf(orders.size()));
        internalTopic.setCode("SalesOrders");
        internalTopics.setTopic(internalTopic);
        result.setTopics(internalTopics);

        // set messages
//        result.setMessages(in.getMessages());

        return result;
    }
}

