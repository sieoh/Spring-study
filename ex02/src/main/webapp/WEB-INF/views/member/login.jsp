<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>로그인</title>
</head>
<body>
  <h1>Custom Login Page</h1>
  <h2><c:out value="${error}"/></h2>
  <h2><c:out value="${logout}"/></h2>

  <!--
  <form method='post' action="/loginProc">
  -->
  <form method='post' action="/member/login">
      <div>
        <input type='text' name='username' value='' autocomplete="off">
      </div>
      <div>
        <input type='password' name='password' value=''>
      </div>
      <!--
      <div>
        <input type='checkbox' name='remember-me'> Remember Me
      </div>
      -->
      <div>
        <input type='submit' value="로그인">
        &nbsp;|&nbsp;
        <a href="/oauth2/authorization/google">구글 로그인</a>
        &nbsp;|&nbsp;
        <a href="/oauth2/authorization/naver">네이버 로그인</a>
        &nbsp;|&nbsp;
        <a href="/oauth2/authorization/kakao">카카오 로그인</a>
      </div>
      <div>
        <a href="/member/join">회원가입</a>
      </div>
  </form>
</body>
</html>
