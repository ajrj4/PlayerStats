<%@page import="com.google.gson.JsonElement"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.google.gson.JsonObject"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.google.gson.JsonArray"%>
<%@page import="model.ChampionDAO"%>
<%@page import="bean.Champion"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Maestria de Campeões</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="css/listarMaestrias.css">
</head>
<body>
	<%
		if(request.getAttribute("maestrias") == null){
			response.sendRedirect("show-mastery");
			return;
		}
	
		ChampionDAO championDao = new ChampionDAO();
		List<Champion> championList = championDao.selectChampions();
		Map<Integer, Champion> championMap = new HashMap<>();
		
		for(int i = 0; i < championList.size(); i++){
			championMap.put(championList.get(i).getChampionId(), championList.get(i));
		}
		
		String maestriaJson = (String)request.getAttribute("maestrias");
		
		JsonArray maestrias = new Gson().fromJson(maestriaJson, JsonArray.class);
		
		Iterator<JsonElement> i = maestrias.iterator();
		
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
		<h1>Maestria de Campeões de <%=session.getAttribute("summoner") %></h1>
		<div class="container">
		<%
			while(i.hasNext()){
			JsonObject champion = (JsonObject) i.next();
			int championIndex = champion.get("championId").getAsInt();
		%>
			<div class="champion">
				<h2><%=championMap.get(championIndex).getChampionName() %></h2>
				<img src="data:image/jpg;base64, <%=championMap.get(championIndex).getChampionIcon() %>"/>
				<p>Nível de Maestria: <%=champion.get("championLevel").getAsInt() %></p>
				<p>Pontos de Maestria: <%=champion.get("championPoints").getAsInt() %></p>
			</div>
		<%
			}
		%>
		</div>
	</section>
</body>
</html>