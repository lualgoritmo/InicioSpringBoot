package com.lucianobass.cardactivity.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tb_invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInvoice;
    private Float total;
    private String status;
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    public Invoice() {
    }

    public Invoice(Float total, String status, List<Transaction> transactions) {
        this.total = total;
        this.status = status;
        this.transactions = transactions;
    }

    public Long getIdInvoice() {
        return idInvoice;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Transaction> getTransaction() {
        return transactions;
    }

    public void setTransaction(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
