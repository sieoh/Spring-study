package org.zerock.service;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.domain.MemberVO;
import org.zerock.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {

  @Autowired
  private MemberMapper memberMapper;
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public int putMember(MemberVO memberVO) {
    memberVO.setRole("ROLE_USER");
    String rawPw = memberVO.getPassword();
    String encryptPw = bCryptPasswordEncoder.encode(rawPw);
    memberVO.setPassword(encryptPw);
    memberVO.setProvider(Strings.EMPTY);
    memberVO.setProviderId(Strings.EMPTY);

    return memberMapper.insertMember(memberVO);
  }
}
