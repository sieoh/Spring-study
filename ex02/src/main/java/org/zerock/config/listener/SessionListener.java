package org.zerock.config.listener;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionListener implements HttpSessionListener {
  //@Value("${server.servlet.session.timeout}")
  private int sessionTime = 10; // 단위는 초

  public void sessionCreated(HttpSessionEvent se) {
    se.getSession().setMaxInactiveInterval(sessionTime);
  }

  public void sessionDestroyed(HttpSessionEvent se) {

  }
}

