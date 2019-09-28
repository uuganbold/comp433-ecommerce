package edu.luc.comp433.persistence;

import edu.luc.comp433.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
