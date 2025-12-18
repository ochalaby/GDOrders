package com.chalabysolutions.gdorders.io;

import com.chalabysolutions.gdorders.model.orders.Order;

import java.io.File;
import java.util.List;

public interface OrderReader {
    List<Order> read(File file) throws Exception;
}
