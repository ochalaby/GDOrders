package com.chalabysolutions.gdorders.ui.views.orders;

import com.chalabysolutions.gdorders.service.AccountDataService;
import com.chalabysolutions.gdorders.service.MappingDataService;
import com.chalabysolutions.gdorders.service.OrderDataService;
import com.chalabysolutions.gdorders.service.SettingsService;
import com.chalabysolutions.gdorders.ui.layout.MainLayout;
import com.chalabysolutions.gdorders.ui.views.StatusBar;
import com.chalabysolutions.gdorders.ui.views.orders.components.*;
import com.chalabysolutions.gdorders.ui.views.orders.logic.OrdersPresenter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Orders")
@PreserveOnRefresh
public class OrdersView extends VerticalLayout {

    public OrdersView(OrderDataService data,
                      AccountDataService account,
                      MappingDataService mapping,
                      SettingsService settings) {

        setSizeFull();

        OrdersPresenter p = new OrdersPresenter(data, account, mapping, settings);

        // Header row (account selector and order upload)
        AccountSelector accountSelector = new AccountSelector(p);
        OrderUploadComponent uploadOrders = new OrderUploadComponent(p);
        Button btnReset = new Button("Reset", e -> p.resetAll());
        HorizontalLayout headerLayout = new HorizontalLayout(accountSelector, uploadOrders, btnReset);
        headerLayout.setAlignItems(Alignment.END);

        // Split layout with customer orders and internal orders
        VerticalLayout leftLayout = new VerticalLayout();
        leftLayout.setSizeFull();
        CustomerOrdersGrid customerGrid = new CustomerOrdersGrid(p);
        OrderLinesGrid customerLineGrid = new OrderLinesGrid();
        p.setCustomerGrids(customerGrid, customerLineGrid);
        Button btnConvert = new Button("Converteer naar interne orders", e -> p.convertOrders());
        leftLayout.add(customerGrid, customerLineGrid, btnConvert);
        leftLayout.expand(customerGrid, customerLineGrid);

        VerticalLayout rightLayout = new VerticalLayout();
        rightLayout.setSizeFull();
        InternalOrdersGrid internalGrid = new InternalOrdersGrid(p);
        OrderLinesGrid internalLineGrid = new OrderLinesGrid();
        p.setInternalGrids(internalGrid, internalLineGrid);
        Button btnExport = new Button("Exporteer geselecteerde interne orders", e -> p.exportInternalOrders());
        rightLayout.add(internalGrid, internalLineGrid, btnExport);
        rightLayout.expand(internalGrid, internalLineGrid);

        SplitLayout split = new SplitLayout(leftLayout, rightLayout);
        split.setSizeFull();
        split.setSplitterPosition(60);

        // Status bar (to inform users about action results)
        StatusBar status = new StatusBar();
        p.setStatusBar(status);

        // Fill the page with components
        add(headerLayout, split, status);
    }
}
