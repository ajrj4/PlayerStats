var nome = $("#nome");
var senha = $("#senha");
var formLogin = $("#frmLogin");

$("#btnLogin").on("click", function(){	
	if(nome.val() === null || nome.val() === ""){
		alert("Preencha o campo nome");
		nome.focus();
		return false;
		
	}else if(senha.val()=== null || senha.val() === ""){
		alert("Preencha o campo senha");
		senha.focus();
		return false;
	}else{
		formLogin.submit();
	}
});

