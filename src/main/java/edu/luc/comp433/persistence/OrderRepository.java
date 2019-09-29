package edu.luc.comp433.persistence;

import edu.luc.comp433.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {
}
