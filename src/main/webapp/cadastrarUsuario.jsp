<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cadastrar Usuário</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;700&display=swap" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/cadastrarUsuarios.css"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script></head>
<body>
	<header>
		<nav>
			<a href="login.jsp">Login</a>
		</nav>
	</header>
	<section class="flexbox">
		<h1>Cadastro</h1>
		<div class="container">
			<form name="frmUsuario" class="frmUsuario" action="insert-summoner" method="post">
				<label for="nome">Nome: </label>
				<input id="nome" type="text" name="nome"/>
				
				<label for="email">Email: </label>
				<input id="email" type="email" name="email"/>
				
				<label for="senha">Senha: </label>
				<input id="senha" type="password" name="senha"/>
				
				<label for="summonerName">Nome de Invocador: </label>
				<input id="summonerName" type="text" id="summonerName" name="summonerName"/>
				<button id="btnChecaInvocador" type="button">Checar Invocador</button>

				<button id="btnCadastro" type="submit" disabled>Cadastrar</button>
			</form>
			</div>
	</section>
<script type="text/javascript" src="js/validarCadastro.js"></script>

</body>
</html>

