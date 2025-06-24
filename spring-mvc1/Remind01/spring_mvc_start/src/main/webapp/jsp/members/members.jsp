<%@ page import="com.seeweed.spring_mvc_start.domain.member.Member" %>
<%@ page import="com.seeweed.spring_mvc_start.domain.member.MemberRepository" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: kronos0697
  Date: 2025. 6. 24.
  Time: 오후 10:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    MemberRepository memberRepository = MemberRepository.getInstance();
    List<Member> members = memberRepository.findAll();
%>
<html>
<head>
    <meta charset="UTF-8">
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
    <%--  정적인 Html 코드 속에서 동적으로 변환이 필요한 부분만 자바를 통해서 작성한다--%>



    <%
        for (Member member : members) {
            out.write(" <tr>");
            out.write("<td>" + member.getId() + "</td>");
            out.write("<td>" + member.getUsername() + "</td>");
            out.write("<td>" + member.getAge() + "</td>");
            out.write(" </tr>");
        }
    %>

    </tbody>
</table>
</body>
</html>
