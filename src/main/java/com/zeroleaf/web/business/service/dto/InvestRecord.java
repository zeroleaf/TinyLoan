package com.zeroleaf.web.business.service.dto;

import com.zeroleaf.web.model.LoanApplicationForm;
import com.zeroleaf.web.model.LoanTrade;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zeroleaf on 2015/5/10.
 */
public class InvestRecord implements Serializable {

    private static final long serialVersionUID = 3501017904398131996L;

    private String  title;
    private Date    date;
    private Double  balance;
    private Boolean isDone;
    private Boolean isRefund;

    public InvestRecord() {
    }

    public InvestRecord(String title, Date date, Double balance, Boolean isDone, Boolean isRefund) {
        this.title = title;
        this.date = date;
        this.balance = balance;
        this.isDone = isDone;
        this.isRefund = isRefund;
    }

    public static InvestRecord from(LoanTrade trade, LoanApplicationForm laf) {
        return new InvestRecord(laf.getTitle(), trade.getDate(),
                trade.getBalance(), laf.isDone(), laf.getIsRefunded());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

    public Boolean getIsRefund() {
        return isRefund;
    }

    public void setIsRefund(Boolean isRefund) {
        this.isRefund = isRefund;
    }
}
