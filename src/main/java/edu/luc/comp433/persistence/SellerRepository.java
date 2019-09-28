package edu.luc.comp433.persistence;

import edu.luc.comp433.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}
