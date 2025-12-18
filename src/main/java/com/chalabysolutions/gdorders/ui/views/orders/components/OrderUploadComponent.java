package com.chalabysolutions.gdorders.ui.views.orders.components;

import com.chalabysolutions.gdorders.ui.views.orders.logic.OrdersPresenter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;

public class OrderUploadComponent extends VerticalLayout {

    public OrderUploadComponent(OrdersPresenter presenter) {
        setPadding(false);
        setSpacing(false);

        MemoryBuffer buffer = new MemoryBuffer();
        Upload uploadOrders = new Upload(buffer);
        uploadOrders.setAcceptedFileTypes(".xml");
        uploadOrders.setUploadButton(new Button("Upload klantorders XML"));
        uploadOrders.setDropAllowed(false);
        uploadOrders.addSucceededListener(event -> presenter.onCustomerOrdersUploaded(buffer, uploadOrders));

        add(uploadOrders);
    }
}
