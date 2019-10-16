package edu.luc.comp433.persistence;

import edu.luc.comp433.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o JOIN FETCH o.items where o.id=(:id)")
    Optional<Order> findByIdAndFetchAndItemsEagerly(@Param("id") Long id);
}
