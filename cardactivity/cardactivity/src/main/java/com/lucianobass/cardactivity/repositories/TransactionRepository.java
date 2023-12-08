package com.lucianobass.cardactivity.repositories;

import com.lucianobass.cardactivity.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCardIdCard(Long id);

    //Transaction findByDocumentNumber(String documentNumber);
}


