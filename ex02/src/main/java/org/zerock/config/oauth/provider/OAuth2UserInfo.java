package org.zerock.config.oauth.provider;

// OAuth2.0의 제공자들이 제공하는 공통 속성
//    -> 제공자이름, 제공자ID, 로그인 Email, 이름
public interface OAuth2UserInfo {
  String getProviderId();
  String getProvider();
  String getEmail();
  String getName();
}
