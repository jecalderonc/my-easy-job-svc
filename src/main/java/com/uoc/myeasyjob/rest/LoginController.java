package com.uoc.myeasyjob.rest;

import com.uoc.myeasyjob.domain.AppUser;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface LoginController {

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    void signup(@RequestBody AppUser appUser);
}
