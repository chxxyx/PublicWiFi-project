<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import ="dao.WifiDAO" %>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>
<%
    WifiDAO wifiDAO = new WifiDAO();
    int result = wifiDAO.getData();

    if(result!=-1){
        System.out.println("ㅇ성공");
    } else {
        System.out.println("실패");
    }

    System.out.println(result);

%>
</body>
</html>