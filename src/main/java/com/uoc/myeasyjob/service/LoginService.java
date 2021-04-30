package com.uoc.myeasyjob.service;

import com.uoc.myeasyjob.domain.AppUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Service interface that exposes all the services that manage the authentication.
 */
public interface LoginService extends UserDetailsService {

    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;

    void signup(AppUser appUser);
}
