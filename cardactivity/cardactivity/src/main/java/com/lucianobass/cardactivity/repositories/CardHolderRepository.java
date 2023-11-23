package com.lucianobass.cardactivity.repositories;

import com.lucianobass.cardactivity.models.CardHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardHolderRepository extends JpaRepository<CardHolder, Long> {
}
