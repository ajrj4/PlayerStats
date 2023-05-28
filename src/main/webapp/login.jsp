<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="login" method="post">
		Nome de Usuario:
		<input type="text" name="nome"/>
		Senha:
		<input type="password" name="senha"/>
		<input type="submit" value="Fazer Login"/>
		<a href="cadastrarUsuario.jsp">Cadastre-se</a>
	</form>
</body>
</html>