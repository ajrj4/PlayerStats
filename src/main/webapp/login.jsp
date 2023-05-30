<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;700&display=swap" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/login.css"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
</head>
<body>
	<header>
		<nav>
			<a href="cadastrarUsuario.jsp">Cadastre-se</a>
		</nav>
	</header>
	<section class="flexbox">
		<h1>Login</h1>
		<div class="container">
			<form name="frmLogin" class="loginForm" action="login" method="post">
				<label for="nome">Nome de Usuario:</label>
				<input type="text" id="nome" name="nome"/>
				
				<label for="senha">Senha:</label>
				<input type="password" id="senha" name="senha"/>
				
				<button id="btnLogin" type="submit">Fazer Login</button>
			</form>
		</div>
		
	</section>
<script type="text/javascript" src="js/validarLogin.js"></script>
</body>
</html>