package com.mycompany.springbasic1123.invoice.entity;

import java.util.Date;

public class Invoice {
    private Integer id;
    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Invoice{" + "id=" + id + ", date=" + date + '}';
    }
   
}
