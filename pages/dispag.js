angular.module("dispag",[]);



angular.module("dispag").controller("logincontroller", function ($scope, $http) { 

	
	const dadosAutenticacao = {
		userdefault : "Alex",
		passwddefault : "1234",
		erroeutenticacao: false,
		user : "",
		passwd : "",
		
		
	};

	$scope.autenticar = function(){
		
		if (dadosAutenticacao.user==dadosAutenticacao.userdefault && dadosAutenticacao.passwd==dadosAutenticacao.passwddefault)
			window.location.href = "debitsdashboard.html";
		else
			dadosAutenticacao.erroeutenticacao = true;
		
	  	
	};

	$scope.dadosAutenticacao = dadosAutenticacao;
	
});
