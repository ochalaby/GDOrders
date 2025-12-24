package com.chalabysolutions.gdorders.ui.views.orders.components;

import com.chalabysolutions.gdorders.model.generic.Country;
import com.chalabysolutions.gdorders.model.orders.DeliveryAddress;
import com.chalabysolutions.gdorders.model.orders.Order;
import com.chalabysolutions.gdorders.ui.views.orders.logic.OrdersPresenter;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;

public class CustomerOrdersGrid extends Grid<Order> {

    public final Checkbox selectAll = new Checkbox();

    public CustomerOrdersGrid(OrdersPresenter presenter) {
        super(Order.class, false);
        setHeight("300px");
        addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        // select-all initialization
        selectAll.setValue(presenter.areAllCustomerOrdersSelected());
        selectAll.addValueChangeListener(e -> {
            if (e.isFromClient()) presenter.onCustomerSelectAllChanged(e.getValue());
        });

        addColumn(new ComponentRenderer<>(order -> {
            Checkbox cb = new Checkbox(presenter.isCustomerSelected(order));
            cb.addValueChangeListener(ev -> {
                if (ev.isFromClient()) presenter.onCustomerOrderSelected(order, ev.getValue());

                // Header-checkbox bijwerken
                selectAll.setValue(presenter.areAllCustomerOrdersSelected());
            });
            return cb;
        })).setHeader(selectAll).setAutoWidth(true).setFlexGrow(0);

        addColumn(Order::getYourRef).setHeader("Ref").setWidth("120px").setFlexGrow(0).setResizable(true);
        addColumn(Order::getSalesordernumber).setHeader("Order Nr").setWidth("100px").setFlexGrow(0).setResizable(true);
        addColumn(Order::getOrderDate).setHeader("order date").setWidth("120px").setFlexGrow(0).setResizable(true);
        addColumn(Order::getDeliveryDate).setHeader("delivery date").setWidth("120px").setFlexGrow(0).setResizable(true);
        addColumn(o -> "(" + o.getOrderedBy().getCode() + ") " + o.getOrderedBy().getName()).setHeader("ordered by").setWidth("220px").setFlexGrow(0).setResizable(true);
        addColumn(o -> o.getDeliveryAddress().getId()).setHeader("Delivery address ID").setWidth("210px").setFlexGrow(0).setResizable(true);
        addColumn(this::getAddressInfo).setHeader("Delivery address").setWidth("280px").setFlexGrow(0).setResizable(true);
        addColumn(o -> "(" + o.getWarehouse().getCode() + ") " + o.getWarehouse().getDescription()).setHeader("Warehouse").setWidth("180px").setFlexGrow(0).setResizable(true);
        addColumn(o -> o.getShippingMethod().getCode()).setHeader("shipping").setWidth("100px").setFlexGrow(0).setResizable(true);

        addItemClickListener(e -> presenter.onCustomerOrderClicked(e.getItem()));
    }

    private String getAddressInfo(Order order){
        String addressInfo = "";

        DeliveryAddress deliveryAddress = order.getDeliveryAddress();
        if (deliveryAddress != null) {
            addressInfo = deliveryAddress.getFormattedAddress();
        }

        return addressInfo;
    }
}
