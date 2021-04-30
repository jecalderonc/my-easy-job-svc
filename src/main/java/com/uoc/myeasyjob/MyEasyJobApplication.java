package com.uoc.myeasyjob;

import com.uoc.myeasyjob.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Main configuration class of the Springboot application.
 */
@SpringBootApplication
@EnableWebSecurity
public class MyEasyJobApplication extends WebSecurityConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(MyEasyJobApplication.class, args);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LoginService loginService;

    /**
     * Authentication manager.
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Custom login service.
     *
     */
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return loginService;
    }

    /**
     * Configure the custom user detail service and encoder of the password.
     *
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder);
    }

    /**
     * Disable the cors interceptor.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
    }

}
