package com.mycompany.springbasic1123.jdbc.invoice.entity;

import java.util.Date;

public class Invoice {
    private Integer id;
    private Date invDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getInvDate() {
        return invDate;
    }

    public void setInvDate(Date invDate) {
        this.invDate = invDate;
    }

    @Override
    public String toString() {
        return "Invoice{" + "id=" + id + ", invDate=" + invDate + '}';
    }

    
}
