<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ page import="dao.WifiDAO" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="dao" class="dao.WifiDAO"/>

<%
   int id = Integer.parseInt(request.getParameter("id"));
   dao.deleteLocation(id);
%>
<c:redirect url="${pageContext.request.contextPath}/historyView.jsp"></c:redirect>