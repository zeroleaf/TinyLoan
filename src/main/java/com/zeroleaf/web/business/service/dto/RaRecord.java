package com.zeroleaf.web.business.service.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zeroleaf on 2015/5/11.
 */
public class RaRecord implements Serializable {

    private static final long serialVersionUID = 2273562513658567055L;

    private String code;
    private String username;
    private String type;
    private Double amount;
    private Date   date;

    public RaRecord() {
    }

    public RaRecord(String code, String username, String type, Double amount, Date date) {
        this.code = code;
        this.username = username;
        this.type = type;
        this.amount = amount;
        this.date = date;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
