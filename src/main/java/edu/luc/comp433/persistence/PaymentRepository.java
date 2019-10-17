package edu.luc.comp433.persistence;

import edu.luc.comp433.model.Customer;
import edu.luc.comp433.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    public Optional<Payment> findByIdAndCustomer(Long id, Customer customer);
}
