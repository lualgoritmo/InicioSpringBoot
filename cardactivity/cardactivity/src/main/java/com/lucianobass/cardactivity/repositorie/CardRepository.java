package com.lucianobass.cardactivity.repositorie;

import com.lucianobass.cardactivity.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
}
