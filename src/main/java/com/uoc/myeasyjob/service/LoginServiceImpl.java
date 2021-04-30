package com.uoc.myeasyjob.service;

import com.uoc.myeasyjob.dao.AppUserDao;
import com.uoc.myeasyjob.domain.AppUser;
import com.uoc.myeasyjob.exception.MyEasyJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.uoc.myeasyjob.util.ErrorConstants.INTERNAL_ERROR;
import static com.uoc.myeasyjob.util.ErrorConstants.REGISTER_USER_ERROR_CODE;
import static com.uoc.myeasyjob.util.ErrorConstants.REGISTER_USER_ERROR_MESSAGE;

/**
 * Implementation of the LoginServicethat exposes all the services that manage the authentication.
 */
@Service
public class LoginServiceImpl implements LoginService {

    public static final String USER = "USER";
    public static final String USER_NOT_FOUND = "User not found.";

    private final AppUserDao appUserDao;

    private final PasswordEncoder passwordEncoder;

    public LoginServiceImpl(AppUserDao appUserDao, PasswordEncoder passwordEncoder) {
        this.appUserDao = appUserDao;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Find and retrieve the user details using the email parameter.
     *
     * @param s Username in this case the email.
     * @return {@link UserDetails}
     * @throws UsernameNotFoundException when the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Optional<AppUser> userOptional = appUserDao.findByEmail(s);

        UserBuilder builder;
        if (userOptional.isPresent()) {
            AppUser appUser = userOptional.orElse(null);
            builder = org.springframework.security.core.userdetails.User.withUsername(appUser.getEmail());
            builder.password(appUser.getPassword());
            builder.roles(USER);
        } else {
            throw new UsernameNotFoundException(USER_NOT_FOUND);
        }
        return builder.build();
    }

    /**
     * Register a new user.
     *
     * @param appUser new user.
     */
    @Override
    public void signup(AppUser appUser) {
        try {
            appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
            appUserDao.save(appUser);
        } catch (Exception e){
            throw new MyEasyJobException(
                    REGISTER_USER_ERROR_CODE,
                    REGISTER_USER_ERROR_MESSAGE,
                    e.getMessage(),
                    INTERNAL_ERROR
                    );
        }
    }

}
