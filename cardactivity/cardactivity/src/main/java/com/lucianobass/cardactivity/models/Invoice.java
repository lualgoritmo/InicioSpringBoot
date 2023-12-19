package com.lucianobass.cardactivity.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
@Table(name = "tb_invoice")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idInvoice;
    private Float total;
    private String status;
    private LocalDate dueDate;
    private LocalDate closingDate;
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;;

    public Invoice() {
    }

    public Invoice(Float total, String status, List<Transaction> transactions,
                   LocalDate DueDate, LocalDate ClosingDate) {
        this.total = total;
        this.status = status;
        this.transactions = transactions;
        if (transactions != null) {
            transactions.forEach(transaction -> transaction.setInvoice(this));
        }
        this.dueDate = DueDate;
        this.closingDate = ClosingDate;
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

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
    }


    @PrePersist
    public void prePersist() {
        if (this.dueDate == null) {
            this.dueDate = LocalDate.now().plusDays(30);
        }

        if (this.closingDate == null) {
            this.closingDate = this.dueDate.minus(10, ChronoUnit.DAYS);
        }

        if (this.status == null) {
            this.status = "IN_PROGRESS";
        }

        if(this.total == null) {
            this.total = 0f;
        }
    }
}
