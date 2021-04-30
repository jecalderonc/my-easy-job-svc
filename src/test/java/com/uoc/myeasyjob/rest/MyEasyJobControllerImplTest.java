package com.uoc.myeasyjob.rest;

import com.uoc.myeasyjob.domain.AppUser;
import com.uoc.myeasyjob.domain.Post;
import com.uoc.myeasyjob.exception.MyEasyJobException;
import com.uoc.myeasyjob.rest.model.PostCardView;
import com.uoc.myeasyjob.service.MyEasyJobService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class MyEasyJobControllerImplTest {

    @Mock
    private MyEasyJobService myEasyJobService;

    private MyEasyJobControllerImpl myEasyJobController;

    @Before
    public void setUp() {
        myEasyJobController = new MyEasyJobControllerImpl(myEasyJobService);
    }

    @Test
    public void saveUserTest() {
        MultipartFile multipartFile = mock(MultipartFile.class);
        String appUser = "{\"idAppUser\":1,\"name\":\"nombre\",\"email\":\"email@email.com\"}";

        when(myEasyJobService.saveUser(any(MultipartFile.class), any(AppUser.class))).thenReturn(new AppUser());
        myEasyJobController.saveUser(multipartFile, appUser);
    }

    @Test(expected = MyEasyJobException.class)
    public void saveUserTestThrowException() {
        MultipartFile multipartFile = mock(MultipartFile.class);
        String appUser = "{\"idUser\":1,\"name\":\"nombre\",\"email\":\"email@email.com\"}";

        when(myEasyJobService.saveUser(any(MultipartFile.class), any(AppUser.class))).thenReturn(new AppUser());
        myEasyJobController.saveUser(multipartFile, appUser);
    }

    @Test
    public void savePostTest() {
        MultipartFile multipartFile = mock(MultipartFile.class);
        MultipartFile[] multipartFiles = {multipartFile};
        String appUser = "{\"idPost\":1,\"title\":\"titulo\",\"description\":\"test description\"}";

        when(myEasyJobService.savePost(any(), any(Post.class))).thenReturn(new Post());
        myEasyJobController.savePost(multipartFiles, appUser);
    }

    @Test(expected = MyEasyJobException.class)
    public void savePostTestThrowException() {
        MultipartFile multipartFile = mock(MultipartFile.class);
        MultipartFile[] multipartFiles = {multipartFile};
        String appUser = "{\"id_Post\":1,\"title\":\"titulo\",\"description\":\"test description\"}";

        when(myEasyJobService.savePost(any(), any(Post.class))).thenReturn(new Post());
        myEasyJobController.savePost(multipartFiles, appUser);
    }

    @Test
    public void getPostByCityListAndCategoryListTest() {
        String idsCity = "1;2";
        String idsCategory = "2;3";
        List<Integer> cities = Arrays.asList(1,2);
        List<Integer> categories = Arrays.asList(2,3);
        when(myEasyJobService.getPostByCityListAndCategoryList(cities, categories)).thenReturn(Collections.singletonList(mock(PostCardView.class)));
        myEasyJobController.getPostByCityListAndCategoryList(idsCity, idsCategory);
    }

    @Test(expected = MyEasyJobException.class)
    public void getPostByCityListAndCategoryListTestThrowExceptionByCities() {
        String idsCity = "1;2;q";
        String idsCategory = "2;3";
        myEasyJobController.getPostByCityListAndCategoryList(idsCity, idsCategory);
    }

    @Test(expected = MyEasyJobException.class)
    public void getPostByCityListAndCategoryListTestThrowExceptionByCategories() {
        String idsCity = "1;2";
        String idsCategory = "2;3;a";
        myEasyJobController.getPostByCityListAndCategoryList(idsCity, idsCategory);
    }
}
