package com.zeroleaf.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by zeroleaf on 2015/5/1.
 *
 * 资金流动表, 包括 充值 与 提现.
 */
@Entity
@Table(name = "amount_flow")
@JsonIgnoreProperties(value = {"user"}, ignoreUnknown = true)
public class AmountFlow implements Serializable {

    // 交易类型.
    public static final int RECHARGE = 1;   // 充值
    public static final int ADVANCE  = 2;   // 提现
    public static final int INVEST   = 3;   // 投资
    public static final int DEBT     = 4;   // 借款
    public static final int REFUND   = 5;   // 借款者 回款
    public static final int PROFIT   = 6;   // 投资者 回款到账

    // 充值方式.
    public static final String ZFB   = "支付宝";
    public static final String CFT   = "财付通";
    public static final String WY    = "网银";

    private static final long serialVersionUID = 5946341026773608351L;

    @Id @GeneratedValue
    private Long id;

    // 交易流水号
    @Column(name = "code", nullable = false)
    private String code;

    // 交易类型, 充值还是提现.
    @Column(name = "type", nullable = false)
    private Integer type;

    // 交易金额.
    @Column(name = "amount", nullable = false, precision = 20, scale = 2)
    private Double amount;

    // 交易内容.
    @Column(name = "trade_content")
    private String tradeContent;

    // 交易时间.
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time")
    private Date time;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public AmountFlow() {
    }

    public AmountFlow(Integer type, Double amount, String tradeContent) {
        this.type           = type;
        this.amount         = amount;
        this.tradeContent   = tradeContent;

        this.time           = new Date(System.currentTimeMillis());
        this.code           = LoanApplicationForm.generateCode();
    }

    public static AmountFlow newInvest(double amount, String title) {
        return new AmountFlow(INVEST, amount, "投资项目 " + title);
    }

    public static AmountFlow newDebt(double amount, String title) {
        return new AmountFlow(DEBT, amount, "借款申请 " + title + " 筹集金额");
    }

    public static AmountFlow newRecharge(double amount, String type) {
        String tradeContent;
        switch (type) {
            case "CFT":
                tradeContent = CFT; break;
            case "WY":
                tradeContent = WY;  break;
            default:
                tradeContent = ZFB;
        }
        tradeContent += " 充值";
        return new AmountFlow(RECHARGE, amount, tradeContent);
    }

    public static AmountFlow newAdvance(double amount, String credit) {
        String tradeContent = String.format("提现到银行卡 %s", credit);
        return new AmountFlow(ADVANCE, amount, tradeContent);
    }

    public static AmountFlow newRefund(double amount, String title) {
        String tradeContent = "回款到项目 - " + title;
        return new AmountFlow(REFUND, amount, tradeContent);
    }

    public static AmountFlow newProfit(double amount, String title) {
        String tradeContent = title + " 项目回款";
        return new AmountFlow(PROFIT, amount, tradeContent);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTradeContent() {
        return tradeContent;
    }

    public void setTradeContent(String tradeContent) {
        this.tradeContent = tradeContent;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AmountFlow)) return false;

        AmountFlow that = (AmountFlow) o;

        return code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public String toString() {
        return "AmountFlow{" +
                "time=" + time +
                ", id=" + id +
                ", code='" + code + '\'' +
                ", type=" + type +
                ", amount=" + amount +
                ", tradeContent='" + tradeContent + '\'' +
                '}';
    }
}
