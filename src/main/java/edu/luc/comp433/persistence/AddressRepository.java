package edu.luc.comp433.persistence;

import edu.luc.comp433.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AddressRepository extends JpaRepository<Address, Long> {
}
