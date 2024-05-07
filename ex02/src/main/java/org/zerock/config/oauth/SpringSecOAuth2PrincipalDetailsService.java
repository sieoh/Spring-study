package org.zerock.config.oauth;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.zerock.config.auth.SpringSecPrincipalDetails;
import org.zerock.config.oauth.provider.GoogleUserInfo;
import org.zerock.config.oauth.provider.KakaoUserInfo;
import org.zerock.config.oauth.provider.NaverUserInfo;
import org.zerock.config.oauth.provider.OAuth2UserInfo;
import org.zerock.domain.MemberVO;
import org.zerock.mapper.MemberMapper;

import java.util.Map;

@Service    // Service 어노테이션 추가
public class SpringSecOAuth2PrincipalDetailsService extends DefaultOAuth2UserService {
  // DefaultOAuth2UserService 상속(extends) 받아서 사용한다.

  @Autowired
  private MemberMapper memberMapper;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    System.out.println("userRequest: "+ userRequest);

    OAuth2User oAuth2User = super.loadUser(userRequest);

    System.out.println("getClientRegistration: " + userRequest.getClientRegistration());
    System.out.println("getAccessToken: " + userRequest.getAccessToken().getTokenValue());
    System.out.println("getAttributes: " + oAuth2User.getAttributes());

    OAuth2UserInfo oAuth2UserInfo = null;
    if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
      System.out.println("구글 로그인 요청");
      oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
    } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
      System.out.println("네이버 로그인 요청");
      oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
    } else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")){
      System.out.println("카카오 로그인 요청");
      oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
    } else {
      System.out.println("구글, 네이버, 카카오 SNS 로그인만 지원합니다");
      return null;
    }

    // 정보
    String provider = oAuth2UserInfo.getProvider();    // google, facebook, naver, kakao ... 등
    String providerId = oAuth2UserInfo.getProviderId();
//    String family_name = oAuth2User.getAttribute("family_name");
//    String given_name = oAuth2User.getAttribute("given_name");
//    String korname = family_name + given_name;
    String email = oAuth2UserInfo.getEmail();
    String korname = oAuth2UserInfo.getName();
    String username = provider + "_" + providerId;
    String password = "-";
    String role = "ROLE_USER";

    // 실제 회원가입된 유저인지 확인
    MemberVO memberVO = memberMapper.selectByUserName(username);
    if (memberVO == null) {   // OAuth2 회원가입이 안된 경우
      // 회원가입 시도
      memberVO = MemberVO.builder()
              .korName(korname)
              .provider(provider)
              .providerId(providerId)
              .username(username)
              .password(password)
              .email(email)
              .role(role)
              .build();
      memberMapper.insertMember(memberVO);
    } else {                  // OAuth2 회원가입이 된 경우

    }

    return new SpringSecPrincipalDetails(memberVO, oAuth2User.getAttributes());
  }
}