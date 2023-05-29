<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.time.Instant"%>
<%@page import="java.util.Date"%>
<%@page import="model.ChampionDAO"%>
<%@page import="com.google.gson.JsonArray"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.google.gson.JsonObject"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Histórico de Partidas</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="css/partida.css">
</head>
<body>
	<%
		if(request.getAttribute("pagina") == null){
			response.sendRedirect("select-partidas");
			return;
		}
	
		String puuid = String.valueOf(session.getAttribute("puuid"));
		int pagina = (int)request.getAttribute("pagina");
		String partidaJson = String.valueOf(request.getAttribute("partida"));
		
		int maxPaginas = 20;
		
		ChampionDAO championDAO = new ChampionDAO();
		
		JsonObject jsonObject = new Gson().fromJson(partidaJson, JsonObject.class);
		Instant dateUnix = Instant.ofEpochMilli(jsonObject.getAsJsonObject("info").get("gameCreation")
				.getAsLong());
		Date data = Date.from(dateUnix);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String dataPartida = dateFormat.format(data);
		
		String modoDeJogo = jsonObject.getAsJsonObject("info").get("gameMode").getAsString();
		
		JsonArray participantes = jsonObject.getAsJsonObject("info").getAsJsonArray("participants");
		JsonObject time1 = (JsonObject)jsonObject.getAsJsonObject("info").getAsJsonArray("teams").get(0);
		JsonObject time2 = (JsonObject)jsonObject.getAsJsonObject("info").getAsJsonArray("teams").get(1);
		
		boolean vitoriaTime1 = time1.get("win").toString().equals("true") ? true : false;
		boolean vitoriaTime2 = time2.get("win").toString().equals("true") ? true : false;
				
		int playersPorPartida = 10;
		
		JsonObject player;
		String championIcon; 
		String summonerName;
		String championName;
		String kills;
		String deaths;
		String assists;
	%>
	
	<header>
		<nav>
			<a href="main">Home</a>
			<ul class=links>
				<li><a href="select-champions">Campeões</a></li>
				<li><a href="logout">Sair</a></li>
			</ul>
		</nav>
	</header>
	<section class="flexbox">
		<h1>Histórico de Partidas</h1>
		<div class="container">
			<div class="teamStats">
				<div class="fonteBold">
			    	<h2><%=modoDeJogo %></h2>
			    	<h2><%=dataPartida %></h2>
					<p>Blue Team</p>
					
					<%if(vitoriaTime1){ %>
						<p>Vitória</p>
					<%} else{%>
						<p>Derrota</p>
					<%} %>
					
				</div>
				
				<div class="horizontalAlign">		
					<%
						for(int i = 0; i < (playersPorPartida / 2); i++){ 
							player = (JsonObject)participantes.get(i);
							championIcon = championDAO.selectChampionById(Integer.parseInt(player.get("championId").toString()))
									.getChampionIcon();
							summonerName = player.get("summonerName").toString().replaceAll("\"", "");
							championName = player.get("championName").toString().replaceAll("\"", "");
							kills = player.get("kills").toString();
							deaths = player.get("deaths").toString();
							assists = player.get("assists").toString();
					%>
					
					<div class="player">
						<div class="fonteBold">
							<p> <%=summonerName %></p>
							<img class="championIcon" src="data:image/jpg;base64, <%=championIcon %>" />
							<p><%=championName %></p>
						</div>
						<div class="playerStats">
							<p>Kills: <%=kills %></p>
							<p>Deaths: <%=deaths %></p>
							<p>Assists: <%=assists %></p>
						</div>
					</div>
					<%
						}
					%>
				</div>
			</div>
			<hr class="barraDivisoria">
			<div class="teamStats">			
				<div class="fonteBold">
					<p>Red Team</p>
					
					<%if(vitoriaTime2){ %>
						<p>Vitória</p>
					<%} else{%>
						<p>Derrota</p>
					<%} %>
					
				</div>
				<div class="horizontalAlign">
					<%
						for(int i = (playersPorPartida / 2); i < playersPorPartida; i++){
							player = (JsonObject)participantes.get(i);
							championIcon = championDAO.selectChampionById(Integer.parseInt(player.get("championId").toString()))
									.getChampionIcon();
							summonerName = player.get("summonerName").toString().replaceAll("\"", "");
							championName = player.get("championName").toString().replaceAll("\"", "");
							kills = player.get("kills").toString();
							deaths = player.get("deaths").toString();
							assists = player.get("assists").toString();
					%>
					
					<div class="player">
						<div class="fonteBold">
							<p> <%=summonerName %></p>
							<img class="championIcon" src="data:image/jpg;base64, <%=championIcon %>" alt ="<%=championName%>"/>
							<p><%=championName %></p>
						</div>
						<div class="playerStats">
							<p>Kills: <%=kills %></p>
							<p>Deaths: <%=deaths %></p>
							<p>Assists: <%=assists %></p>
						</div>
					</div>
					<%
						}
					%>
				</div>
			</div>
		</div>
	</section>
	<footer>
	
		<%if(pagina > 0){ %>
		
			<a href="select-partidas?pagina=<%=pagina - 1%>">Anterior</a>
		
		<% 
			}
		%>
		<a href="update-partidas?puuid=<%=puuid%>">Atualizar Partidas</a>
		<%
		  if(pagina < maxPaginas - 1){		  
		%>
			<a href="select-partidas?puuid=<%=puuid %>&pagina=<%=pagina + 1%>">Proxima</a>
		
		<% } %>
		
		

	</footer>
	<script src="js/checarPartidas.js"></script>
</body>
</html>