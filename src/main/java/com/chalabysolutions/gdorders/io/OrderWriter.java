package com.chalabysolutions.gdorders.io;

import com.chalabysolutions.gdorders.model.orders.Order;

import java.io.File;
import java.util.List;

public interface OrderWriter {
    void write(List<Order> orders, File file) throws Exception;
}
