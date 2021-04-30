package com.uoc.myeasyjob.dao;

import com.uoc.myeasyjob.domain.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interface that expose the methods that interacts with the database for the AppUser entity.
 */
@Repository
public interface AppUserDao extends CrudRepository<AppUser, Integer> {
    List<AppUser> findAll();

    Optional<AppUser> findById(Integer id);

    AppUser save(AppUser appUser);

    void deleteById(Integer id);

    Optional<AppUser> findByEmailAndPassword(String email, String pass);

    Optional<AppUser> findByEmail(String email);
}
