package org.zerock.service;

import org.zerock.domain.MemberVO;

public interface MemberService {

  // 회원가입
  int putMember(MemberVO memberVO);
}
