package com.zeroleaf.web.model;

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
public class AmountFlow implements Serializable {

    public static final int RECHARGE = 1;   // 充值
    public static final int ADVANCE  = 2;   // 提现

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
