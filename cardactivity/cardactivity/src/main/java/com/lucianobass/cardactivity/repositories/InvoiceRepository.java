package com.lucianobass.cardactivity.repositories;

import com.lucianobass.cardactivity.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    //    @Query("SELECT i FROM Invoice i JOIN i.transactions t WHERE t.card.idCard = :cardId")
//    List<Invoice> findInvoicesByCardId(@Param("cardId") Long cardId);

    @Query("SELECT DISTINCT i FROM Invoice i JOIN FETCH i.transactions t WHERE t.card.idCard = :cardId")
    List<Invoice> findInvoicesWithDetailsByCardId(@Param("cardId") Long cardId);

    //@Query("SELECT i FROM Invoice i WHERE YEAR(i.closingDate) = YEAR(:date) AND MONTH(i.closingDate) = MONTH(:date) AND i.card.idCard = :idCard")
    Invoice findFirstInvoiceByCardIdCardOrderByClosingDateDesc(Long idCard);
}
