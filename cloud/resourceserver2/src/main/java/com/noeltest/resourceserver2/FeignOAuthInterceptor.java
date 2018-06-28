package com.noeltest.resourceserver2;

import org.springframework.security.oauth2.client.OAuth2ClientContext;

import feign.RequestInterceptor;
import feign.RequestTemplate;

// https://jmnarloch.wordpress.com/2015/10/14/spring-cloud-feign-oauth2-authentication/
public class FeignOAuthInterceptor implements RequestInterceptor {
    private static final String BEARER = "Bearer";
    private static final String AUTHORIZATION = "Authorization";
    private OAuth2ClientContext context;
    
    public FeignOAuthInterceptor(OAuth2ClientContext context) {
        super();
        this.context = context;
    }

    @Override
    public void apply(RequestTemplate restTemplate) {
        // there is an auth header here already
        if(restTemplate.headers().get(AUTHORIZATION) != null) {
            return;
        }
        
        // no token, should handle this as an error
        if(context.getAccessTokenRequest().getExistingToken() == null) {
            return;
        }
        
        // set the token : Authorization : Bearer TOKEN
        restTemplate.header(AUTHORIZATION, String.format("%s %s", BEARER,
                        context.getAccessTokenRequest().getExistingToken().toString()));
    }
    
}
