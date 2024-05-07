package org.zerock.config.oauth.provider;

import java.util.Map;

public class GoogleUserInfo implements OAuth2UserInfo {

  private Map<String, Object> attrs;
  public GoogleUserInfo(Map<String, Object> attrs) {
    this.attrs = attrs;
  }

  @Override
  public String getProviderId() {
    return (String)attrs.get("sub");
  }

  @Override
  public String getProvider() {
    return "google";
  }

  @Override
  public String getEmail() {
    return (String)attrs.get("email");
  }

  @Override
  public String getName() {
    return (String)attrs.get("name");
  }
}
