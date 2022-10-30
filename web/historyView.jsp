<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>

<%@ page import="dao.WifiDAO" %>
<%@ page import="vo.HistoryVO" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
  WifiDAO dao = new WifiDAO();
  HistoryVO vo = new HistoryVO();
  List<HistoryVO> ls = dao.selectLocation();
  pageContext.setAttribute("ls", ls);
%>
<!DOCTYPE html>
<html>
<head>
  <style>
    #customers {
      font-family: Arial, Helvetica, sans-serif;
      border-collapse: collapse;
      width: 100%;
    }

    #customers td, #customers th {
      border: 1px solid #ddd;
      padding: 8px;
    }

    #customers tr:nth-child(even){background-color: #f2f2f2;}

    #customers tr:hover {background-color: #ddd;}

    #customers th {
      padding-top: 12px;
      padding-bottom: 12px;
      text-align: center;
      background-color: #04AA6D;
      color: white;
    }
  </style>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width", initial-scale="1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
  <title>JSP wifi</title>
</head>
<body>
<div class="row">
  <div class="col-md-12">
    <h2>위치 히스토리 목록</h2>
  </div>
  <div class="col-md-12">
    <div class="ml-4">
      <a href="index.jsp" class="ml-4">홈</a>
      <a href="historyView.jsp" class="ml-4">위치 히스토리목록</a>
      <a href="wifiGet.jsp" class="ml-4">Open API 와이파이 정보 가져오기</a><br>
    </div>
  </div>
</div>
<div class="row">
  <table id="customers" class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
    <thead>
    <tr>
      <th class="bg-success" style="text-align:center;">ID</th>
      <th class="bg-success" style="text-align:center;">X좌표</th>
      <th class="bg-success" style="text-align:center;">Y좌표</th>
      <th class="bg-success" style="text-align:center;">조회일자</th>
      <th class="bg-success" style="text-align:center;">비고</th>
    </tr>
    </thead>
    <tbody>

      <c:forEach var="history" items="${ls}">
        <tr>
          <td>${history.id}</td>
          <td>${history.lat}</td>
          <td>${history.lnt}</td>
          <td>${history.date}</td>
          <td>
            <form action="historyDelete.jsp?id=${history.id}" method="post">
              <button class="btn btn-primary" type="submit" >삭제</button>
            </form>
          </td>
        </tr>
      </c:forEach>

    </tbody>
  </table>
</div>

<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
</body>
</html>
