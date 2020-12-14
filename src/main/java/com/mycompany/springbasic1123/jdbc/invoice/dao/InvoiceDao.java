package com.mycompany.springbasic1123.jdbc.invoice.dao;

import com.mycompany.springbasic1123.jdbc.invoice.entity.Item;
import java.util.List;

public interface InvoiceDao {
    List<Item> queryItem();
}
