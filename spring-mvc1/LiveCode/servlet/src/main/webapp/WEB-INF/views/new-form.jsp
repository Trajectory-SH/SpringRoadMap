<%--
  Created by IntelliJ IDEA.
  User: kronos0697
  Date: 2025. 6. 23.
  Time: 오후 2:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<!-- save : 상대경로를 사용한다. 현재 디렉토리에서 하나만 더 /save라고 붙는다. -->
<form action="save" method="post">
    username: <input type="text" name="username" />
    age: <input type="text" name="age" />
    <button type="submit">전송</button>
</form>

</body>
</html>
