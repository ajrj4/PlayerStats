<%@page import="bean.Champion"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Campeões</title>
</head>
<body>
	<%
		@SuppressWarnings("unchecked")
		List<Champion> championList = (List<Champion>)request.getAttribute("champions");
	%>
	
	<div>
		<a href="show-mastery">Mostrar minhas maestrias</a>
		<%
			for(int i = 0; i < championList.size(); i++){
		%>
			<p><%=championList.get(i).getChampionName() %></p>
			<img src="data:image/jpg;base64, <%=championList.get(i).getChampionIcon() %>"/>
		<%
			}
		%>
	</div>
</body>
</html>