package com.zeroleaf.web.business.service.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zeroleaf on 2015/5/11.
 */
public class AppRecord implements Serializable {

    private String code;
    private String username;
    private Integer quantity;
    private Date date;
    private String status;

    private Double amount;


    public AppRecord() {
    }

    public AppRecord(String code, String username, Integer quantity,
                     Date date, String status) {
        this.code = code;
        this.username = username;
        this.quantity = quantity;
        this.date = date;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
