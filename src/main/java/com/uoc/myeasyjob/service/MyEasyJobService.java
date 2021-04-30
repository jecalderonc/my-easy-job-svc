package com.uoc.myeasyjob.service;

import com.uoc.myeasyjob.domain.AppUser;
import com.uoc.myeasyjob.domain.Category;
import com.uoc.myeasyjob.domain.City;
import com.uoc.myeasyjob.domain.Post;
import com.uoc.myeasyjob.domain.Rating;
import com.uoc.myeasyjob.rest.model.UserView;
import com.uoc.myeasyjob.rest.model.PostCardView;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * Service interface that exposes all the services that manage the resources.
 *
 */
public interface MyEasyJobService {

    AppUser getUserById(Integer id);

    AppUser saveUser(MultipartFile file, AppUser appUser);

    Boolean deleteUser(Integer id);

    Post getPostById(Integer id);

    public Post getPostEditMode(Integer postId);

    List<Post> getPostsByUserId(Integer id);

    Post savePost(MultipartFile[] files,Post post);

    Boolean deletePost(Integer id);

    List<Rating> getRatingsByUserId(Integer id);

    Rating saveRating(Rating rating);

    Boolean deleteRating(Integer id);

    List<Category> getCategories();

    List<City> getCities();

    List<Post> getPostByCityAndCategory(Integer city, Integer category);

    List<Category> getCategoriesWithPost();

    List<City> getCitiesWithPost();

    List<PostCardView> getPostByCityListAndCategoryList(List<Integer> idsCity, List<Integer> idsCategory);

    PostCardView getPostCardViewById(Integer postId);

    UserView getUserView(Integer userId);

    AppUser getUserByEmail(String email);

    Boolean isEmailAvailable(String email);
}
