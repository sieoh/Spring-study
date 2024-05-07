package com.humanedu.searchfood.utils;

import org.apache.logging.log4j.util.Strings;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;


@Component
public class StringUtils {
  private int cnt = 0;
  private int cnt2 = 0;

  /** 정규화 유틸 **/
  public static String removeTags(String str) {
    if (Strings.isEmpty(str)) {
        return str;
    }
    return Pattern.compile("<.+?>").matcher(str)
        .replaceAll("");
  }

  /** 스케줄 유틸 **/
  @Scheduled(fixedDelay = 1000)   // 1초마다 실행
  public void scheduleTest1() {
//    System.out.println("1초마다 실행 하는 로그 " + (cnt++));
  }

  // 초(0-59) 분(0-59) 시간(0-23) 일(1-31) 월(1-12) 요일(0-6) (0: 일, 1: 월, 2:화, 3:수, 4:목, 5:금, 6:토)
  @Scheduled(cron = "0/2 * * * * *", zone = "Asia/Seoul")
  public void scheduleTest2() {
//    System.out.println("2초마다 실행 하는 로그 " + (cnt2++));
  }
}
