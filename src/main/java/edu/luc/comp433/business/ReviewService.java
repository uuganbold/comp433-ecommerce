package edu.luc.comp433.business;

import edu.luc.comp433.business.dto.ReviewDTO;
import edu.luc.comp433.exceptions.DuplicatedEntryException;
import edu.luc.comp433.exceptions.EntryNotFoundException;

import java.util.List;

public interface ReviewService {

    ReviewDTO getReview(long id);

    ReviewDTO createReview(ReviewDTO reviewDTO) throws DuplicatedEntryException;

    ReviewDTO save(ReviewDTO dto) throws EntryNotFoundException;

    List<ReviewDTO> list();

    void delete(long id) throws EntryNotFoundException;
}
