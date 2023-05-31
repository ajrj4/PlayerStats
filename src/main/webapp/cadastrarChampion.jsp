<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cadastrar Champion</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;700&display=swap" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/cadastrarChampions.css"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
</head>
<body>
	<header>
		<nav>
		<a href="main">Home</a>
			<a href="logout">Sair</a>
		</nav>
	</header>
	<section class="flexbox">
		<h1>Cadastrar Champion</h1>
		<div class="container">
			<form name="frmChampion" class="frmChampion" action="insert-champion" method="post" enctype="multipart/form-data">
				<label for="id">ID do Campeão:</label>
				<input class="inputData" id="id" type="text" name="id"/>
				
				<label for="name">Nome do Campeão:</label>
				<input class="inputData" id="name" type="text" name="name"/>
				
				<label for="imgUpload">Ícone do Campeão:</label>
				<input type="file" id="imgUpload" name="icon" />
				
				<button id="btnCadastro" type="submit">Enviar</button>
			</form>
		</div>	
	</section>
	<script type="text/javascript" src="js/validarChampion.js"></script>
</body>
</html>