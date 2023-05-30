<%@page import="bean.Champion"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Campeões</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="css/listarChampions.css">
</head>
<body>
	<%
		if(request.getAttribute("champions") == null){
			response.sendRedirect("select-champions");
			return;
		}
	
		@SuppressWarnings("unchecked")
		List<Champion> championList = (List<Champion>)request.getAttribute("champions");
		int nivelUsuario = (int) session.getAttribute("nivelUsuario");
	%>
	<header>
		<nav>
			<a href="main">Home</a>
			<ul class="links">
				<li><a href="cadastrarChampion.jsp">Cadastrar Campeões</a></li>
				<li><a href="show-mastery">Mostrar Minhas Maestrias</a></li>
				<li><a href="logout">Sair</a></li>
			</ul>
		</nav>
	</header>
	
	<section class="flexbox">
		<div class="container">	
			<%
			for(int i = 0; i < championList.size(); i++){
			%>
			<div class="champion">
				<h3><%=championList.get(i).getChampionName() %></h3>
				<img src="data:image/jpg;base64, <%=championList.get(i).getChampionIcon() %>"/>
				
				<%if(nivelUsuario == 1){ %>
					<a href="delete-champion?id=<%=championList.get(i).getChampionId()%>">Excluir</a>
				<%	
				}
				%>
			</div>
			<%	
			}
			%>
		</div>
	</section>
</body>
</html>