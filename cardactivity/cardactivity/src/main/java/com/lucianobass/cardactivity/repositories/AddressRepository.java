package com.lucianobass.cardactivity.repositories;

import com.lucianobass.cardactivity.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
