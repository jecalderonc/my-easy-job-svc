package com.uoc.myeasyjob.dao;

import com.uoc.myeasyjob.domain.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interface that expose the methods that interacts with the database for the City entity.
 */
@Repository
public interface CityDao extends CrudRepository<City, Integer> {
    List<City> findAll();

    Optional<City> findById(Integer id);

    City save(City city);

    void deleteById(Integer id);

    List<City> findAllByIdCityIn(List<Integer> ids);
}
