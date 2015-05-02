package com.zeroleaf.web.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zeroleaf on 2015/5/1.
 */
@Entity
@Table(name = "user")
public class User implements Serializable {

    // 借款人
    public static final int DEBTOR   = 1;

    // 投资人
    public static final int INVESTOR = 2;

    private static final long serialVersionUID = 3007749885262460456L;

    @Id @GeneratedValue
    private Long id;

    // 用户名
    @Column(name = "nick",      nullable = false, unique = true)
    private String nick;

    @Column(name = "password",  nullable = false)
    private String password;

    @Column(name = "type",      nullable = false)
    private Integer type;

    // 身份证号
    @Column(name = "id_number")
    private String idNumber;

    // 用户姓名
    @Column(name = "name")
    private String name;

    @Column(name = "phone_number", length = 11)
    private String phoneNumber;

    // 银行卡号
    @Column(name = "card_number")
    private Long cardNumber;

    @Column(name = "email")
    private String email;

//    TODO 貌似仍旧会查询该字段
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Asset asset;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<AmountFlow> amountFlows = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<LoanApplicationForm> applicationForms = new ArrayList<>();

    public User() {
    }

    public User(String nick, String password, Integer type) {
        this.nick = nick;
        this.password = password;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public List<AmountFlow> getAmountFlows() {
        return amountFlows;
    }

    public void setAmountFlows(List<AmountFlow> amountFlows) {
        this.amountFlows = amountFlows;
    }

    public List<LoanApplicationForm> getApplicationForms() {
        return applicationForms;
    }

    public void addLoanApplicationForm(LoanApplicationForm laf) {
        if (laf != null) {
            applicationForms.add(laf);
            laf.setUser(this);
        }
    }

    public void setApplicationForms(List<LoanApplicationForm> applicationForms) {
        this.applicationForms = applicationForms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        return nick.equals(user.nick);
    }

    @Override
    public int hashCode() {
        return nick.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nick='" + nick + '\'' +
                ", type=" + type +
                ", idNumber='" + idNumber + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", cardNumber=" + cardNumber +
                ", email='" + email + '\'' +
                '}';
    }
}
