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
import com.uoc.myeasyjob.rest.model.RatingView;
import com.uoc.myeasyjob.rest.model.UserView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class MyEasyJobServiceImplTest {

    private static final String DEFAULT_EMAIL = "correo@correo.com";

    @Mock
    private CategoryDao categoryDao;

    @Mock
    private CityDao cityDao;

    @Mock
    private PostDao postDao;

    @Mock
    private RatingDao ratingDao;

    @Mock
    private AppUserDao appUserDao;

    @Mock
    private PostImageDao postImageDao;

    private MyEasyJobServiceImpl myEasyJobService;

    @Before
    public void setUp() {
        myEasyJobService = new MyEasyJobServiceImpl(
                categoryDao,
                cityDao,
                postDao,
                ratingDao,
                appUserDao,
                postImageDao);
    }

    @Test
    public void getUserByIdTest() {
        when(appUserDao.findById(anyInt())).thenReturn(Optional.of(new AppUser()));
        AppUser appUser = myEasyJobService.getUserById(1);
        assertNotNull(appUser);
    }

    @Test(expected = MyEasyJobException.class)
    public void getUserByIdTestThrowException() {
        when(appUserDao.findById(anyInt())).thenReturn(Optional.empty());
        AppUser appUser = myEasyJobService.getUserById(1);
        assertNotNull(appUser);
    }

    @Test
    public void saveUserTestNoFile() {
        AppUser appUserMock = mock(AppUser.class);
        when(appUserDao.findById(anyInt())).thenReturn(Optional.of(new AppUser()));
        when(appUserDao.save(any(AppUser.class))).thenReturn(new AppUser());
        AppUser appUser = myEasyJobService.saveUser(null, appUserMock);
        assertNotNull(appUser);
    }


    @Test
    public void saveUserTestWithFile() {
        AppUser appUserMock = mock(AppUser.class);
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(appUserDao.findById(anyInt())).thenReturn(Optional.of(new AppUser()));
        when(appUserDao.save(any(AppUser.class))).thenReturn(new AppUser());
        AppUser appUser = myEasyJobService.saveUser(multipartFile, appUserMock);
        assertNotNull(appUser);
    }

    @Test(expected = MyEasyJobException.class)
    public void saveUserTestWithFileAndException() throws IOException {
        AppUser appUserMock = mock(AppUser.class);
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getBytes()).thenThrow(new IOException());
        when(appUserDao.findById(anyInt())).thenReturn(Optional.of(new AppUser()));
        when(appUserDao.save(any(AppUser.class))).thenReturn(new AppUser());
        AppUser appUser = myEasyJobService.saveUser(multipartFile, appUserMock);
    }

    @Test
    public void deleteUserTest() {
        doNothing().when(appUserDao).deleteById(anyInt());
        Boolean result = myEasyJobService.deleteUser(1);
        assertTrue(result);
    }

    @Test
    public void getPostByIdTest() {
        when(postDao.findById(anyInt())).thenReturn(Optional.of(new Post()));
        Post post = myEasyJobService.getPostById(1);
        assertNotNull(post);
    }

    @Test(expected = MyEasyJobException.class)
    public void getPostByIdTestThrowException() {
        when(postDao.findById(anyInt())).thenReturn(Optional.empty());
        Post post = myEasyJobService.getPostById(1);
    }

    @Test
    public void getPostEditModeTest() {
        Post post = new Post();
        when(postDao.findById(anyInt())).thenReturn(Optional.of(post));
        when(postImageDao.findAllByIdPostOrderByPriority(anyInt())).thenReturn(Collections.singletonList(new PostImage()));
        Post result = myEasyJobService.getPostEditMode(1);
        assertNotNull(result);
        assertEquals(1, result.getPostImageList().size());
    }

    @Test
    public void getPostsByUserIdTest() {
        Post post = new Post();
        post.setIdPost(1);
        List<Post> postList = Collections.singletonList(post);

        List<PostImage> postImages = Collections.singletonList(new PostImage());

        when(postDao.findByIdUser(anyInt())).thenReturn(postList);
        when(postImageDao.findAllByIdPostOrderByPriority(1)).thenReturn(postImages);
        List<Post> result = myEasyJobService.getPostsByUserId(1);
        assertNotNull(result);
        assertNotNull(result.get(0).getMainPostImage());
    }

    @Test
    public void getPostsByUserIdTestNullPostImage() {
        Post post = new Post();
        post.setIdPost(1);
        List<Post> postList = Collections.singletonList(post);

        List<PostImage> postImages = Collections.EMPTY_LIST;

        when(postDao.findByIdUser(anyInt())).thenReturn(postList);
        when(postImageDao.findAllByIdPostOrderByPriority(1)).thenReturn(postImages);
        List<Post> result = myEasyJobService.getPostsByUserId(1);
        assertNotNull(result);
        assertNull(result.get(0).getMainPostImage());
    }

    @Test
    public void savePostTestEmptyFiles() {
        Post postMock = mock(Post.class);
        when(postDao.save(any(Post.class))).thenReturn(postMock);
        MultipartFile[] multipartFiles = new MultipartFile[0];
        Post result = myEasyJobService.savePost(multipartFiles, postMock);
        assertNotNull(result);
    }

    @Test
    public void savePostTestNullFiles() {
        Post postMock = mock(Post.class);
        when(postDao.save(any(Post.class))).thenReturn(postMock);
        Post result = myEasyJobService.savePost(null, postMock);
        assertNotNull(result);
    }

    @Test
    public void savePostTestWithFiles() {
        Post postMock = mock(Post.class);
        when(postDao.save(any(Post.class))).thenReturn(postMock);
        MultipartFile multipartFileMock = mock(MultipartFile.class);
        when(multipartFileMock.getOriginalFilename()).thenReturn("1");
        MultipartFile[] multipartFiles = {multipartFileMock};

        Post result = myEasyJobService.savePost(multipartFiles, postMock);
        assertNotNull(result);
    }

    @Test(expected = MyEasyJobException.class)
    public void savePostTestWithFilesThrownException() throws IOException {
        Post postMock = mock(Post.class);
        MultipartFile multipartFileMock = mock(MultipartFile.class);
        MultipartFile[] multipartFiles = {multipartFileMock};

        when(postDao.save(any(Post.class))).thenReturn(postMock);
        when(multipartFileMock.getOriginalFilename()).thenReturn("1");
        when(multipartFileMock.getBytes()).thenThrow(IOException.class);

        Post result = myEasyJobService.savePost(multipartFiles, postMock);
    }

    @Test(expected = MyEasyJobException.class)
    public void savePostTestWithFilesThrownNPException() {
        Post postMock = mock(Post.class);
        MultipartFile multipartFileMock = mock(MultipartFile.class);
        MultipartFile[] multipartFiles = {multipartFileMock};

        when(postDao.save(any(Post.class))).thenReturn(postMock);
        when(multipartFileMock.getOriginalFilename()).thenReturn("a");

        Post result = myEasyJobService.savePost(multipartFiles, postMock);
    }

    @Test
    public void deletePostTest() {
        when(postImageDao.findAllByIdPostOrderByPriority(anyInt()))
                .thenReturn(Collections.singletonList(mock(PostImage.class)));
        doNothing().when(postImageDao).deleteById(anyInt());
        doNothing().when(postDao).deleteById(anyInt());
        Boolean result = myEasyJobService.deletePost(1);
        assertTrue(result);
    }

    @Test
    public void getRatingsByUserIdTest() {
        when(ratingDao.findByIdUser(anyInt()))
                .thenReturn(Collections.singletonList(mock(Rating.class)));
        List<Rating> result = myEasyJobService.getRatingsByUserId(1);
        assertEquals(1, result.size());
    }

    @Test
    public void saveRatingTest() {
        when(ratingDao.save(any(Rating.class))).thenReturn(mock(Rating.class));
        Rating result = myEasyJobService.saveRating(new Rating());
        assertNotNull(result);
    }

    @Test
    public void deleteRatingTest() {
        doNothing().when(ratingDao).deleteById(anyInt());
        Boolean result = myEasyJobService.deleteRating(1);
        assertTrue(result);
    }

    @Test
    public void getCategoriesTest() {
        when(categoryDao.findAll())
                .thenReturn(Collections.singletonList(mock(Category.class)));
        List<Category> result = myEasyJobService.getCategories();
        assertEquals(1, result.size());
    }

    @Test
    public void getCitiesTest() {
        when(cityDao.findAll())
                .thenReturn(Collections.singletonList(mock(City.class)));
        List<City> result = myEasyJobService.getCities();
        assertEquals(1, result.size());
    }

    @Test
    public void getPostByCityAndCategoryTestScenario1() {
        Integer city = 0;
        Integer category = 0;
        when(postDao.findAll())
                .thenReturn(Collections.singletonList(mock(Post.class)));
        List<Post> result = myEasyJobService.getPostByCityAndCategory(city, category);
        assertEquals(1, result.size());
    }

    @Test
    public void getPostByCityAndCategoryTestScenario2() {
        Integer city = 1;
        Integer category = 0;
        when(postDao.findByIdCity(city))
                .thenReturn(Collections.singletonList(mock(Post.class)));
        List<Post> result = myEasyJobService.getPostByCityAndCategory(city, category);
        assertEquals(1, result.size());
    }

    @Test
    public void getPostByCityAndCategoryTestScenario3() {
        Integer city = 0;
        Integer category = 1;
        when(postDao.findByIdCategory(category))
                .thenReturn(Collections.singletonList(mock(Post.class)));
        List<Post> result = myEasyJobService.getPostByCityAndCategory(city, category);
        assertEquals(1, result.size());
    }

    @Test
    public void getPostByCityAndCategoryTestScenario4() {
        Integer city = 1;
        Integer category = 1;
        when(postDao.findByIdCityAndIdCategory(city, category))
                .thenReturn(Collections.singletonList(mock(Post.class)));
        List<Post> result = myEasyJobService.getPostByCityAndCategory(city, category);
        assertEquals(1, result.size());
    }

    @Test
    public void getCategoriesWithPostTest() {
        Post postMock = mock(Post.class);
        Category category = new Category();
        category.setIdCategory(1);
        when(postDao.findAll())
                .thenReturn(Collections.singletonList(postMock));
        when(postMock.getIdCategory()).thenReturn(1);

        List<Integer> categoryIds = Collections.singletonList(1);
        when(categoryDao.findAllByIdCategoryIn(categoryIds))
                .thenReturn(Collections.singletonList(category));

        when(postDao.countAllByIdCategory(1))
                .thenReturn(3);

        List<Category> result = myEasyJobService.getCategoriesWithPost();
        assertEquals((Integer) 3, result.get(0).getActiveCount());
    }

    @Test
    public void getCitiesWithPostTest() {
        Post postMock = mock(Post.class);
        City city = new City();
        city.setIdCity(1);
        when(postDao.findAll())
                .thenReturn(Collections.singletonList(postMock));
        when(postMock.getIdCity()).thenReturn(1);

        List<Integer> cityIds = Collections.singletonList(1);
        when(cityDao.findAllByIdCityIn(cityIds))
                .thenReturn(Collections.singletonList(city));

        when(postDao.countAllByIdCity(1))
                .thenReturn(3);

        List<City> result = myEasyJobService.getCitiesWithPost();
        assertEquals((Integer) 3, result.get(0).getActiveCount());
    }

    @Test
    public void getRatingViewByUserTest() {
        Rating rating = new Rating();
        rating.setScore(5);
        when(ratingDao.findByIdUser(anyInt())).thenReturn(Collections.singletonList(rating));
        RatingView ratingView = myEasyJobService.getRatingViewByUser(1);
        assertEquals(Double.valueOf(5), ratingView.getScore());
        assertEquals((Integer) 1, ratingView.getQuantity());
    }

    @Test
    public void getPostByCityListAndCategoryListTestScenario1() {
        List<Integer> city = Collections.emptyList();
        List<Integer> category = Collections.emptyList();

        when(postDao.findAll())
                .thenReturn(Collections.singletonList(mock(Post.class)));
        when(cityDao.findById(anyInt()))
                .thenReturn(Optional.of(mock(City.class)));
        when(categoryDao.findById(anyInt()))
                .thenReturn(Optional.of(mock(Category.class)));
        when(appUserDao.findById(anyInt()))
                .thenReturn(Optional.of(mock(AppUser.class)));
        when(ratingDao.findByIdUser(anyInt()))
                .thenReturn(Collections.singletonList(mock(Rating.class)));
        when(postImageDao.findAllByIdPostOrderByPriority(anyInt()))
                .thenReturn(Collections.singletonList(mock(PostImage.class)));

        List<PostCardView> result = myEasyJobService.getPostByCityListAndCategoryList(city, category);
        assertEquals(1, result.size());
    }

    @Test
    public void getPostByCityListAndCategoryListTestScenario2() {
        List<Integer> category = Collections.emptyList();
        List<Integer> city = Collections.singletonList(1);

        when(postDao.findByIdCityIn(city))
                .thenReturn(Collections.singletonList(mock(Post.class)));
        when(cityDao.findById(anyInt()))
                .thenReturn(Optional.of(mock(City.class)));
        when(categoryDao.findById(anyInt()))
                .thenReturn(Optional.of(mock(Category.class)));
        when(appUserDao.findById(anyInt()))
                .thenReturn(Optional.of(mock(AppUser.class)));
        when(ratingDao.findByIdUser(anyInt()))
                .thenReturn(Collections.singletonList(mock(Rating.class)));
        when(postImageDao.findAllByIdPostOrderByPriority(anyInt()))
                .thenReturn(Collections.singletonList(mock(PostImage.class)));

        List<PostCardView> result = myEasyJobService.getPostByCityListAndCategoryList(city, category);
        assertEquals(1, result.size());
    }

    @Test
    public void getPostByCityListAndCategoryListTestScenario3() {
        List<Integer> category = Collections.singletonList(1);
        List<Integer> city = Collections.emptyList();

        when(postDao.findByIdCategoryIn(category))
                .thenReturn(Collections.singletonList(mock(Post.class)));
        when(cityDao.findById(anyInt()))
                .thenReturn(Optional.of(mock(City.class)));
        when(categoryDao.findById(anyInt()))
                .thenReturn(Optional.of(mock(Category.class)));
        when(appUserDao.findById(anyInt()))
                .thenReturn(Optional.of(mock(AppUser.class)));
        when(ratingDao.findByIdUser(anyInt()))
                .thenReturn(Collections.singletonList(mock(Rating.class)));
        when(postImageDao.findAllByIdPostOrderByPriority(anyInt()))
                .thenReturn(Collections.singletonList(mock(PostImage.class)));

        List<PostCardView> result = myEasyJobService.getPostByCityListAndCategoryList(city, category);
        assertEquals(1, result.size());
    }

    @Test
    public void getPostByCityListAndCategoryListTestScenario4() {
        List<Integer> city = Collections.singletonList(1);
        List<Integer> category = Collections.singletonList(1);

        when(postDao.findByIdCityInAndIdCategoryIn(city, category))
                .thenReturn(Collections.singletonList(mock(Post.class)));
        when(cityDao.findById(anyInt()))
                .thenReturn(Optional.of(mock(City.class)));
        when(categoryDao.findById(anyInt()))
                .thenReturn(Optional.of(mock(Category.class)));
        when(appUserDao.findById(anyInt()))
                .thenReturn(Optional.of(mock(AppUser.class)));
        when(ratingDao.findByIdUser(anyInt()))
                .thenReturn(Collections.singletonList(mock(Rating.class)));
        when(postImageDao.findAllByIdPostOrderByPriority(anyInt()))
                .thenReturn(Collections.singletonList(mock(PostImage.class)));

        List<PostCardView> result = myEasyJobService.getPostByCityListAndCategoryList(city, category);
        assertEquals(1, result.size());
    }

    @Test
    public void getPostCardViewByIdTest() {
        when(postDao.findById(anyInt()))
                .thenReturn(Optional.of(mock(Post.class)));
        when(postImageDao.findAllByIdPostOrderByPriority(anyInt()))
                .thenReturn(Collections.singletonList(mock(PostImage.class)));
        when(cityDao.findById(anyInt()))
                .thenReturn(Optional.of(mock(City.class)));
        when(categoryDao.findById(anyInt()))
                .thenReturn(Optional.of(mock(Category.class)));
        when(appUserDao.findById(anyInt()))
                .thenReturn(Optional.of(mock(AppUser.class)));
        when(ratingDao.findByIdUser(anyInt()))
                .thenReturn(Collections.singletonList(mock(Rating.class)));
        when(postImageDao.findAllByIdPostOrderByPriority(anyInt()))
                .thenReturn(Collections.singletonList(mock(PostImage.class)));

        PostCardView result = myEasyJobService.getPostCardViewById(1);

        assertNotNull(result);
        assertEquals(1, result.getPostImageList().size());
    }

    @Test
    public void getUserViewTest() {
        when(appUserDao.findById(anyInt()))
                .thenReturn(Optional.of(mock(AppUser.class)))
                .thenReturn(Optional.of(mock(AppUser.class)));
        when(ratingDao.findByIdUser(anyInt()))
                .thenReturn(Collections.singletonList(mock(Rating.class)));

        Rating rating = new Rating();
        rating.setScore(5);
        rating.setIdQualifierUser(1);
        when(ratingDao.findByIdUser(anyInt())).thenReturn(Collections.singletonList(rating));
        RatingView ratingView = myEasyJobService.getRatingViewByUser(1);
        assertEquals(Double.valueOf(5), ratingView.getScore());
        assertEquals((Integer) 1, ratingView.getQuantity());

        UserView result = myEasyJobService.getUserView(1);
        assertEquals(1, result.getRatingList().size());
        assertEquals((Integer) 5, result.getRatingList().get(0).getScore());
    }

    @Test
    public void getUserByEmailTest() {
        when(appUserDao.findByEmail(any(String.class))).thenReturn(Optional.of(new AppUser()));
        AppUser appUser = myEasyJobService.getUserByEmail(DEFAULT_EMAIL);
        assertNotNull(appUser);
    }

    @Test(expected = MyEasyJobException.class)
    public void getUserByEmailTestThrowException() {
        when(appUserDao.findByEmail(any(String.class))).thenReturn(Optional.empty());
        AppUser appUser = myEasyJobService.getUserByEmail(DEFAULT_EMAIL);
    }

    @Test
    public void isEmailAvailableTest() {
        when(appUserDao.findByEmail(any(String.class))).thenReturn(Optional.of(new AppUser()));
        Boolean result = myEasyJobService.isEmailAvailable(DEFAULT_EMAIL);
        assertFalse(result);
    }

    @Test
    public void isEmailAvailableTestNotExists() {
        when(appUserDao.findByEmail(any(String.class))).thenReturn(Optional.empty());
        Boolean result = myEasyJobService.isEmailAvailable(DEFAULT_EMAIL);
        assertTrue(result);
    }
}
