package com.zeroleaf.web.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by zeroleaf on 2015/5/1.
 *
 * 借款申请表.
 */
@Entity
@Table(name = "loan_application_form")
public class LoanApplicationForm implements Serializable {

    public static final int UNAUDITED = 1;  // 未审核
    public static final int PASS      = 2;  // 通过
    public static final int FAIL      = 4;  // 未通过

    @Id @GeneratedValue
    private Long id;

    // 申请单号.
    @Column(name = "code", nullable = false)
    private String code;

    // TODO 检验该值 10 <= x <= 30
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    // TODO 当前该值固定为 1000.00
    // 每份的金额.
    @Column(name = "price", nullable = false, precision = 20, scale = 2)
    private Double price;

    // 资产抵押说明.
    @Column(name = "pledge")
    private String pledge;

    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    private Date date;

    // 申请状态.
    @Column(name = "status", nullable = false)
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // ------- 借款类型 -------

    // 借款期限
    @Column(name = "deadline")
    private Integer deadline;

    // 回款
    @Column(name = "refund", precision = 20, scale = 2)
    private Double refund;

    // 年利率
    @Column(name = "apr", precision = 5, scale = 4)
    private Double apr;


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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPledge() {
        return pledge;
    }

    public void setPledge(String pledge) {
        this.pledge = pledge;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDeadline() {
        return deadline;
    }

    public void setDeadline(Integer deadline) {
        this.deadline = deadline;
    }

    public Double getRefund() {
        return refund;
    }

    public void setRefund(Double refund) {
        this.refund = refund;
    }

    public Double getApr() {
        return apr;
    }

    public void setApr(Double apr) {
        this.apr = apr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoanApplicationForm)) return false;

        LoanApplicationForm that = (LoanApplicationForm) o;

        return code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public String toString() {
        return "LoanApplicationForm{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", pledge='" + pledge + '\'' +
                ", date=" + date +
                ", status=" + status +
                ", deadline=" + deadline +
                ", refund=" + refund +
                ", apr=" + apr +
                '}';
    }
}
