package org.zerock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zerock.config.auth.SpringSecPrincipalDetails;
import org.zerock.domain.MemberVO;
import org.zerock.service.MemberService;

@Controller
@RequestMapping("/member")
public class IndexController {
  @Autowired
  private MemberService memberService;

  /**
   * Description: 회원가입 페이지
   * @return 회원가입 view page
   */
  @GetMapping("/join")
  public String join() {
    return "member/join";
  }

  /**
   * Desc: 실제 회원가입 프로세스
   * @return 로그인 redirect page
   */
  @PostMapping("/joinProc")
  public String joinProc(MemberVO memberVO) {
    System.out.println("회원가입 진행: " + memberVO);

    memberService.putMember(memberVO);

    return "redirect:/member/login";
  }

  /**
   * Desc: 로그인 페이지
   * @return 로그인 view page
   */
  @GetMapping("/login")
  public String login() {
    return "member/login";
  }

  @Secured("ROLE_ADMIN")  // @EnableMethodSecurity(securedEnabled = true) or @EnableGlobalMethodSecurity(securedEnabled = true)이 설정되어야 적용됨
  @GetMapping("/info")
  @ResponseBody   // @Controller로 선언된 클래스의 메소드에서 response로 json값을 받을 때 사용
  public String info() {
    return "멤버정보";
  }

  @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  // @PreAuthorize("isAuthenticated()")   // 로그인 인증을 받았는 경우에만 실행
  @GetMapping("/data")
  @ResponseBody   // @Controller로 선언된 클래스의 메소드에서 response로 json값을 받을 때 사용
  public String data() {
    return "데이터정보";
  }

  @GetMapping("/login-failure")
  public String loginFail() {
    return "member/login-fail";
  }

  /**
   * Desc: 로그인한 하지 않은 유저이면 authentication null
   *       로그인한 유저이면 authentication 유저에 대한 정보가 저장된 객체가 만들어짐
   *       테스트용
   * @param authentication
   * @param principalDetails
   * @return
   */
  @GetMapping("/test1")       // GET -> /member/test1
  @ResponseBody
  public String test1Login(
    Authentication authentication
    , @AuthenticationPrincipal SpringSecPrincipalDetails principalDetails
  ) {  // DI주입을 했으므로 사용이 가능
    SpringSecPrincipalDetails springSecPrincipalDetails
      = (SpringSecPrincipalDetails) authentication.getPrincipal();
    System.out.println("로그인한 유저 정보(authentication): " + springSecPrincipalDetails.getMemberVO());
    System.out.println("로그인한 유저 정보(SpringSecPrincipalDetails): " + principalDetails.getMemberVO());

    return "세션정보 확인";
  }

  /**
   * Desc: OAuth로그인한 정보 확인 용도 테스트
   * @param authentication
   * @param oAuth2User
   * @return
   */
  @GetMapping("/oauth2/test1")       // GET -> /member/oauth2/test1
  @ResponseBody
  public String oauth2Test1Login(
    Authentication authentication
    , @AuthenticationPrincipal OAuth2User oAuth2User
  ) {  // DI주입을 했으므로 사용이 가능
    OAuth2User oAuth2UserCopy
      = (OAuth2User) authentication.getPrincipal();
    System.out.println("OAuth2 로그인한 유저 정보(authentication): " + oAuth2UserCopy.getAttributes());
    System.out.println("OAuth2 로그인한 유저 정보(oAuth2User): " + oAuth2User.getAttributes());

    return "OAuth2 세션정보 확인";
  }

  @GetMapping("/user")
  @ResponseBody
  public String user(@AuthenticationPrincipal SpringSecPrincipalDetails ssPrincipalDetails) {
    System.out.println("SpringSecPrincipalDetails: "+ ssPrincipalDetails.getMemberVO());
    return "member - user";
  }
}
