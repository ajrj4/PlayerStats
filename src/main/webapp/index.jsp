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
<title>Home</title>
<link rel="stylesheet" type="text/css" href="css/index.css"/>
</head>
<body>
	<%
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		Usuario usuario = usuarioDAO.selectUsuarioByName(String.valueOf(session.getAttribute("usuario")));
		SummonerDAO summonerDAO = new SummonerDAO();
		Summoner summoner = summonerDAO.selectSummonerById(usuario.getSummonerId());
	%>
	<header>
		<nav class="navHeader">
			<a href="logout">Sair</a>
		</nav>
	</header>
	<section class="flexbox">
		<h1>Página Inicial</h1>
		<div class="container">
			<h1><%=summoner.getSummonerName() %></h1>
			<h2>Nível: <%=summoner.getSummonerLevel() %></h2>
			<nav class="links">
				<a href="update-summoner">Atualizar dados</a>
				<a href="select-champions">Listar Campeões</a>
				<a href="select-partidas">Partidas</a>
				<a href="delete-usuario">Excluir conta</a>
			</nav>
		</div>
	</section>
</body>
</html>