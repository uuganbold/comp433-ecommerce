package edu.luc.comp433.persistence;

import edu.luc.comp433.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    @Query("select s from Seller s JOIN FETCH s.addresses where s.id=(:id)")
    Optional<Seller> findByIdAndFetchAndAddressesEagerly(@Param("id") Long id);
}
