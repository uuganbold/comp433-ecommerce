package edu.luc.comp433.persistence;

import edu.luc.comp433.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {
}
