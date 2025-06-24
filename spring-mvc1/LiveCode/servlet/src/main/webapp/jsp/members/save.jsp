<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page import="hello.servlet.domain.member.MemberRepository" %><%--
  Created by IntelliJ IDEA.
  User: kronos0697
  Date: 2025. 6. 23.
  Time: 오후 12:24
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%//request, response는 그냥 사용이 가능하다.-> 우리 눈에는 안보이지만 jsp도 결국 자동으로 servlet으로 바뀐다.
  MemberRepository memberRepository = MemberRepository.getInstance();

  System.out.println("MemberSaveServlet.service");
  String username = request.getParameter("username");
  int age = Integer.parseInt(request.getParameter("age"));//getParameter의 return값은 항상 문자열이 나온다.

  Member member = new Member(username, age);
  memberRepository.save(member);

%>
<html>
<head>
    <title>Title</title>
</head>
<body>
성공
<ul>
  <li>id = <%=member.getId()%></li>
  <li>username = <%=member.getUsername()%></li>
  <li>age = <%=member.getAge()%></li>
</ul>
<a href="/index.html">메인</a>
</body>
</html>



<%--
<%= %>
Java코드를 그냥 화면에 출력해주는 분법이다.
--%>