package com.uoc.myeasyjob.rest;

import com.uoc.myeasyjob.domain.AppUser;
import com.uoc.myeasyjob.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.uoc.myeasyjob.util.Constants.LOGIN_PATH;
import static com.uoc.myeasyjob.util.Constants.MY_EASY_JOB;

@RestController
@CrossOrigin
@RequestMapping("/" + MY_EASY_JOB + "/" + LOGIN_PATH)
public class LoginControllerImpl implements LoginController {

    @Autowired
    LoginService loginService;

    @Override
    public void signup(AppUser appUser) {
        loginService.signup(appUser);
    }
}
