<%@ page import="com.seeweed.spring_mvc_start.domain.member.MemberRepository" %>
<%@ page import="com.seeweed.spring_mvc_start.domain.member.Member" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--🔧 1. JSP는 내부적으로 서블릿으로 변환됨

JSP 파일은 실행될 때 다음과 같은 과정을 거칩니다:
1.	JSP → 서블릿(Java 파일) 로 변환됨
2.	그 서블릿이 컴파일되어 .class 파일로 만들어짐
3.	톰캣 등의 컨테이너가 이 .class 파일을 실행

즉, JSP는 결국 서블릿이기 때문에 서블릿에서 사용 가능한 객체는 JSP에서도 사용할 수 있습니다.--%>

<%
    // request, response 사용 가능
    MemberRepository memberRepository = MemberRepository.getInstance();

    System.out.println("save.jsp");


    String username = request.getParameter("username");
    int age = Integer.parseInt(request.getParameter("age"));
    Member member = new Member(username, age);
    System.out.println("member = " + member);
    memberRepository.save(member);
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>saveJSP</title>
</head>
<body>
save 성공!!
<ul>
    <li>id=<%=member.getId()%>
    </li>
    <li>username=<%=member.getUsername()%>
    </li>
    <li>age=<%=member.getAge()%>
    </li>
</ul>
<a href="/index.html">메인</a>
</body>
</html>
