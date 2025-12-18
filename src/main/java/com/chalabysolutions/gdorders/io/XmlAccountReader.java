package com.chalabysolutions.gdorders.io;

import com.chalabysolutions.gdorders.model.accounts.Account;
import com.chalabysolutions.gdorders.model.internalaccounts.EExact;
import com.chalabysolutions.gdorders.model.orders.Order;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class XmlAccountReader {

    private final XmlAccountReaderConverter xmlAccountReaderConverter = new XmlAccountReaderConverter();

    public List<Account> read(File file) throws Exception {
        return read (new FileInputStream(file));
    }

    // Nieuwe methode: lees vanuit InputStream
    public List<Account> read(InputStream input) throws Exception {
        JAXBContext ctx = JAXBContext.newInstance(EExact.class);
        Unmarshaller um = ctx.createUnmarshaller();
        EExact loadedOrders = (EExact) um.unmarshal(input);

        // Zet om naar generieke Order-objecten
        return xmlAccountReaderConverter.convert(loadedOrders);
    }
}
