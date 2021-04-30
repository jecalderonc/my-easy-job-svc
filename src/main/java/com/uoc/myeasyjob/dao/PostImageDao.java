package com.uoc.myeasyjob.dao;

import com.uoc.myeasyjob.domain.PostImage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interface that expose the methods that interacts with the database for the Rating entity.
 */
@Repository
public interface PostImageDao extends CrudRepository<PostImage, Integer> {
    List<PostImage> findAll();

    Optional<PostImage> findById(Integer id);

    Optional<PostImage> findByIdPostAndPriority(Integer id, Integer priority);

    PostImage save(PostImage postImage);

    void deleteById(Integer id);

    List<PostImage> findAllByIdPostOrderByPriority(Integer id);
}
