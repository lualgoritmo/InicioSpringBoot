package com.lucianobass.cardactivity.repositories;

import com.lucianobass.cardactivity.modelsentitys.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
