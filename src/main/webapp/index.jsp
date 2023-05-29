<%@page import="model.UsuarioDAO"%>
<%@page import="model.SummonerDAO"%>
<%@page import="bean.Summoner"%>
<%@page import="bean.Usuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		Usuario usuario = usuarioDAO.selectUsuarioByName(String.valueOf(session.getAttribute("usuario")));
		SummonerDAO summonerDAO = new SummonerDAO();
		Summoner summoner = summonerDAO.selectSummonerById(usuario.getSummonerId());
	%>
	<header>
		<a href="select-champions">Atualizar dados</a>
		<a href="select-champions">Campeões</a>
		<a href="cadastrarChampion.jsp">Cadastrar Campeões</a>
		<a href="select-partidas">Partidas</a>
		<a href="delete-usuario">Excluir conta</a>
		<a href="logout">Sair</a>
	</header>
	<h1><%=summoner.getSummonerName() %></h1>
	<h2><%=summoner.getSummonerLevel() %></h2>
</body>
</html>