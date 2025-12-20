package com.chalabysolutions.gdorders.service;

import com.chalabysolutions.gdorders.model.orders.Order;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Service
public class OrderDataService {

    private final List<Order> customerOrders = new ArrayList<>();
    private final List<Order> internalOrders = new ArrayList<>();

    private final Set<Order> selectedCustomerOrders = new HashSet<>();
    private final Set<Order> selectedInternalOrders = new HashSet<>();

    // Vervang de volledige klantorders en selecteer ze standaard
    public void replaceCustomerOrders(List<Order> orders) {
        customerOrders.clear();
        customerOrders.addAll(orders);
        selectedCustomerOrders.clear();
        selectedCustomerOrders.addAll(orders);
    }

    // Select / deselect all
    public void selectAllCustomerOrders(boolean select) {
        selectedCustomerOrders.clear();
        if (select) selectedCustomerOrders.addAll(customerOrders);
    }

    // Individuele selectie
    public void setCustomerOrderSelected(Order order, boolean selected) {
        if (selected) selectedCustomerOrders.add(order);
        else selectedCustomerOrders.remove(order);
    }

    // Vervang de interne orders en selecteer alles standaard
    public void replaceInternalOrders(List<Order> orders) {
        internalOrders.clear();
        internalOrders.addAll(orders);
        selectedInternalOrders.clear();
        selectedInternalOrders.addAll(orders);
    }

    // Select / deselect all
    public void selectAllInternalOrders(boolean select) {
        selectedInternalOrders.clear();
        if (select) selectedInternalOrders.addAll(internalOrders);
    }

    // Individuele selectie
    public void setInternalOrderSelected(Order order, boolean selected) {
        if (selected) selectedInternalOrders.add(order);
        else selectedInternalOrders.remove(order);
    }

    public void clear() {
        customerOrders.clear();
        internalOrders.clear();
        selectedCustomerOrders.clear();
        selectedInternalOrders.clear();
    }
}

