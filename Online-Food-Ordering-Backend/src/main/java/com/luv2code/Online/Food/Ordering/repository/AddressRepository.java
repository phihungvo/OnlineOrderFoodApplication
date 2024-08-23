package com.luv2code.Online.Food.Ordering.repository;

import com.luv2code.Online.Food.Ordering.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
