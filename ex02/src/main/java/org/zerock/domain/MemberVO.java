package org.zerock.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO {
  private int mno;
  private String username;
  private String password;
  private String email;
  private String role;
  private Timestamp createDate;
  private String korName;
  private String provider;    // OAuth 지원 회사
  private String providerId;  // OAuth 지원 회사에서 사용하는 ID

  @Builder
  public MemberVO(String username, String password, String email, String role, Timestamp createDate,
                  String korName, String provider, String providerId) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.role = role;
    this.createDate = createDate;
    this.korName = korName;
    this.provider = provider;
    this.providerId = providerId;
  }
}
