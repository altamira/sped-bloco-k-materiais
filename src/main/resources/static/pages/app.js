var app = angular.module('myApp', []);

/*app.config(function($httpProvider) {
  $httpProvider.defaults.withCredentials = true;
});*/

app.service('ActiveMQ', function() {

	var client; // = Stomp.client( "ws://localhost:61614/stomp", "v11.stomp" );

	function connect() {
		//client = Stomp.overTCP('localhost', 61612);
		client = Stomp.client( "ws://inventario.altamira.com.br:61614/stomp", "v11.stomp" );
		client.connect( "", "",
			function() {
				console.log('Conectado no ActiveMQ via STOMP over Websocket.')
			    client.subscribe("/topic/IHM-MATERIAL-MOVIMENTACAO",
			    	function( message ) {
			    	    alert( message );
			    	    //message.ack();
			        }, 
					{ack: 'client', persistent: true, id:'stomp-ID-123453'} 
			    );
			}, function(error) {
				console.log('STOMP: ' + error);
			    setTimeout(connect, 5000);
			    console.log('STOMP: Reconecting in 5 seconds');
			}
		);					
	}

	connect();

	this.send = function(topic, msg) {
		return client.send(topic, { }, msg);
	}

});

// servico de inventario
app.service('Inventario', ['$http', 'ActiveMQ', function ($http, ActiveMQ) {
	this.getAll = function () {
        return $http.get('http://inventario.altamira.com.br/materialInventarios');
    }
	this.get = function (tipo, numero) {
        return $http.get('http://inventario.altamira.com.br/materialInventarios/search/findByIdTipoAndIdNumero?tipo=' + tipo + '&numero=' + numero);
    }
    this.medidas = function (tipo, numero) {
    	return $http.get('http://inventario.altamira.com.br/materialInventarioMedidas/search/findByIdTipoAndIdNumero?tipo=' + tipo + '&numero=' + numero);
    }
}]);

// servico de materiais
app.service('Material', function ($http) {
	this.getAll = function () {
        return $http.get('http://inventario.altamira.com.br/materials');
    }
	this.get = function (codigo) {
        return $http.get('http://inventario.altamira.com.br/materials/' + codigo);
    }
});

app.controller('myCtrl', ['$scope', '$document', 'ActiveMQ', 'Inventario', 'Material', function($scope, $document, ActiveMQ, Inventario, Material) {
	$scope.lote = {};
	$scope.lotes = [];
	$scope.barcode = "";
	$scope.quantidade = "";
	$scope.valid = false;

	$scope.erro = "";

	// variaveis para debug do teclado
	/*
	$scope.keys = [];
	$scope.key_code = 0;
	$scope.key_press = 0;
	*/

    $document.bind("keypress", function(event) {
        
    	// usado para debug
        /*
        console.debug(event)

		$scope.key_code = String.fromCharCode(event.charCode);
		$scope.key_press = event.keyCode;
		*/

		$scope.msg = "";

		if (event.which == 13 && $scope.valid) {
			if (Number($scope.quantidade)) {
				
				$scope.lote.quantidade = parseInt($scope.quantidade, 10);

				$scope.lotes.reverse().push($scope.lote);

                if ($scope.lotes.length > 10) {
                    $scope.lotes = $scope.lotes.slice($scope.lotes.length - 10);
                }

                $scope.lotes = $scope.lotes.reverse();

                var movimentacao = {
									ihm: "ALMOXARIFADO",
									datahora: "2015-12-10T00:00:00-0000",
									operador: "NILSON",
									materiais: 
									  [{ 
									    movimentacao: "D1", 
									    lote: 
									      { 
									        tipo: $scope.lote.id.tipo, 
										    numero: $scope.lote.id.numero
									      }, 
									    codigo: $scope.lote.material.codigo, 
									    descricao: $scope.lote.material.descricao, 
									    emUso: false,
									    local:"ALMOXARIFADO",
									    medidas: $scope.lote.material.medidas.length ? $scope.lote.material.medidas :
									    		[
											        { "medida": "largura", "unidade": "mm", "valor": 85.000000},
											        { "medida": "espessura", "unidade": "mm", "valor": 1.800000},
											        { "medida": "quantidade", "unidade": "pc", "valor": 1},
											        { "medida": "peso", "unidade": "kg", "valor": 2250.0000000000}
											    ]
									  },
									  { 
									    movimentacao: "D2", 
									    lote: 
									      { 
									        tipo: $scope.lote.id.tipo, 
										    numero: $scope.lote.id.numero
									      }, 
									    codigo: $scope.lote.material.codigo, 
									    descricao: $scope.lote.material.descricao, 
									    emUso: false,
									    local:"<setor destino requisitante>",
									    medidas: $scope.lote.material.medidas.length ? $scope.lote.material.medidas :
									    		[
											        { "medida": "largura", "unidade": "mm", "valor": 85.000000},
											        { "medida": "espessura", "unidade": "mm", "valor": 1.800000},
											        { "medida": "quantidade", "unidade": "pc", "valor": 1},
											        { "medida": "peso", "unidade": "kg", "valor": 2250.0000000000}
											    ]
									  }]
									};

				console.log(JSON.stringify(movimentacao));

				//Inventario.post($scope.lote);
				ActiveMQ.send("/queue/IHM-MATERIAL-MOVIMENTACAO", JSON.stringify(movimentacao));
		
				//console.log("Baixando estoque");
				//alert('Estoque baixado com sucesso.');
		
			} else {
				$scope.msg = "Quantidade invalida.";
			}

			$scope.barcode = "";
			$scope.lote = {};
			$scope.quantidade = "";
			$scope.valid = false;
		} else if ((event.which == 43 || event.which == 13)) {
			if ($scope.barcode.trim().length && !$scope.valid) {

				$scope.barcode = $scope.barcode.trim().replace(" ", "").replace("-", "").replace(".", "").replace(" ", "").replace("-", "").replace(".", "");

				var tipo = $scope.barcode.substr(0, 2);
				var numero = $scope.barcode.substr(2, $scope.barcode.length - 4);
				var digito = $scope.barcode.substr($scope.barcode.length - 2, 2);

				if (tipo && numero && $scope.barcode.trim().length <= 16) {
					Inventario.get(tipo, numero).then(function (response) {
						if (response.data._embedded) {
							$scope.lote = response.data._embedded.materialInventarios[0];
							Material.get($scope.lote.material).then(function (response) {
								$scope.lote.material = response.data;
								Inventario.medidas(tipo, numero).then(function (response) {
									if (response.data._embedded) {
										$scope.lote.material.medidas = response.data._embedded.materialInventarioMedidas;
									} else {
										$scope.lote.material.medidas = [];
									}
								});
								$scope.valid = true;
							});
						} else {
							$scope.msg = 'Lote nao foi encontrado !';
							$scope.barcode = "";
							$scope.valid = false;
						}
					});
				} else {
					$scope.msg = 'Codigo de barras "' + $scope.barcode.substr(1, 16) + '" invalido, verifique.';
					$scope.barcode = "";
					$scope.valid = false;
				}
				
			} else {
				$scope.barcode = "";
				$scope.keys = [];
				$scope.quantidade = "";
			}
		} else if (event.charCode == 42 || event.charCode == 47) {
			$scope.lote = {};
			$scope.barcode = "";
			$scope.keys = [];
			$scope.quantidade = "";
			$scope.valid = false;
		} else if (!$scope.valid && (event.charCode > 47 && event.charCode < 58) || (event.charCode == 45)) {
			$scope.barcode += String.fromCharCode(event.charCode);	
		} else if ($scope.valid) {
			$scope.quantidade += String.fromCharCode(event.charCode);	
		}

		// debug
		//$scope.keys.push({"keyCode": event.keyCode, "charCode": String.fromCharCode(event.charCode)});

    	$scope.$apply();

    	event.preventDefault();

    });

}]);

