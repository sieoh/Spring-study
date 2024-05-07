package org.zerock.config.oauth.provider;

import java.util.Map;

public class KakaoUserInfo  implements OAuth2UserInfo {

  private Map<String, Object> attrs;
  private Map<String, Object> kakaoAccountAttrs;
  private Map<String, Object> profileAttrs;

  public KakaoUserInfo(Map<String, Object> attrs){
    this.attrs = attrs;
    this.kakaoAccountAttrs = (Map<String, Object>) attrs.get("kakao_account");
    this.profileAttrs = (Map<String, Object>) kakaoAccountAttrs.get("profile");
  }

  @Override
  public String getProviderId() {
    return attrs.get("id").toString();
  }

  @Override
  public String getProvider() {
    return "kakao";
  }

  @Override
  public String getName() {
    return profileAttrs.get("nickname").toString();
  }

  @Override
  public String getEmail() {
    return kakaoAccountAttrs.get("email").toString();
  }
}
