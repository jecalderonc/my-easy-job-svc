package com.uoc.myeasyjob.dao;

import com.uoc.myeasyjob.domain.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interface that expose the methods that interacts with the database for the Category entity.
 */
@Repository
public interface CategoryDao extends CrudRepository<Category, Integer> {
    List<Category> findAll();

    Optional<Category> findById(Integer id);

    Category save(Category category);

    void deleteById(Integer id);

    List<Category> findAllByIdCategoryIn(List<Integer> ids);
}
