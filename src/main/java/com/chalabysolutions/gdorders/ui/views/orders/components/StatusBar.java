package com.chalabysolutions.gdorders.ui.views.orders.components;

import com.chalabysolutions.gdorders.ui.views.StatusLevel;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class StatusBar extends VerticalLayout {

    private final NativeLabel label = new NativeLabel("Ready.");

    public StatusBar() {
        add(label);
    }

    public void show(StatusLevel level, String msg) {
        label.getStyle().set("color", level.getColor());
        label.setText(msg);
    }

    public void clear(){
        label.setText("");
    }
}
