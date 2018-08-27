package com.ciandt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name="users")
public class User implements Serializable {
    @Id
    @Column(name="user_id")
    private Integer userId;

    @Column(name="balance_brl")
    private BigDecimal balanceBRL = new BigDecimal(0);

    public User(Integer userId, BigDecimal balanceBRL) {
        this.userId = userId;
        this.balanceBRL = balanceBRL;
    }

    public User() {}

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public BigDecimal getBalanceBRL() {
        return balanceBRL;
    }

    public void setBalanceBRL(BigDecimal balanceBRL) {
        this.balanceBRL = balanceBRL;
    }


}