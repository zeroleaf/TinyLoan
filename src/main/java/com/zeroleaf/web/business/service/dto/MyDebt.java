package com.zeroleaf.web.business.service.dto;

import java.io.Serializable;

/**
 * Created by zeroleaf on 2015/5/11.
 *
 * 我的借款显示的一条记录.
 */
public class MyDebt implements Serializable {

    private String debtor;

    private String loanTitle;
    private String refundDate;
    private Double refundAmount;

    private String investor;
    private Double profit;
    private Integer iQuantity;

    public MyDebt() {
    }

    public MyDebt(String loanTitle, String refundDate, Double refundAmount,
                  String investor, Double profit, Integer iQuantity) {
        this.loanTitle = loanTitle;
        this.refundDate = refundDate;
        this.refundAmount = refundAmount;
        this.investor = investor;
        this.profit = profit;
        this.iQuantity = iQuantity;
    }

    public String getDebtor() {
        return debtor;
    }

    public void setDebtor(String debtor) {
        this.debtor = debtor;
    }

    public String getLoanTitle() {
        return loanTitle;
    }

    public void setLoanTitle(String loanTitle) {
        this.loanTitle = loanTitle;
    }

    public String getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(String refundDate) {
        this.refundDate = refundDate;
    }

    public Double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getInvestor() {
        return investor;
    }

    public void setInvestor(String investor) {
        this.investor = investor;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Integer getiQuantity() {
        return iQuantity;
    }

    public void setiQuantity(Integer iQuantity) {
        this.iQuantity = iQuantity;
    }
}
