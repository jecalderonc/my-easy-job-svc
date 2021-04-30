package com.uoc.myeasyjob.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uoc.myeasyjob.domain.AppUser;
import com.uoc.myeasyjob.domain.Category;
import com.uoc.myeasyjob.domain.City;
import com.uoc.myeasyjob.domain.Post;
import com.uoc.myeasyjob.domain.Rating;
import com.uoc.myeasyjob.exception.MyEasyJobException;
import com.uoc.myeasyjob.rest.model.PostCardView;
import com.uoc.myeasyjob.rest.model.UserView;
import com.uoc.myeasyjob.service.MyEasyJobService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.uoc.myeasyjob.util.Constants.SEMICOLON;
import static com.uoc.myeasyjob.util.ErrorConstants.BAD_REQUEST;
import static com.uoc.myeasyjob.util.ErrorConstants.PARSING_REQUEST_ERROR_CODE;
import static com.uoc.myeasyjob.util.ErrorConstants.PARSING_REQUEST_ERROR_MESSAGE;

/**
 * Implementation of the rest controller MyEasyJobController.
 */
@Component
public class MyEasyJobControllerImpl implements MyEasyJobController {

    private final MyEasyJobService myEasyJobService;

    public MyEasyJobControllerImpl(MyEasyJobService myEasyJobService) {
        this.myEasyJobService = myEasyJobService;
    }

    /**
     * Retrieve all the categories.
     *
     * @return {@link List}
     */
    @Override
    public List<Category> getAllCategories() {
        return myEasyJobService.getCategories();
    }

    /**
     * Retrieve all the cities.
     *
     * @return {@link List}
     */
    public List<City> getAllCities() {
        return myEasyJobService.getCities();
    }

    /**
     * Retrieve the user information of the user using the id sent as parameter.
     *
     * @return {@link AppUser}
     */
    @Override
    public AppUser getUser(Integer userId) {
        return myEasyJobService.getUserById(userId);
    }

    /**
     * Retrieve the basic information of the user using the id sent as parameter.
     *
     * @return {@link AppUser}
     */
    @Override
    public AppUser getUserByEmail(String email) {
        return myEasyJobService.getUserByEmail(email);
    }

    /**
     * Validate if the email is available to register a new user.
     *
     * @return {@link Boolean}
     */
    public Boolean isEmailAvailable(String email){
        return myEasyJobService.isEmailAvailable(email);
    }

    /**
     * Update the user information.
     */
    @Override
    public void saveUser(MultipartFile file, String appUser) {
        //The object is received as String, we need to map it using a mapper.
        AppUser appUser1;
        try {
            appUser1 = new ObjectMapper().readValue(appUser, AppUser.class);
        } catch (Exception e) {
            throw new MyEasyJobException(
                    PARSING_REQUEST_ERROR_CODE,
                    PARSING_REQUEST_ERROR_MESSAGE,
                    ExceptionUtils.getStackTrace(e),
                    BAD_REQUEST
            );
        }
        myEasyJobService.saveUser(file, appUser1);
    }

    /**
     * Retrieve the list of post according to the cityId and categoryId parameters.
     *
     * @return {@link List}
     */
    @Override
    public List<Post> getPostByCityAndCategory(Integer city, Integer category) {
        return myEasyJobService.getPostByCityAndCategory(city, category);
    }

    /**
     * Retrieve the lost of post created by the user.
     *
     * @return {@link List}
     */
    @Override
    public List<Post> getPostByUser(Integer userId) {
        return myEasyJobService.getPostsByUserId(userId);
    }

    /**
     * Retrieve all the information of the post using the id of the post.
     *
     * @return {@link Post}
     */
    @Override
    public Post getPostEditMode(Integer postId) {
        return myEasyJobService.getPostEditMode(postId);
    }

    /**
     * Retrieve the basic information of the post using the id sent as parameter.
     *
     * @return {@link Post}
     */
    @Override
    public Post getPost(Integer postId) {
        return myEasyJobService.getPostById(postId);
    }

    /**
     * Retrieve the details of the post using the id sent as parameter.
     *
     * @return {@link PostCardView}
     */
    @Override
    public PostCardView getPostCardView(Integer postId) {
        return myEasyJobService.getPostCardViewById(postId);
    }

    /**
     * Save the post information, the method also saves the images asociated to the post, these images are received as
     * multipart object.
     */
    @Override
    public void savePost(MultipartFile[] files, String post) {
        //The object is received as String, we need to map it using a mapper.
        Post post1;
        try {
            post1 = new ObjectMapper().readValue(post, Post.class);
        } catch (Exception e) {
            throw new MyEasyJobException(
                    PARSING_REQUEST_ERROR_CODE,
                    PARSING_REQUEST_ERROR_MESSAGE,
                    ExceptionUtils.getStackTrace(e),
                    BAD_REQUEST
            );
        }
        myEasyJobService.savePost(files, post1);
    }

    /**
     * Delete the post associated to the id sent, this method also deletes the images asociated to the post.
     */
    @Override
    public void deletePost(Integer postId) {
        myEasyJobService.deletePost(postId);
    }

    /**
     * Retrieve the list of ranting of the user using the id sent as parameter.
     *
     * @return {@link List}
     */
    @Override
    public List<Rating> getRatingByUser(Integer user) {
        return myEasyJobService.getRatingsByUserId(user);
    }

    /**
     * Save the rating sent as parameter.
     */
    @Override
    public void saveRating(Rating rating) {
        myEasyJobService.saveRating(rating);
    }

    /**
     * Delete the rating associated to the id sent as parameter.
     */
    @Override
    public void deleteRating(Integer ratingId) {
        myEasyJobService.deleteRating(ratingId);
    }

    /**
     * Retrieve the list of categories that have associated at least one post.
     *
     * @return {@link List}
     */
    @Override
    public List<Category> getCategoriesWithPost() {
        return myEasyJobService.getCategoriesWithPost();
    }

    /**
     * Retrieve the list of cities that have associated at least one post.
     *
     * @return {@link List}
     */
    @Override
    public List<City> getCitiesWithPost() {
        return myEasyJobService.getCitiesWithPost();
    }

    /**
     * Retrieve the list of post that are related to one of the categories or cities sent as parameters.
     *
     * @return {@link List}
     */
    @Override
    public List<PostCardView> getPostByCityListAndCategoryList(String idsCity, String idsCategory) {
        //the parameters are splited in order to get a list of id of the cities and categories.
        List<Integer> cities;
        List<Integer> categories;
        try {
            cities = (Objects.isNull(idsCity) || StringUtils.isEmpty(idsCity)) ?
                    Collections.EMPTY_LIST :
                    Arrays.stream(idsCity.split(SEMICOLON)).map(Integer::parseInt).collect(Collectors.toList());
            categories = (Objects.isNull(idsCategory) || StringUtils.isEmpty(idsCategory)) ?
                    Collections.EMPTY_LIST :
                    Arrays.stream(idsCategory.split(SEMICOLON)).map(Integer::parseInt).collect(Collectors.toList());

        } catch (Exception e) {
            throw new MyEasyJobException(
                    PARSING_REQUEST_ERROR_CODE,
                    PARSING_REQUEST_ERROR_MESSAGE,
                    ExceptionUtils.getStackTrace(e),
                    BAD_REQUEST
            );
        }

        return myEasyJobService.getPostByCityListAndCategoryList(cities, categories);
    }

    /**
     * Retrieve the basic information of the user using the id received as parameter.
     *
     * @return {@link UserView}
     */
    @Override
    public UserView getUserView(Integer userId) {
        return myEasyJobService.getUserView(userId);
    }
}
