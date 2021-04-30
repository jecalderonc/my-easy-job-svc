package com.uoc.myeasyjob.service;

import com.uoc.myeasyjob.dao.AppUserDao;
import com.uoc.myeasyjob.domain.AppUser;
import com.uoc.myeasyjob.exception.MyEasyJobException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class LoginServiceImplTest {

    private static final String DEFAULT_EMAIL = "correo@correo.com";
    private static final String DEFAULT_PASSWORD = "12345";

    @Mock
    private AppUserDao appUserDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    private LoginServiceImpl loginService;

    @Before
    public void setUp() {
        loginService = new LoginServiceImpl(
                appUserDao,
                passwordEncoder);
    }

    @Test
    public void loadUserByUsernameTest() {
        AppUser appUser = new AppUser();
        appUser.setEmail(DEFAULT_EMAIL);
        appUser.setPassword(DEFAULT_PASSWORD);

        when(appUserDao.findByEmail(DEFAULT_EMAIL)).thenReturn(Optional.of(appUser));

        UserDetails userDetails = loginService.loadUserByUsername(DEFAULT_EMAIL);
        assertNotNull(userDetails);
        assertEquals(DEFAULT_PASSWORD, userDetails.getPassword());
    }

    @Test
    public void signupTest() {
        AppUser appUser = new AppUser();
        appUser.setEmail(DEFAULT_EMAIL);
        appUser.setPassword(DEFAULT_PASSWORD);

        when(passwordEncoder.encode(DEFAULT_EMAIL)).thenReturn("Enconded pass");
        when(appUserDao.save(any(AppUser.class))).thenReturn(new AppUser());
        loginService.signup(appUser);
    }

    @Test(expected = MyEasyJobException.class)
    public void signupTestThrownException() {
        AppUser appUser = new AppUser();
        appUser.setEmail(DEFAULT_EMAIL);
        appUser.setPassword(DEFAULT_PASSWORD);

        when(passwordEncoder.encode(DEFAULT_EMAIL)).thenReturn("Enconded pass");
        when(appUserDao.save(any(AppUser.class))).thenThrow(RuntimeException.class);
        loginService.signup(appUser);
    }
}
