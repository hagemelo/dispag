angular.module("debitsdashboard",[]);

angular.module("debitsdashboard").controller("debitsdashboardcontroller", function ($scope, $http) { 

	
	const debitosdados = {
		debitos : [],
		detalhesDebitosBasicos: [],
		detalhesDebitosRecorrentes: [],
		detalhesDebitosAvulsos: [],
		tiposDebitos:['Básicos', 'Recorrentes', 'Avulsos'],
		debitosBasicos:['Água','Aluguel','Gás','IPTU','Internet','Luz','Telefone'],
		showDetalhes: false,
		novoDebito: {
			vencimento:'',
			tipo:'',
			descricao:'',
			valor:0,
			parcela:1
		}
	};

	preparar = function(){
		var debito = {
			nome:'Mar 2020',
			valor:'R$ 250,00',
			cat:[{nome:'Básicos', valor:'R$ 0,00'},
				 {nome:'Recorrentes', valor:'R$ 100,00'},
				 {nome:'Avulsos', valor:'R$ 150,00'}
			]
			
		};
		
		debitosdados.debitos.push(debito);
		
		
		debito ={
			nome:'Abr 2020',
			valor:'R$ 1.600,00',
			cat:[{nome:'Básicos', valor:'R$ 600,00'},
				 {nome:'Recorrentes', valor:'R$ 800,00'},
				 {nome:'Avulsos', valor:'R$ 200,00'}
			]
		};
		
		debitosdados.debitos.push(debito);
		
	  	debito ={
			nome:'Mai 2020',
			valor:'R$ 780,00',
			cat:[{nome:'Básicos', valor:'R$ 180,00'},
				 {nome:'Recorrentes', valor:'R$ 300,00'},
				 {nome:'Avulsos', valor:'R$ 300,00'}
			]
		};
		debitosdados.debitos.push(debito);
		
		var detalhe = {
			vencimento:'12/04/2020',
			descricao:'Água',
			valor:'R$ 35,00',
			status:'PAGO'			
		};
		debitosdados.detalhesDebitosBasicos.push(detalhe);
		detalhe = {
			vencimento:'20/04/2020',
			descricao:'Luz',
			valor:'R$ 35,00',
			status:'À VENCER'			
		};
		debitosdados.detalhesDebitosBasicos.push(detalhe);
		
		detalhe = {
			vencimento:'01/04/2020',
			descricao:'Cartao Master',
			valor:'R$ 365,00',
			status:'ATRASADO'			
		};
		debitosdados.detalhesDebitosRecorrentes.push(detalhe);
		
		detalhe = {
			vencimento:'04/04/2020',
			descricao:'Cartao Lider',
			valor:'R$ 962,00',
			status:'PAGO'			
		};
		debitosdados.detalhesDebitosRecorrentes.push(detalhe);
		
		detalhe = {
			vencimento:'04/04/2020',
			descricao:'Tinta',
			valor:'R$ 962,00',
			status:'PAGO'			
		};
		debitosdados.detalhesDebitosAvulsos.push(detalhe);
		
		detalhe = {
			vencimento:'04/04/2020',
			descricao:'1 Mensalidade do Colegio',
			valor:'R$ 562,00',
			status:'PAGO'			
		};
		debitosdados.detalhesDebitosAvulsos.push(detalhe);
		
	};

	preparar();

	$scope.debitosdados = debitosdados;
	
	$scope.mostrardetalhes = function(mesano){
		
		if (mesano=='Mar 2020')
			debitosdados.detalhesDebitosBasicos =  [];
		
		debitosdados.showDetalhes = true;
		
	  	
	};
	
});
