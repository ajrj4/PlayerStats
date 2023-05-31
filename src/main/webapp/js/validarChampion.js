var id = $("#id");
var nome = $("#name");
var formChampion = $("#frmChampion");

$("#btnCadastro").on("click", function(){	
	
	if(id.val() === null || id.val() === ""){
		
		alert("Preencha o campo id");
		id.focus();
		return false;	
		
	}else if(nome.val()=== null || nome.val() === ""){
		
		alert("Preencha o campo nome");
		nome.focus();
		return false;
		
	}else{
		formChampion.submit();
	}
});