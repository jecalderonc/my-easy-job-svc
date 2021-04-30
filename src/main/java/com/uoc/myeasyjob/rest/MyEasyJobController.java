package com.uoc.myeasyjob.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uoc.myeasyjob.domain.AppUser;
import com.uoc.myeasyjob.domain.Category;
import com.uoc.myeasyjob.domain.City;
import com.uoc.myeasyjob.domain.Post;
import com.uoc.myeasyjob.domain.Rating;
import com.uoc.myeasyjob.rest.model.PostCardView;
import com.uoc.myeasyjob.rest.model.UserView;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.uoc.myeasyjob.util.Constants.MY_EASY_JOB;

/**
 * Rest controller that exposes all the endpoints that manage the resources.
 */
@RestController
@CrossOrigin
@RequestMapping("/" + MY_EASY_JOB)
public interface MyEasyJobController {

    /**
     * Retrieve all the categories.
     *
     * @return {@link List}
     */
    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    List<Category> getAllCategories();

    /**
     * Retrieve all the cities.
     *
     * @return {@link List}
     */
    @RequestMapping(value = "/cities", method = RequestMethod.GET)
    List<City> getAllCities();

    /**
     * Retrieve the user information of the user using the id sent as parameter.
     *
     * @return {@link AppUser}
     */
    @RequestMapping(value = "/account/user/{userId}", method = RequestMethod.GET)
    AppUser getUser(@PathVariable Integer userId);

    /**
     * Retrieve the basic information of the user using the id sent as parameter.
     *
     * @return {@link AppUser}
     */
    @RequestMapping(value = "/account/user/{email}/basicinfo", method = RequestMethod.GET)
    AppUser getUserByEmail(@PathVariable String email);

    /**
     * Validate if the email is available to register a new user.
     *
     * @return {@link Boolean}
     */
    @RequestMapping(value = "/user/email/isavaliable", method = RequestMethod.GET)
    Boolean isEmailAvailable(@RequestParam String email);

    /**
     * Update the user information.
     */
    @RequestMapping(value = "/account/user", method = RequestMethod.POST)
    void saveUser(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam("appUser") String appUser);

    /**
     * Retrieve the list of post according to the cityId and categoryId parameters.
     *
     * @return {@link List}
     */
    @RequestMapping(value = "/post/getpostbyparams", method = RequestMethod.GET)
    List<Post> getPostByCityAndCategory(@RequestParam Integer city, @RequestParam Integer category);

    /**
     * Retrieve the lost of post created by the user.
     *
     * @return {@link List}
     */
    @RequestMapping(value = "/account/post/user/{userId}", method = RequestMethod.GET)
    List<Post> getPostByUser(@PathVariable() Integer userId);

    /**
     * Retrieve all the information of the post using the id of the post.
     *
     * @return {@link Post}
     */
    @RequestMapping(value = "/account/post/{postId}", method = RequestMethod.GET)
    Post getPostEditMode(@PathVariable Integer postId);

    /**
     * Retrieve the basic information of the post using the id sent as parameter.
     *
     * @return {@link Post}
     */
    @RequestMapping(value = "/post/{postId}", method = RequestMethod.GET)
    Post getPost(@PathVariable Integer postId);

    /**
     * Retrieve the details of the post using the id sent as parameter.
     *
     * @return {@link PostCardView}
     */
    @RequestMapping(value = "/post/{postId}/details", method = RequestMethod.GET)
    PostCardView getPostCardView(@PathVariable Integer postId);

    /**
     * Save the post information, the method also saves the images asociated to the post, these images are received as
     * multipart object.
     */
    @RequestMapping(value = "/account/post", method = RequestMethod.POST)
    void savePost(@RequestParam(value = "files", required = false) MultipartFile[] file, @RequestParam("post") String post);

    /**
     * Delete the post associated to the id sent, this method also deletes the images asociated to the post.
     */
    @RequestMapping(value = "/account/post/{postId}", method = RequestMethod.DELETE)
    void deletePost(@PathVariable Integer postId);

    /**
     * Retrieve the list of ranting of the user using the id sent as parameter.
     *
     * @return {@link List}
     */
    @RequestMapping(value = "/rating/getratingbyuser", method = RequestMethod.GET)
    List<Rating> getRatingByUser(@RequestParam Integer user);

    /**
     * Save the rating sent as parameter.
     */
    @RequestMapping(value = "/account/rating", method = RequestMethod.POST)
    void saveRating(@RequestBody Rating rating);

    /**
     * Delete the rating associated to the id sent as parameter.
     */
    @RequestMapping(value = "/account/rating/{ratingId}", method = RequestMethod.DELETE)
    void deleteRating(@PathVariable Integer ratingId);

    /**
     * Retrieve the list of categories that have associated at least one post.
     *
     * @return {@link List}
     */
    @RequestMapping(value = "/categories/active", method = RequestMethod.GET)
    List<Category> getCategoriesWithPost();

    /**
     * Retrieve the list of cities that have associated at least one post.
     *
     * @return {@link List}
     */
    @RequestMapping(value = "/cities/active", method = RequestMethod.GET)
    List<City> getCitiesWithPost();

    /**
     * Retrieve the list of post that are related to one of the categories or cities sent as parameters.
     *
     * @return {@link List}
     */
    @RequestMapping(value = "/post/getpostbylistparams", method = RequestMethod.GET)
    List<PostCardView> getPostByCityListAndCategoryList(String idsCity, String idsCategory);

    /**
     * Retrieve the basic information of the user using the id received as parameter.
     *
     * @return {@link UserView}
     */
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    UserView getUserView(@PathVariable Integer userId);
}
