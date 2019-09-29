package edu.luc.comp433.persistence;

import edu.luc.comp433.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
