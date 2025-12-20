package com.chalabysolutions.gdorders.ui.views.orders.logic;

import com.chalabysolutions.gdorders.io.XmlOrderReader;
import com.chalabysolutions.gdorders.io.XmlOrderWriter;
import com.chalabysolutions.gdorders.model.accounts.Account;
import com.chalabysolutions.gdorders.model.orders.Order;
import com.chalabysolutions.gdorders.service.*;
import com.chalabysolutions.gdorders.ui.views.StatusBar;
import com.chalabysolutions.gdorders.ui.views.StatusLevel;
import com.chalabysolutions.gdorders.ui.views.orders.components.CustomerOrdersGrid;
import com.chalabysolutions.gdorders.ui.views.orders.components.InternalOrdersGrid;
import com.chalabysolutions.gdorders.ui.views.orders.components.OrderLinesGrid;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;

import java.io.*;
import java.util.List;
import java.util.Set;

public class OrdersPresenter {

    private final OrderDataService orderService;
    private final AccountDataService accountService;
    private final SettingsService settingsService;
    private final OrderConversionService converter;

    private CustomerOrdersGrid customerOrdersGrid;
    private InternalOrdersGrid internalOrdersGrid;
    private OrderLinesGrid customerOrderLinesGrid;
    private OrderLinesGrid internalOrderLinesGrid;
    private StatusBar status;

    private final XmlOrderReader orderReader = new XmlOrderReader();
    private final XmlOrderWriter orderWriter = new XmlOrderWriter();

    public OrdersPresenter(OrderDataService orderService, AccountDataService accountService,
                           MappingDataService mappingService, SettingsService settingsService) {
        this.orderService = orderService;
        this.accountService = accountService;
        this.settingsService = settingsService;
        this.converter = new OrderConversionService(accountService, mappingService, settingsService);
    }

    /* ----------- Used by view components ----------- */

    public List<Account> getAccounts() {
        return accountService.getAccounts();
    }

    public Account getSelectedAccount() {
        return accountService.getSelectedAccount();
    }

    public void onAccountSelected(Account a) {
        accountService.setSelectedAccount(a);
    }

    public boolean areAllCustomerOrdersSelected() {
        return areAllItemsSelected(orderService.getCustomerOrders(), orderService.getSelectedCustomerOrders());
    }

    public boolean areAllInternalOrdersSelected() {
        return areAllItemsSelected(orderService.getInternalOrders(), orderService.getSelectedInternalOrders());
    }

    private boolean areAllItemsSelected(List<Order> grid, Set<Order> selected) {
        return !grid.isEmpty() && grid.size() == selected.size();
    }

    public void onCustomerSelectAllChanged(boolean value) {
        orderService.selectAllCustomerOrders(value);
        customerOrdersGrid.getDataProvider().refreshAll();
    }

    public void onInternalSelectAllChanged(boolean value) {
        orderService.selectAllInternalOrders(value);
        internalOrdersGrid.getDataProvider().refreshAll();
    }

    public boolean isCustomerSelected(Order o) {
        return orderService.getSelectedCustomerOrders().contains(o);
    }

    public void onCustomerOrderSelected(Order o, boolean selected) {
        orderService.setCustomerOrderSelected(o, selected);
        customerOrdersGrid.getDataProvider().refreshAll();
    }

    public void onCustomerOrderClicked(Order o) {
        customerOrderLinesGrid.setItems(o.getOrderLines());
    }

    public boolean isInternalSelected(Order o) {
        return orderService.getSelectedInternalOrders().contains(o);
    }

    public void onInternalOrderSelected(Order o, boolean selected) {
        orderService.setInternalOrderSelected(o, selected);
        internalOrdersGrid.getDataProvider().refreshAll();
    }

    public void onInternalOrderClicked(Order o) {
        internalOrderLinesGrid.setItems(o.getOrderLines());
    }

    public void onCustomerOrdersUploaded(MemoryBuffer buffer, Upload uploadOrders) {
        try (InputStream in = buffer.getInputStream()) {
            uploadOrders.clearFileList();

            // Orders inlezen en alles automatisch aanvinken
            List<Order> loaded = orderReader.read(in);
            orderService.replaceCustomerOrders(loaded);

            // Order grid verversen
            refreshAll();

            // Header checkbox updaten
            customerOrdersGrid.selectAll.setValue(true);

            status.show(StatusLevel.INFO, "Klant orders ingelezen.");
        } catch (Exception e) {
            status.show(StatusLevel.ERROR, "Fout bij inlezen klant orders: " + e.getMessage());
        }
    }

    public void convertOrders() {
        if (accountService.getSelectedAccount() == null) {
            status.show(StatusLevel.ERROR, "Kies eerst een account.");
            return;
        }

        if (orderService.getSelectedCustomerOrders().isEmpty()) {
            status.show(StatusLevel.ERROR, "Geen orders geselecteerd.");
            return;
        }

        List<Order> internal =
                orderService.getCustomerOrders().stream()
                        .filter(orderService.getSelectedCustomerOrders()::contains)
                        .map(converter::convert)
                        .toList();

        orderService.replaceInternalOrders(internal);

        // Order grid verversen
        refreshAll();

        // Header checkbox updaten
        internalOrdersGrid.selectAll.setValue(true);

        status.show(StatusLevel.INFO, "Conversie voltooid.");
    }

    public void exportInternalOrders() {
        if (orderService.getSelectedInternalOrders().isEmpty()) {
            status.show(StatusLevel.ERROR, "Geen interne orders geselecteerd.");
            return;
        }

        List<Order> orders =
                orderService.getInternalOrders().stream()
                        .filter(orderService.getSelectedInternalOrders()::contains)
                        .toList();

        try {
            exportServerSide(orders);
        } catch (Exception e) {
            status.show(StatusLevel.ERROR, "Export fout: " + e.getMessage());
        }
    }

    private void exportServerSide(List<Order> orders) {
        File dir = settingsService.ensureExportDir();
        if (dir == null) {
            status.show(StatusLevel.ERROR, "Geen default export directory ingesteld.");
            return;
        }

        String timestamp = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        File out = new File(dir, "InternalOrders_" + timestamp + ".xml");

        try (FileOutputStream fos = new FileOutputStream(out)) {
            orderWriter.write(orders, fos);
            status.show(StatusLevel.INFO, "Export opgeslagen in: " + out.getAbsolutePath());
        } catch (Exception e) {
            status.show(StatusLevel.ERROR, "Export mislukt: " + e.getMessage());
        }
    }

    /* ---- Links to UI components ---- */

    public void setCustomerGrids(CustomerOrdersGrid customerOrdersGrid, OrderLinesGrid customerOrderLinesGrid) {
        this.customerOrdersGrid = customerOrdersGrid;
        this.customerOrderLinesGrid = customerOrderLinesGrid;
        customerOrdersGrid.setItems(orderService.getCustomerOrders());
    }

    public void setInternalGrids(InternalOrdersGrid internalOrdersGrid, OrderLinesGrid internalOrderLinesGrid) {
        this.internalOrdersGrid = internalOrdersGrid;
        this.internalOrderLinesGrid = internalOrderLinesGrid;
        internalOrdersGrid.setItems(orderService.getInternalOrders());
    }

    public void setStatusBar(StatusBar status) {
        this.status = status;
    }

    public void refreshAll() {
        customerOrdersGrid.getDataProvider().refreshAll();
        internalOrdersGrid.getDataProvider().refreshAll();
    }

    public void resetAll() {
        orderService.clear();
        refreshAll();
        customerOrdersGrid.selectAll.setValue(false);
        internalOrdersGrid.selectAll.setValue(false);
    }
}
