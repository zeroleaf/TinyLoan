package com.zeroleaf.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by zeroleaf on 2015/5/1.
 *
 * 借款申请表.
 */
@Entity
@Table(name = "loan_application_form")
public class LoanApplicationForm implements Serializable {

    // 审核状态
    public static final int UNAUDITED = 1;  // 未审核
    public static final int PASS      = 2;  // 通过
    public static final int DENY      = 4;  // 未通过

    // 借贷状态
    public static final String FREEZE    = "正在融资";   // 冻结, 即未完成.
    public static final String DONE      = "融资完成";   // 完成.

    @Id @GeneratedValue
    private Long id;

    @Column(name = "title")
    private String title;

    // 申请单号.
    @Column(name = "code", nullable = false)
    private String code;

    // TODO 检验该值 10 <= x <= 30
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "raise_quantity")
    private Integer raiseQuantity = 0;

    // TODO 当前该值固定为 1000.00
    // 每份的金额.
    @Column(name = "price", nullable = false, precision = 20, scale = 2)
    private Double price;

    // 资产抵押说明.
    @Column(name = "pledge")
    private String pledge;

    // 申请日期.
    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    private Date date;

    // 借款日期, 全部份数都筹集到的日期.
    @Temporal(TemporalType.DATE)
    @Column(name = "loan_date")
    private Date loanDate;

    // 审核状态.
    @Column(name = "status", nullable = false)
    private Integer status;

    @ManyToOne()
    private User user;

    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL)
    private List<LoanTrade> trades;

    // ------- 借款类型 -------

    // 借款期限
    @Column(name = "deadline")
    private Integer deadline;

    // 回款
    @Column(name = "refund", precision = 20, scale = 2)
    private Double refund;

    @Column(name = "is_refunded")
    private Boolean isRefunded;

    // TODO 由于该值是通过业务规则计算而来, 所以没必要存该值.
    // 年利率
    @Column(name = "apr", precision = 5, scale = 4)
    private Double apr;

    public LoanApplicationForm() {
    }

    protected LoanApplicationForm(String title, Integer quantity, String pledge, Integer deadline) {
        this.title    = title;
        this.quantity = quantity;
        this.pledge   = pledge;
        this.deadline = deadline;

        this.price      = 1000.00;
        this.date       = new Date(System.currentTimeMillis());
        this.status     = UNAUDITED;
        this.apr        = judgeApr(deadline);
        this.code       = generateCode();
        this.isRefunded = false;
    }

    private static Double judgeApr(Integer deadline) {
        switch (deadline) {
            case 3:
                return 0.08;
            case 6:
                return 0.09;
            default:
                return 0.12;
        }
    }

    static String generateCode() {
        Random random = new Random();
        return String.format("%d%06d", System.currentTimeMillis(), random.nextInt(1000000));
    }

    public static LoanApplicationForm newDefaultInstance(String title, Integer quantity, String pledge, Integer deadline) {
        return new LoanApplicationForm(title, quantity, pledge, deadline);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getFormatStatus() {
        switch (status) {
            case UNAUDITED:
                return "未审核";
            case DENY:
                return "未通过";
            default:
                return "通过";
        }
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Boolean getIsRefunded() {
        return isRefunded;
    }

    public void setIsRefunded(Boolean isRefunded) {
        this.isRefunded = isRefunded;
    }

    public Double getApr() {
        return apr;
    }

    public void setApr(Double apr) {
        this.apr = apr;
    }

    public Integer getRaiseQuantity() {
        return raiseQuantity;
    }

    public void setRaiseQuantity(Integer raiseQuantity) {
        this.raiseQuantity = raiseQuantity;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public List<LoanTrade> getTrades() {
        return trades;
    }

    public void setTrades(List<LoanTrade> trades) {
        this.trades = trades;
    }

    //----------------------------------------------------------------------
    // 业务规则方法.
    //----------------------------------------------------------------------

    public String getIsRefundString() {
        return isRefunded ? "已回款" : "未回款";
    }

    /**
     * 单份收益.
     *
     * @return
     */
    public double getSingleProfit() {
        return price * apr * deadline / 12;
    }

    /**
     * 回款金额.
     *
     * @return
     */
    public double getRefundBalance() {
        return (price + getSingleProfit()) * quantity;
    }

    /**
     * 还款日期.
     *
     * @return
     */
    public String getRefundDate() {
        Date refundDate = new Date(date.getTime());
        refundDate.setMonth(refundDate.getMonth() + deadline);
        return DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CHINA).format(refundDate);
    }

    /**
     * 获取该借款总金额.
     *
     * @return 借款总金额.
     */
    public double getBalance() {
        return price * quantity;
    }

    public String getProgressString() {
        return String.format("%d/%d", raiseQuantity, quantity);
    }

    public void addLoanTrade(LoanTrade trade) {
        if (trade != null) {
            trades.add(trade);
            trade.setForm(this);

            raiseQuantity += trade.getQuantity();
            if (raiseQuantity >= quantity) {
                loanDate = new Date(System.currentTimeMillis());
            }
        }
    }

    /**
     * 获取字符串表示的借款状态.
     *
     * @return 借款状态.
     */
    public String getLoanStatus() {
        return raiseQuantity < quantity ? FREEZE : DONE;
    }

    /**
     * 借款是否完成.
     *
     * @return 完成 true; 否则 false.
     */
    public boolean isDone() {
        return raiseQuantity >= quantity;
    }

    public String calRefund() {
        Double refund = price * judgeApr(deadline) * deadline / 12;
        return String.format("%.2f", refund);
    }

    /**
     * 获取待筹集份数.
     *
     * @return 待筹集份数.
     */
    public int getToRaiseQuantity() {
        return quantity - raiseQuantity;
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
                ", title='" + title + '\'' +
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
