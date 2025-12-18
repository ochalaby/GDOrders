package com.chalabysolutions.gdorders.io;

import com.chalabysolutions.gdorders.model.orders.Order;
import com.chalabysolutions.gdorders.model.customerorders.EExact;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class XmlOrderReader implements OrderReader {

    private final XmlOrderReaderConverter xmlOrderReaderConverter = new XmlOrderReaderConverter();

    @Override
    public List<Order> read(File file) throws Exception {
        return read (new FileInputStream(file));
    }

    public List<Order> read(InputStream input) throws Exception {
        JAXBContext ctx = JAXBContext.newInstance(EExact.class);
        Unmarshaller um = ctx.createUnmarshaller();
        EExact loadedOrders = (EExact) um.unmarshal(input);

        // Zet om naar generieke Order-objecten
        return xmlOrderReaderConverter.convert(loadedOrders);
    }
}
