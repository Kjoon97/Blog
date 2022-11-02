package com.kang.blog.config.oauth.provider;

public interface OAuth2UserInfo {
    String getProviderId();     //facebook,google,등 OAuth 회원의 Primary key
    String getProvider();       //facebook, google, 등 OAuth 명
    String getEmail();
    String getName();
}
