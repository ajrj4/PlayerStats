<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cadastrar Usuário</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>

</head>
<body>
	<form name="frmUsuario" action="insert-summoner" method="post">
		<label for="nome">Nome: </label>
		<input type="text" name="nome"/>
		
		<label for="email">Email: </label>
		<input type="email" name="email"/>
		
		<label for="senha">Senha: </label>
		<input type="password" name="senha"/>
		
		<label for="summonerName">Nome de Invocador: </label>
		<input type="text" id="summonerName" name="summonerName"/>
		<input type="submit" id="btnCadastrar" name="btnCadastrar" value="Cadastrar">
	</form>
</body>
</html>

