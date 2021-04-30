package com.uoc.myeasyjob.dao;

import com.uoc.myeasyjob.domain.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interface that expose the methods that interacts with the database for the Post entity.
 */
@Repository
public interface PostDao extends CrudRepository<Post, Integer> {
    List<Post> findAll();

    Optional<Post> findById(Integer id);

    Post save(Post post);

    void deleteById(Integer id);

    List<Post> findByIdUser(Integer id);

    List<Post> findByIdCategory(Integer id);

    List<Post> findByIdCategoryIn(List<Integer> idsCategory);

    List<Post> findByIdCity(Integer id);

    List<Post> findByIdCityIn(List<Integer> idsCity);

    List<Post> findByIdCityAndIdCategory(Integer city, Integer category);

    List<Post> findByIdCityInAndIdCategoryIn(List<Integer> idsCity, List<Integer> idsCategory);

    Integer countAllByIdCategory(Integer id);

    Integer countAllByIdCity(Integer id);
}
