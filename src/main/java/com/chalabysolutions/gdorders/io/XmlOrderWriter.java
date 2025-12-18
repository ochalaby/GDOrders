package com.chalabysolutions.gdorders.io;

import com.chalabysolutions.gdorders.model.orders.Order;
import com.chalabysolutions.gdorders.model.internalorders.EExact;
import com.chalabysolutions.gdorders.service.XmlWriterService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

public class XmlOrderWriter implements OrderWriter {

    private final XmlOrderWriterConverter xmlOrderWriterConverter = new XmlOrderWriterConverter();

    @Override
    public void write(List<Order> orders, File file) throws Exception {
        write(orders, new FileOutputStream(file));
    }

    // Nieuwe methode: schrijf naar OutputStream
    public void write(List<Order> orders, OutputStream fos) throws Exception {
        // Converteer orders naar internal EExact
        EExact converted = xmlOrderWriterConverter.convert(orders);

        XmlWriterService writer = new XmlWriterService(EExact.class);
        writer.write(converted, fos);
    }
}
