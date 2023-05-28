<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form name="frmChampion" action="insert-champion" method="post" enctype="multipart/form-data">
		<input type="text" name="id" placeholder="id"/>
		<input type="text" name="name" placeholder="name"/>
		<input type="file" name="icon" />
		<input type="submit" value="Enviar" />
	</form>
</body>
</html>