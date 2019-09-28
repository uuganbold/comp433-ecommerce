package edu.luc.comp433.persistence;

import edu.luc.comp433.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
