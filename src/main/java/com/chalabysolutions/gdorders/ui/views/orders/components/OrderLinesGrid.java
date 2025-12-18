package com.chalabysolutions.gdorders.ui.views.orders.components;

import com.chalabysolutions.gdorders.model.orders.OrderLine;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;

public class OrderLinesGrid extends Grid<OrderLine> {

    public OrderLinesGrid() {
        super(OrderLine.class, false);
        setHeight("200px");
        addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        addColumn(OrderLine::getLine).setHeader("Line").setWidth("60px").setFlexGrow(0).setResizable(true);
        addColumn(OrderLine::getDeliveryDate).setHeader("Delivery date").setWidth("120px").setFlexGrow(0).setResizable(true);
        addColumn(ol -> ol.getItem().getCode()).setHeader("Item code").setWidth("120px").setFlexGrow(0).setResizable(true);
        addColumn(OrderLine::getDescription).setHeader("Item").setWidth("380px").setFlexGrow(0).setResizable(true);
        addColumn(OrderLine::getQuantity).setHeader("Qty").setWidth("60px").setFlexGrow(0).setResizable(true);
    }
}
