package com.uoc.myeasyjob.service;

import com.uoc.myeasyjob.dao.AppUserDao;
import com.uoc.myeasyjob.dao.CategoryDao;
import com.uoc.myeasyjob.dao.CityDao;
import com.uoc.myeasyjob.dao.PostDao;
import com.uoc.myeasyjob.dao.PostImageDao;
import com.uoc.myeasyjob.dao.RatingDao;
import com.uoc.myeasyjob.domain.AppUser;
import com.uoc.myeasyjob.domain.Category;
import com.uoc.myeasyjob.domain.City;
import com.uoc.myeasyjob.domain.Post;
import com.uoc.myeasyjob.domain.PostImage;
import com.uoc.myeasyjob.domain.Rating;
import com.uoc.myeasyjob.exception.MyEasyJobException;
import com.uoc.myeasyjob.rest.model.PostCardView;
import com.uoc.myeasyjob.rest.model.RatingUserView;
import com.uoc.myeasyjob.rest.model.RatingView;
import com.uoc.myeasyjob.rest.model.UserView;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.uoc.myeasyjob.util.ErrorConstants.FINDING_ENTITY_ERROR_CODE;
import static com.uoc.myeasyjob.util.ErrorConstants.FINDING_ENTITY_ERROR_MESSAGE;
import static com.uoc.myeasyjob.util.ErrorConstants.INTERNAL_ERROR;
import static com.uoc.myeasyjob.util.ErrorConstants.NOT_FOUND;
import static com.uoc.myeasyjob.util.ErrorConstants.PROCESS_FILE_ERROR_CODE;
import static com.uoc.myeasyjob.util.ErrorConstants.PROCESS_FILE_ERROR_MESSAGE;

/**
 * Implementation of the MyEasyJobService interface that exposes all the services that manage the resources.
 */
@Service
public class MyEasyJobServiceImpl implements MyEasyJobService {

    private final CategoryDao categoryDao;

    private final CityDao cityDao;

    private final PostDao postDao;

    private final RatingDao ratingDao;

    private final AppUserDao appUserDao;

    private final PostImageDao postImageDao;

    public MyEasyJobServiceImpl(CategoryDao categoryDao, CityDao cityDao, PostDao postDao, RatingDao ratingDao, AppUserDao appUserDao, PostImageDao postImageDao) {
        this.categoryDao = categoryDao;
        this.cityDao = cityDao;
        this.postDao = postDao;
        this.ratingDao = ratingDao;
        this.appUserDao = appUserDao;
        this.postImageDao = postImageDao;
    }

    /**
     * Return the information of the user associated to the id parameter.
     *
     * @param id Id if the user,
     * @return {@link AppUser}
     */
    @Override
    public AppUser getUserById(Integer id) {
        return this.findUserByID(id);
    }

    /**
     * Save the user received as parameter.
     *
     * @param file    Profile image as multipart object.
     * @param appUser User info.
     * @return {@link AppUser}
     */
    @Override
    public AppUser saveUser(MultipartFile file, AppUser appUser) {

        byte[] byteArr = null;

        if (Objects.nonNull(file)) {
            try {
                byteArr = file.getBytes();
            } catch (IOException e) {
                throw new MyEasyJobException(
                        PROCESS_FILE_ERROR_CODE,
                        PROCESS_FILE_ERROR_MESSAGE,
                        ExceptionUtils.getStackTrace(e),
                        INTERNAL_ERROR
                );
            }
        }

        AppUser currentUser = this.findUserByID(appUser.getIdAppUser());
        currentUser.setName(Objects.nonNull(appUser.getName()) ? appUser.getName() : currentUser.getName());
        currentUser.setPassword(Objects.nonNull(appUser.getPassword()) ? appUser.getPassword() : currentUser.getPassword());
        currentUser.setDescription(Objects.nonNull(appUser.getDescription()) ? appUser.getDescription() : currentUser.getDescription());
        currentUser.setEmail(Objects.nonNull(appUser.getEmail()) ? appUser.getEmail() : currentUser.getEmail());
        currentUser.setImage(Objects.nonNull(appUser.getImage()) ? appUser.getImage() : currentUser.getImage());
        currentUser.setPhone(Objects.nonNull(appUser.getPhone()) ? appUser.getPhone() : currentUser.getPhone());
        if (Objects.nonNull(byteArr)) {
            currentUser.setImage(byteArr);
        }
        return appUserDao.save(currentUser);
    }

    /**
     * Delete the user associated the id parameter.
     *
     * @param id Id of the User.
     * @return {@link Boolean}
     */
    @Override
    public Boolean deleteUser(Integer id) {
        appUserDao.deleteById(id);
        return Boolean.TRUE;
    }

    /**
     * Return the info of the post associated to the id parameter.
     *
     * @param id Id of the post.
     * @return {@link Post}
     */
    @Override
    public Post getPostById(Integer id) {
        return this.findPostByID(id);
    }

    /**
     * Return the post associated to the id parameter, the method also Return the list of images of the post.
     *
     * @param postId Id of the post.
     * @return {@link Post}
     */
    @Override
    public Post getPostEditMode(Integer postId) {
        Post post = this.findPostByID(postId);
        List postImageList = postImageDao.findAllByIdPostOrderByPriority(postId);
        post.setPostImageList(postImageList);
        return post;
    }

    /**
     * Returns the list of post associated to the UserID parameter.
     *
     * @param id Id of the user.
     * @return {@link List}
     */
    @Override
    public List<Post> getPostsByUserId(Integer id) {
        List<Post> posts = postDao.findByIdUser(id);
        posts.forEach(
                post -> post.setMainPostImage(
                        postImageDao.findAllByIdPostOrderByPriority(post.getIdPost())
                                .stream()
                                .findFirst()
                                .orElse(null)
                ));

        return posts;
    }

    /**
     * Save the post received as parameter, the method also save the list of images associated to the post.
     *
     * @param files List of images of the post.
     * @param post  Post object.
     * @return {@link Post}
     */
    @Override
    public Post savePost(MultipartFile[] files, Post post) {

        Post post1 = postDao.save(post);

        if (Objects.nonNull(files)) {
            for (MultipartFile file : files) {
                if (Objects.nonNull(file)) {
                    try {
                        Integer priority = Integer.valueOf(Objects.requireNonNull(file.getOriginalFilename()));
                        PostImage postImage = postImageDao.findByIdPostAndPriority(post.getIdPost(), priority).orElse(new PostImage());
                        byte[] byteArr = file.getBytes();
                        postImage.setIdPost(post1.getIdPost());
                        postImage.setPriority(priority);
                        postImage.setImage(byteArr);
                        postImageDao.save(postImage);
                    } catch (Exception e) {
                        throw new MyEasyJobException(
                                PROCESS_FILE_ERROR_CODE,
                                PROCESS_FILE_ERROR_MESSAGE,
                                ExceptionUtils.getStackTrace(e),
                                INTERNAL_ERROR
                        );
                    }
                }
            }
        }

        return post1;
    }

    /**
     * Delete the post associated to the id parameter, the method also deletes the images associated to the post.
     *
     * @param id Id of the post.
     * @return {@link Boolean}
     */
    @Override
    public Boolean deletePost(Integer id) {
        List<PostImage> postImages = postImageDao.findAllByIdPostOrderByPriority(id);
        postImages.forEach(
                postImage -> postImageDao.deleteById(postImage.getIdPostImage())
        );
        postDao.deleteById(id);
        return Boolean.TRUE;
    }

    /**
     * Return the ratings associated to the User Id parameter.
     *
     * @param id Id of the user.
     * @return {@link List}
     */
    @Override
    public List<Rating> getRatingsByUserId(Integer id) {
        return ratingDao.findByIdUser(id);
    }

    /**
     * Save the rating received as parameter.
     *
     * @param rating Rating object.
     * @return {@link Rating}
     */
    @Override
    public Rating saveRating(Rating rating) {
        return ratingDao.save(rating);
    }

    /**
     * Delete the rating associated to the Rating Id parameter.
     *
     * @param id Id of the rating.
     * @return {@link Boolean}
     */
    @Override
    public Boolean deleteRating(Integer id) {
        ratingDao.deleteById(id);
        return Boolean.TRUE;
    }

    /**
     * Return the list of all the categories.
     *
     * @return {@link List}
     */
    @Override
    public List<Category> getCategories() {
        return categoryDao.findAll();
    }

    /**
     * Return the list of all the cities.
     *
     * @return {@link List}
     */
    @Override
    public List<City> getCities() {
        return cityDao.findAll();
    }

    /**
     * Return the list of post associated to the city or category received as parameter.
     *
     * @param city     Id of the city.
     * @param category Id of the Category.
     * @return {@link List}
     */
    @Override
    public List<Post> getPostByCityAndCategory(Integer city, Integer category) {
        if (city == 0 && category == 0) {
            return postDao.findAll();
        } else if (city != 0 && category == 0) {
            return postDao.findByIdCity(city);
        } else if (city == 0 && category != 0) {
            return postDao.findByIdCategory(category);
        } else {
            return postDao.findByIdCityAndIdCategory(city, category);
        }
    }

    /**
     * Return the categories that haves at least one post associated.
     *
     * @return {@link List}
     */
    @Override
    public List<Category> getCategoriesWithPost() {
        List<Category> lst = categoryDao.findAllByIdCategoryIn(postDao.findAll().stream().map(Post::getIdCategory).distinct().collect(Collectors.toList()));

        for (Category category : lst) {
            category.setActiveCount(postDao.countAllByIdCategory(category.getIdCategory()));
        }
        return lst;
    }

    /**
     * Return the cities that haves at least one post associated.
     *
     * @return {@link List}
     */
    @Override
    public List<City> getCitiesWithPost() {
        List<City> lst = cityDao.findAllByIdCityIn(postDao.findAll().stream().map(Post::getIdCity).distinct().collect(Collectors.toList()));

        for (City city : lst) {
            city.setActiveCount(postDao.countAllByIdCity(city.getIdCity()));
        }
        return lst;
    }

    /**
     * Return the rating view object with the details of the scores associated to the user.
     *
     * @param id Id of the user.
     * @return {@link RatingView}
     */
    public RatingView getRatingViewByUser(Integer id) {
        List<Rating> ratings = ratingDao.findByIdUser(id);
        return getRatingViewByListRatingUser(ratings);
    }

    /**
     * Return the rating view object with the details of the scores associated to the user.
     *
     * @param id Id of the user.
     * @return {@link RatingView}
     */
    private RatingView getRatingViewByListRatingUser(List<Rating> ratings) {
        RatingView ratingView = new RatingView(0, 0d);
        if (!ratings.isEmpty()) {
            Double score = ratings.stream().mapToDouble(Rating::getScore).average().orElse(0);
            Integer quantity = ratings.size();
            return new RatingView(quantity, score);
        }
        return ratingView;
    }

    /**
     * Return the list of post associated to the city list or category list received as parameter.
     *
     * @param idsCity     List of ids of the cities.
     * @param idsCategory List of ids of the categories.
     * @return {@link List}
     */
    @Override
    public List<PostCardView> getPostByCityListAndCategoryList(List<Integer> idsCity, List<Integer> idsCategory) {
        List<PostCardView> postCardViews = Collections.EMPTY_LIST;
        List<Post> posts = Collections.EMPTY_LIST;
        if (idsCategory.isEmpty() && idsCity.isEmpty())
            posts = postDao.findAll();

        if (!idsCategory.isEmpty() && !idsCity.isEmpty())
            posts = postDao.findByIdCityInAndIdCategoryIn(idsCity, idsCategory);

        if (idsCategory.isEmpty() && !idsCity.isEmpty())
            posts = postDao.findByIdCityIn(idsCity);

        if (!idsCategory.isEmpty() && idsCity.isEmpty())
            posts = postDao.findByIdCategoryIn(idsCategory);

        if (!posts.isEmpty()) {
            postCardViews = posts.stream().map(this::buildPostCardView).collect(Collectors.toList());
        }

        return postCardViews;
    }

    /**
     * Return the detail of the post associated to the id parameter.
     *
     * @param postId Id of the post.
     * @return {@link PostCardView}
     */
    @Override
    public PostCardView getPostCardViewById(Integer postId) {
        Post post = this.findPostByID(postId);
        List postImageList = postImageDao.findAllByIdPostOrderByPriority(postId);
        PostCardView postCardView = buildPostCardView(post);
        Objects.requireNonNull(postCardView).setPostImageList(postImageList);
        return postCardView;
    }

    /**
     * Get all the information of the post and build the object with all the relevant information.
     *
     * @param post Post object.
     * @return {@link PostCardView}
     */
    private PostCardView buildPostCardView(Post post) {
        if (Objects.nonNull(post)) {
            AppUser appUser = this.findUserByID(post.getIdUser());
            return new PostCardView(
                    post,
                    this.findCityByID(post.getIdCity()),
                    this.findCategoryByID(post.getIdCategory()),
                    getRatingViewByUser(appUser.getIdAppUser()),
                    new UserView(appUser),
                    postImageDao.findAllByIdPostOrderByPriority(post.getIdPost())
                            .stream()
                            .findFirst()
                            .orElse(null)
            );
        }
        return null;
    }

    /**
     * Return the detail of the user associated to the userId parameter.
     *
     * @param userId Id of the user.
     * @return {@link UserView}
     */
    @Override
    public UserView getUserView(Integer userId) {
        UserView userView = new UserView(this.getUserById(userId));
        List<Rating> ratings = ratingDao.findByIdUser(userId);
        userView.setRatingView(this.getRatingViewByListRatingUser(ratings));
        if (!ratings.isEmpty()) {
            ratings = ratings.stream().peek(rating -> rating.setQualifierRatingUserView(new RatingUserView(this.findUserByID(rating.getIdQualifierUser())))
            ).collect(Collectors.toList());
        }
        userView.setRatingList(ratings);
        return userView;
    }

    /**
     * find the user or throws a new exception.
     *
     * @param id Id of the User
     * @return {@link AppUser}
     */
    private AppUser findUserByID(Integer id) {
        return appUserDao.findById(id).orElseThrow(
                () -> new MyEasyJobException(
                        FINDING_ENTITY_ERROR_CODE,
                        FINDING_ENTITY_ERROR_MESSAGE,
                        "Method: findUserByID, Id: " + id,
                        NOT_FOUND
                )
        );
    }

    /**
     * find the category or throws a new exception.
     *
     * @param id Id of the category
     * @return {@link Category}
     */
    private Category findCategoryByID(Integer id) {
        return categoryDao.findById(id).orElseThrow(
                () -> new MyEasyJobException(
                        FINDING_ENTITY_ERROR_CODE,
                        FINDING_ENTITY_ERROR_MESSAGE,
                        "Method: findCategoryByID, Id: " + id,
                        NOT_FOUND
                )
        );
    }

    /**
     * find the city or throws a new exception.
     *
     * @param id Id of the city
     * @return {@link City}
     */
    private City findCityByID(Integer id) {
        return cityDao.findById(id).orElseThrow(
                () -> new MyEasyJobException(
                        FINDING_ENTITY_ERROR_CODE,
                        FINDING_ENTITY_ERROR_MESSAGE,
                        "Method: findCityByID, Id: " + id,
                        NOT_FOUND
                )
        );
    }

    /**
     * find the post or throws a new exception.
     *
     * @param id Id of the post
     * @return {@link Post}
     */
    private Post findPostByID(Integer id) {
        return postDao.findById(id).orElseThrow(
                () -> new MyEasyJobException(
                        FINDING_ENTITY_ERROR_CODE,
                        FINDING_ENTITY_ERROR_MESSAGE,
                        "Method: findPostByID, Id: " + id,
                        NOT_FOUND
                )
        );
    }


    /**
     * Return the user associated the the email parameter.
     *
     * @param email Email of the user.
     * @return {@link AppUser}
     */
    @Override
    public AppUser getUserByEmail(String email) {
        return appUserDao.findByEmail(email).orElseThrow(
                () -> new MyEasyJobException(
                        FINDING_ENTITY_ERROR_CODE,
                        FINDING_ENTITY_ERROR_MESSAGE,
                        "Method: getUserByEmail, Id: " + email,
                        NOT_FOUND
                )
        );
    }

    /**
     * Validate if the email is available to register a new user.
     *
     * @return {@link Boolean}
     */
    public Boolean isEmailAvailable(String email){
        return Objects.isNull(appUserDao.findByEmail(email).orElse(null));
    }
}
