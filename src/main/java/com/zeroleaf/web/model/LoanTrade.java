package com.zeroleaf.web.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zeroleaf on 2015/5/3.
 *
 * 借款交易表.
 */
@Entity
@Table(name = "loan_trade")
public class LoanTrade {

    @Id @GeneratedValue
    private Long id;

    // 购买份数.
    @Column(name = "quantity")
    private Integer quantity;

    // 拍下日期.
    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;

    @ManyToOne
    private LoanApplicationForm form;

    @OneToOne
    private User investor;

    protected LoanTrade() {}

    protected LoanTrade(User investor, Integer quantity, Date date) {
        this.investor = investor;
        this.quantity = quantity;
        this.date     = date;
    }

    public static LoanTrade newTrade(User investor, Integer quantity) {
        return new LoanTrade(investor, quantity, new Date(System.currentTimeMillis()));
    }

    //----------------------------------------------------------------------
    // Getter 与 Setter.
    //----------------------------------------------------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LoanApplicationForm getForm() {
        return form;
    }

    public void setForm(LoanApplicationForm form) {
        this.form = form;
    }

    public User getInvestor() {
        return investor;
    }

    public void setInvestor(User investor) {
        this.investor = investor;
    }

    //----------------------------------------------------------------------
    // 业务逻辑方法.
    //----------------------------------------------------------------------

    /**
     * 获取该借款交易金额.
     *
     * @return 该借款交易金额.
     */
    public Double getBalance() {
        if (form == null) {
            throw new IllegalStateException("要获取交易金额必需先关联借款申请.");
        }

        return quantity * form.getPrice();
    }

    /**
     * 回款时的金额.
     *
     * @return
     */
    public Double getProfit() {
        if (form == null) {
            throw new IllegalStateException("要获取交易金额必需先关联借款申请.");
        }

        return quantity * (form.getPrice() + form.getSingleProfit());
    }
}
