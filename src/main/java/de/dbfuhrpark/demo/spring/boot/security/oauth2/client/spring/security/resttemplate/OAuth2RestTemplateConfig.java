package de.dbfuhrpark.demo.spring.boot.security.oauth2.client.spring.security.resttemplate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
//no @EnableWebSecurity necessary
public class OAuth2RestTemplateConfig extends WebSecurityConfigurerAdapter {

//    @Value("${security.oauth2.client.username}")
//    private String clientUser;
//    @Value("${security.oauth2.client.password}")
//    private String clientPassword;
    @Value("${security.oauth2.client.id}")
    private String clientId;

    @Value("${security.oauth2.client.secret}")
    private String clientSecret;

    @Value("${security.oauth2.client.accessTokenUri}")
    private String accessTokenUri;

    @Value("${security.oauth2.client.userAuthorizationUri}")
    private String userAuthorizationUri;


    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers("/**");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        super.configure(http);
    }

// this is for auth with client credentials as a specific client
    @Bean
    public OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails() {
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        details.setClientId(clientId);
        details.setClientSecret(clientSecret);
        details.setAccessTokenUri(accessTokenUri);
        return details;
    }

//    // this is for auth with user  credentials as a user of a specific client
//    @Bean
//    public OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails() {
//        ResourceOwnerPasswordResourceDetails details = new ResourceOwnerPasswordResourceDetails();
//        details.setPassword(clientPassword);
//        details.setUsername(clientUser);
//        details.setClientId(clientId);
//        details.setClientSecret(clientSecret);
//        details.setAccessTokenUri(accessTokenUri);
//        return details;
//    }

    @Bean
    public OAuth2RestTemplate createRestTemplate(OAuth2ClientContext clientContext) {
        return new OAuth2RestTemplate(oAuth2ProtectedResourceDetails(), clientContext);
    }
}