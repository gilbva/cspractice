package com.github.gilbva.systems.dataservers.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentTransaction {
    private String id;

    private String account;

    private BigDecimal amount;

    private LocalDateTime datetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }
}
