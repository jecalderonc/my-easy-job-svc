package com.uoc.myeasyjob.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * Configuration class of the security server of the service.
 */
@Configuration
@EnableAuthorizationServer
public class AppAuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    private static final String APP_USER = "MyEasyJob";
    private static final String APP_SECRET = "Secret123";
    private static final String APP_GRAND_TYPE = "password";
    private static final String USER = "USER";
    private static final String READ = "read";
    private static final String WRITE = "write";

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenStore tokenStore;

    /**
     * Main configuration of the security server, client, grand type, authorities and more.
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(APP_USER)
                .authorizedGrantTypes(APP_GRAND_TYPE)
                .authorities(USER)
                .scopes(READ, WRITE)
                .autoApprove(true)
                .secret(passwordEncoder().encode(APP_SECRET));
    }

    /**
     * Encryption method to the passwords.
     *
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configuration of the Authentication manager and token store of the security server.
     *
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore);
    }

    /**
     * TokenStore where we will keep the token generated, we will use a InMemory option.
     *
     */
    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }
}
