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
        System.out.println("성공");
    } else {
        System.out.println("실패");
    }

    System.out.println(result);

%>

<div class="col-md-12" style="text-align: center;">
    <h2>WIFI 정보를 정상적으로 저장하였습니다.</h2>
    <div>
        <a href="index.jsp" style="text-align: center">홈 으로 가기</a>
    </div>
</div>

</body>


</html>