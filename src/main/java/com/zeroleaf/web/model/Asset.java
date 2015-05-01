package com.zeroleaf.web.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by zeroleaf on 2015/5/1.
 *
 * 用户资产表.
 */
@Entity
@Table(name = "asset")
public class Asset implements Serializable {

    private static final long serialVersionUID = -3166753059775998161L;

    @Id @GeneratedValue
    private Long id;

    // 资产账户
    @Column(name = "account", nullable = false)
    private Long account;

    // 账户余额
    @Column(name = "balance", precision = 20, scale = 2)
    private Double balance;

    @OneToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
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
        if (!(o instanceof Asset)) return false;

        Asset asset = (Asset) o;

        return account.equals(asset.account);
    }

    @Override
    public int hashCode() {
        return account.hashCode();
    }

    @Override
    public String toString() {
        return "Asset{" +
                "id=" + id +
                ", account=" + account +
                ", balance=" + balance +
                '}';
    }
}
