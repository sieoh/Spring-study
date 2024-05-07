package org.zerock.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.zerock.config.oauth.SpringSecOAuth2PrincipalDetailsService;

@Configuration      // spring bean 등록
@EnableWebSecurity //웹보안 활성화를위한 annotation
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // 옛날 코드
@EnableMethodSecurity(securedEnabled = true)   // @Secured, @PreAuthorize, @PostAuthorize 어노테이션 사용해주는 어노테이션(indexController의 어노테이션 사용 가능)
public class SecurityConfig {

  @Autowired  // api 로그인 사용을 위함
  private SpringSecOAuth2PrincipalDetailsService oAuth2PrincipalDetailsService;
  @Bean
  BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean   // 실제 @Configuration을 사용하려면 @Bean에 등록을 해줘야 FilterChain을 사용할 수 있다.
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // method chaining으로 security 설정(spring boot 3.0.9)
    http.csrf().disable()       // 토큰 위조 공격 방어 사용안함
      .authorizeRequests((authorizeHttpRequests) ->
        authorizeHttpRequests
          //.requestMatchers(new AntPathRequestMatcher("/board/**")).authenticated() // /board로 시작하는 url은 인증을 받아야 함
          //.requestMatchers(new AntPathRequestMatcher("/replies/**")).authenticated() // /reply로 시작하는 url은 인증을 받아야 함
          .requestMatchers(
            //new AntPathRequestMatcher("/board/**", "POST"),
            new AntPathRequestMatcher("/board/**"),
            new AntPathRequestMatcher("/replies/**"))
          .authenticated()
          .requestMatchers(
            new AntPathRequestMatcher("/admin/**"))
          //.access("hasRole('ROLE_ADMIN')")   // role에 의한 접근 제어
          .hasRole("ADMIN")
          .requestMatchers(
            new AntPathRequestMatcher("/manager/**"))
          //.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")   // role에 의한 접근 제어
          .hasAnyRole("MANAGER", "ADMIN")
          .anyRequest().permitAll()
      )
//            .formLogin()
//            .loginPage("/member/login")           // form 로그인 페이지 url 설정(get)
//            //.loginProcessingUrl("/login")       // url post(지금은 필요없음)
//            .defaultSuccessUrl("/board/list")     // form 로그인 성공시 이동해야 할 url
//            .failureUrl("/member/login-failure")
//            .and()
      // 요즘 추세는 아래와 같이 람다식으로 작성한다.
      .formLogin(formLogin ->
        formLogin.loginPage("/member/login")
          //.loginProcessingUrl("/login")
          .defaultSuccessUrl("/board/list")
          .failureUrl("/member/login-failure")
      )
      .logout()                           // form 로그아웃(/logout)
      .logoutSuccessUrl("/member/login")  // form 로그아웃 성공시 이동해야 할 url
      .invalidateHttpSession(true)        // 로그아웃 본인 계정 session 초기화
      .and()
      .oauth2Login()                      // API 로그인
      .loginPage("/member/login")         // API 로그인 페이지
      .userInfoEndpoint()
      .userService(oAuth2PrincipalDetailsService)  // 엑세스 토큰 및 사용자 정보 처리하는 서비스 등록
    ;

    return http.build();
  }

}
