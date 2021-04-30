package com.uoc.myeasyjob.dao;

import com.uoc.myeasyjob.domain.Rating;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interface that expose the methods that interacts with the database for the Rating entity.
 */
@Repository
public interface RatingDao extends CrudRepository<Rating, Integer> {
    List<Rating> findAll();

    Optional<Rating> findById(Integer id);

    Rating save(Rating rating);

    void deleteById(Integer id);

    List<Rating> findByIdUser(Integer id);
}
