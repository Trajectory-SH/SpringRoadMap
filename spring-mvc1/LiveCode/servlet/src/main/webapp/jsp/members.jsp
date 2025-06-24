<%@ page import="hello.servlet.domain.member.MemberRepository" %>
<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: kronos0697
  Date: 2025. 6. 23.
  Time: 오후 12:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  MemberRepository memberRepository = MemberRepository.getInstance();
  List<Member> members = memberRepository.findAll();
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="/index.html">메인</a>
<table>
  <thead>
  <th>id</th>
  <th>username</th>
  <th>age</th>
  </thead>
  <tbody>
  <%//여기서 out은 직접 선언하지 않아도 자동으로 사용할 수 있는 내장 객체이다.
    //javax.servlet.jsp.JspWriter 타입의 객체 -> 브라우저로 출력보내는 스트림 역할을 수행한다.
    //Jsp가 서블릿으로 변환될 때 다음과 같은 서블릿 코드가 자동 생성된다.
    /*JspWriter out = _jspx_page_context.getOut();*/
    for (Member member : members) {
      out.write(" <tr>");
      out.write(" <td>"+member.getId()+"</td>");
      out.write(" <td>"+member.getUsername()+"</td>");
      out.write(" <td>"+member.getAge()+"</td>");
      out.write(" </tr>");
    }
  %>

  </tbody>
</table>

</body>
</html>
