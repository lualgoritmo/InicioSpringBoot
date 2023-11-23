package com.lucianobass.cardactivity.repositories;

import com.lucianobass.cardactivity.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
}
