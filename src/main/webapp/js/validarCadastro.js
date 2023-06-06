const key = ""; //Não é interessante deixar público no github 

var nome = $("#nome");
var email = $("#email");
var senha = $("#senha");
var summonerName = $("#summonerName");
var formCadastro = $("#frmCadastro");
var btnChecaInvocador = $("#btnChecaInvocador");
var btnCadastro = $("#btnCadastro");

btnCadastro.on("click", function() {
	if (nome.val() === null || nome.val() === "") {

		alert("Preencha o campo nome");
		nome.focus();
		return false;

	} else if (email.val() === null || email.val() === "") {

		alert("Preencha o campo email");
		email.focus();
		return false;

	} else if (senha.val() === null || senha.val() === "") {

		alert("Preencha o campo senha");
		senha.focus();
		return false;

	} else if (summonerName.val() === null || summonerName.val() === "") {

		alert("Preencha o campo invocador");
		summonerName.focus();
		return false;

	} else {
		formCadastro.submit();
	}
});

summonerName.on("input", () => {
	btnCadastro.prop("disabled", true);
})

btnChecaInvocador.on("click", () => {
	$.ajax({
		url: "https://br1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + summonerName.val() + "?api_key=" + key,
		dataType: "json",
		success: (result) => {
			summonerName.val(result.name).removeClass("error").addClass("success");
			btnCadastro.prop("disabled", false);
			
		},
		error: () => {
			summonerName.removeClass("success").addClass("error");
			btnCadastro.prop("disabled", true);
		}
	})
});
