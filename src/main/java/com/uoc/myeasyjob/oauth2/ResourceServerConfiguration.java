package com.uoc.myeasyjob.oauth2;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.stereotype.Controller;

/**
 * Configuration class of the scopes of the security and authentication of the service.
 */
@EnableResourceServer
@Controller
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final String MY_EASY_JOB_ACCOUNT = "/my-easy-job/account/**";
    private static final String OAUTH_TOKEN = "/oauth/token";
    private static final String OAUTH_AUTHORIZE = "/oauth/authorize**";
    private static final String USER = "'USER'";
    private static final String MY_EASY_JOB = "/my-easy-job/**";

    /**
     * Configuration of the paths and scopes of security.
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(OAUTH_TOKEN, OAUTH_AUTHORIZE).permitAll();
        http.requestMatchers()
                .antMatchers(MY_EASY_JOB_ACCOUNT)
                .and().authorizeRequests()
                .antMatchers(MY_EASY_JOB_ACCOUNT).access("hasRole(" + USER + ")")
                .and()
                .requestMatchers()
                .antMatchers(MY_EASY_JOB)
                .and().authorizeRequests()
                .antMatchers(MY_EASY_JOB)
                .permitAll();
    }
}
