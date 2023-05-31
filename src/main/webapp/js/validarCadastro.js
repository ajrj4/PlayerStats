var nome = $("#nome");
var email = $("#email");
var senha = $("#senha");
var summonerName = $("#summonerName");
var formCadastro = $("#frmCadastro");

$("#btnCadastro").on("click", function(){	
	if(nome.val() === null || nome.val() === ""){
		
		alert("Preencha o campo nome");
		nome.focus();
		return false;	
		
	}else if(email.val()=== null || email.val() === ""){
		
		alert("Preencha o campo email");
		email.focus();
		return false;
		
	}else if(senha.val()=== null || senha.val() === ""){
		
		alert("Preencha o campo senha");
		senha.focus();
		return false;
		
	}else if(summonerName.val()=== null || summonerName.val() === ""){
		
		alert("Preencha o campo invocador");
		summonerName.focus();
		return false;
		
	}else{
		formCadastro.submit();
	}
});

