/* global angular.js */
(function () {
    var module = angular.module('App', ['ngRoute']);
                     
    module.directive('barchart', function ($window) {

        return {
            restrict: 'E',
            template: '<div></div>',
            replace: true,
            link: function ($scope, element, attrs) {
                var morris;
                angular.element($window).bind('resize', function () {
                    if (morris) {
                        //console.log('morris resized');
                        morris.redraw();
                    }
                });

                attrs.$observe('value', function (val) {
                    if (!morris) {
                        //console.log('creating chart');
                        morris = Morris.Bar({
                            element: element,
                            data: angular.fromJson(val),
                            xkey: $scope[attrs.xkey],
                            ykeys: $scope[attrs.ykeys],
                            labels: $scope[attrs.labels],
                            lineColors: ['#fff'],
                            lineWidth: 2,
                            pointSize: 4,
                            gridLineColor: 'rgba(255,255,255,.5)',
                            resize: true,
                            gridTextColor: '#fff',
                            barColors: function (row, series, type) {
                                //console.log("--> "+row.label, row.x, series, type);
                                if(row.label == 'Parada Indeterminada') return "#e66454";
                                else if(row.label == 'Ociosa') return "#5ebd5e";
                                else if(row.label == 'Produzindo') return "#5bc0de";
                                else if(row.label == 'Produzindo (Ajuste Manual)') return "#5bc0de";
                                else if(row.label == 'Preparacao') return "#5bc0de";
                                else if(row.label == 'Manutencao') return "#e66454";
                                else if(row.label == 'Maquina Desligada') return "#5ebd5e";
                                else if(row.label == 'IHM Desligada') return "#f4b04f";
                                else if(row.label == 'Falha Comunicacao') return "#e66454";
                            }
                        });
                    } else {
                        //console.log('setting chart values');
                        morris.setData(angular.fromJson(val));
                    }
                });
            }
        };
    });

    // maquina services
    module.service('MaquinaService', function ($http) {
    	this.getAll = function () {
            return $http.get('http://fabrica.grupo.altamira.com.br/maquinas/search/findAllByAtivo?ativo=true');
        }
    	this.get = function (codigo) {
            return $http.get('http://fabrica.grupo.altamira.com.br/maquinas/' + codigo);
        }
        this.getLog = function (codigo) {
            return $http.get('http://fabrica.grupo.altamira.com.br/maquinaLogs/search/findAllByMaquina?maquina=' + codigo + '&page=0&size=20&sort=datahora,sequencia,desc');
        }
        this.getSumario = function(codigo) {
            return $http.get('http://fabrica.grupo.altamira.com.br/sumarios/search/findByMaquina?maquina=' + codigo);
        }
        this.getSumarioAll = function() {
            return $http.get('http://fabrica.grupo.altamira.com.br/sumarios/search/findAll');
        }
        this.getIHM = function(codigo) {
            return $http.get('http://fabrica.grupo.altamira.com.br/iHMs/search/findAllByMaquina?maquina=' + codigo);
        }
    });

    // menu services
    module.controller('MenuCtrl', function (MaquinaService, $scope) {
    	var self = this;
        self.options = [];
        self.count = 0;

        function refresh() {
        	MaquinaService.getAll().then(function (response) {
                self.options = response.data._embedded.maquinas;
                for( var i = 0; i < self.options.length; ++i ) {
                	
                	if (self.options[i].falhaComunicacao > 0) {
                		self.options[i].tempo = self.options[i].falhaComunicacao;
                		self.options[i].situacao = 99;
                	}
                	
                	if (self.options[i].situacao == '0' ||
                		self.options[i].situacao == '5' ||
                		self.options[i].situacao == '99') {
                		self.count++;
                	}

                    var segundos = parseInt(self.options[i].tempo, 10); 
                    var segundo = parseInt(segundos % 60, 10); 
                    var minutos = parseInt(segundos / 60, 10); 
                    var minuto = parseInt(minutos % 60, 10); 
                    var horas = parseInt(minutos / 60, 10);
                    var hora = parseInt(horas % 24, 10);
                    var dias = parseInt(horas / 24, 10); 
                    var dia = parseInt(dias, 10);
                    
                    if (dia > 1) {
                        self.options[i].tempo = dia + ' dias'
                    } else if (dia == 1) {
                        self.options[i].tempo = dia + ' dia'
                    } else if (hora > 0) {
                        self.options[i].tempo = hora + ' h'
                    } else if (minuto > 0) {
                        self.options[i].tempo = minuto + ' min'
                    } else {
                        self.options[i].tempo = segundo + ' s';
                    }
                }
            });
        }

        refresh();
        
        function initView() {

            var sock = new SockJS('http://fabrica.grupo.altamira.com.br/api/notify');
            sock.onmessage = function (response) {
                var msg = JSON.parse(response.data);

                for( var i = 0; i < self.options.length; ++i ) {
                	if (self.options[i].codigo == msg.data.maquina) {
                		self.options[i].situacao = msg.data.modo;

                        var segundos = parseInt(msg.data.tempo, 10); 
                        var segundo = parseInt(segundos % 60, 10); 
                        var minutos = parseInt(segundos / 60, 10); 
                        var minuto = parseInt(minutos % 60, 10); 
                        var horas = parseInt(minutos / 60, 10);
                        var hora = parseInt(horas % 24, 10);
                        var dias = parseInt(horas / 24, 10); 
                        var dia = parseInt(dias, 10);
                        
                        if (dia > 1) {
                            self.options[i].tempo = dia + ' dias'
                        } else if (dia == 1) {
                            self.options[i].tempo = dia + ' dia'
                        } else if (hora > 0) {
                            self.options[i].tempo = hora + ' h'
                        } else if (minuto > 0) {
                            self.options[i].tempo = minuto + ' min'
                        } else {
                            self.options[i].tempo = segundo + ' s';
                        }
                	}
                }
                
                self.count = 0;
                for( var i = 0; i < self.options.length; ++i ) {
                	if (self.options[i].situacao == '0' ||
                		self.options[i].situacao == '5' ||
                		self.options[i].situacao == '99') {
                		self.count++;
                	}
                }
                
                $scope.$apply();
            };
        }

        initView();        

    });
    // end menu services

    module.controller('DashboardCtrl', ['MaquinaService', '$scope','$routeParams', function (MaquinaService, $scope, $routeParams) {
        var self = this;
        self.maquinas = [];
        self.count = 0;

        function refresh() {
            MaquinaService.getAll().then(function (response) {
                self.maquinas = response.data._embedded.maquinas;

                for( var i = 0; i < self.maquinas.length; ++i ) {

                	if (self.maquinas[i].falhaComunicacao > 0) {
                		self.maquinas[i].situacao = 99;
                		self.maquinas[i].tempo = self.maquinas[i].falhaComunicacao;
                	} 
                	
                }
            });
        }

        refresh();

        self.data = {};
        self.total = 0;  

        $scope.xkey = 'descricao';

        $scope.ykeys = ['percentual'];

        $scope.labels = ['%'];

        $scope.myModel = [];
        
        function updateGraph() {
            self.data.sumario = [];
            MaquinaService.getSumarioAll().then(function (response) {
                if (response.data._embedded) {
                    $scope.myModel = response.data._embedded.sumarios; 
                    self.data.sumario = response.data._embedded.sumarios; 
                    for( var i = 0; i < self.data.sumario.length; ++i ) {
                        /*if (self.data.sumario[i].tempo > 60 * 60 * 10) {
                            self.data.sumario[i].tempo = 60 * 60 * 10;
                        }*/
                        self.total += self.data.sumario[i].tempo;
                    }
                    for( var i = 0; i < self.data.sumario.length; ++i ) {
                         self.data.sumario[i].percentual = (self.data.sumario[i].tempo / self.total) * 100;
                         self.data.sumario[i].percentual = self.data.sumario[i].percentual.toFixed(2);
                    }
                }
            });
        }

        updateGraph();
        
        function initView() {

            var sock = new SockJS('http://fabrica.grupo.altamira.com.br/api/notify');
            sock.onmessage = function (response) {
                var msg = JSON.parse(response.data);

                for( var i = 0; i < self.maquinas.length; ++i ) {
                    if (self.maquinas[i].codigo == msg.data.maquina) {

                    	self.maquinas[i].situacao = msg.data.modo;
                        self.maquinas[i].operador = msg.data.operador;
                        self.maquinas[i].tempo = msg.data.tempo;

                    }
                }
                
                $scope.$apply();
            };
        };

        initView(); 
    }]);
        
    module.controller('MaquinaCtrl', ['MaquinaService', '$scope','$routeParams', function (MaquinaService, $scope, $routeParams) {
        var self = this;
        self.data = {};
        self.total = 0;  

        $scope.xkey = 'descricao';

        $scope.ykeys = ['percentual'];

        $scope.labels = ['%'];

        $scope.myModel = [];

        function refresh() {
            MaquinaService.get($routeParams.codigo).then(function (response) {
                self.data = response.data;
                self.data.log = [];
                self.data.sumario = [];
                self.data.ihm = [];
                MaquinaService.getLog($routeParams.codigo).then(function (response) {
                    if (response.data.page.totalElements > 0) {
                        self.data.log = response.data._embedded.maquinaLogs;    
                    }
                });
                MaquinaService.getSumario($routeParams.codigo).then(function (response) {
                    if (response.data._embedded) {
                        $scope.myModel = response.data._embedded.sumarios; 
                        self.data.sumario = response.data._embedded.sumarios; 
                        for( var i = 0; i < self.data.sumario.length; ++i ) {
                            /*if (self.data.sumario[i].tempo > 60 * 60 * 10) {
                                self.data.sumario[i].tempo = 60 * 60 * 10;
                            }*/
                            self.total += self.data.sumario[i].tempo;
                        }
                        for( var i = 0; i < self.data.sumario.length; ++i ) {
                             self.data.sumario[i].percentual = (self.data.sumario[i].tempo / self.total) * 100;
                             self.data.sumario[i].percentual = self.data.sumario[i].percentual.toFixed(2);
                        }
                    }
                });
                MaquinaService.getIHM($routeParams.codigo).then(function (response) {
                    self.data.ihm = response.data._embedded.iHMs; 
                });
            });
        }

        refresh();

        function initView() {

            var sock = new SockJS('http://fabrica.grupo.altamira.com.br/api/notify');
            sock.onmessage = function (response) {
                var msg = JSON.parse(response.data);

                if (self.data.codigo == msg.data.maquina) {
                	
                    self.data.situacao = msg.data.modo;
            		self.data.tempo = msg.data.tempo;
                    self.data.operador = msg.data.operador;

                    self.data.log.reverse().push(msg.data);

                    if (self.data.log.length > 20) {
                        self.data.log = self.data.log.slice(self.data.log.length - 20);
                    }

                    self.data.log = self.data.log.reverse();

                    $scope.$apply();
                }

            };
        }

        initView();          
    }]);
    
    module.filter('statusClass', function () {
        return function (input) {
            switch (input) {
            case 0: return 'danger';
            case 1: return 'success';
            case 2: return 'info';
            case 3: return 'info';
            case 4: return 'info';
            case 5: return 'danger';
            case 6: return 'success';
            case 7: return 'warning';
            case 99: return 'danger';
            }
        }
    });

    module.filter('statusPanelClass', function () {
        return function (input) {
            switch (input) {
            case 0: return 'panel-danger';
            case 1: return 'panel-success';
            case 2: return 'panel-info';
            case 3: return 'panel-info';
            case 4: return 'panel-info';
            case 5: return 'panel-danger';
            case 6: return 'panel-success';
            case 7: return 'panel-warning';
            case 99: return 'panel-danger';
            }
        }
    });   

    module.filter('statusName', function () {
        return function (input) {
            switch (input) {
            case 0: return 'Parada Indeterminada';
            case 1: return 'Ociosa';
            case 2: return 'Produzindo';
            case 3: return 'Produzindo (Ajuste Manual)';
            case 4: return 'Preparacao';
            case 5: return 'Manutencao';
            case 6: return 'Maquina Desligada';
            case 7: return 'IHM Desligada';
            case 99: return 'Falha Comunicacao';
            }
        }
    });   

    module.filter('statusLabelClass', function () {
        return function (input) {
            switch (input) {
            case 0: return 'label-danger';
            case 1: return 'label-success';
            case 2: return 'label-info';
            case 3: return 'label-info';
            case 4: return 'label-info';
            case 5: return 'label-danger';
            case 6: return 'label-success';
            case 7: return 'label-warning';
            case 99: return 'label-danger';
            }
        }
    }); 

    module.filter('dayFormat', function() {
    	return function (input) {
    		var segundos = parseInt(input, 10); 
            var segundo = parseInt(segundos % 60, 10); 
            var minutos = parseInt(segundos / 60, 10); 
            var minuto = parseInt(minutos % 60, 10); 
            var horas = parseInt(minutos / 60, 10);
            var hora = parseInt(horas % 24, 10);
            var dias = parseInt(horas / 24, 10); 
            var dia = parseInt(dias, 10);
            
            var format = '';
            
            if (dia > 1) {
            	format = dia + ' dias'
            } else if (dia == 1) {
            	format = dia + ' dia'
            } else if (hora > 0) {
            	format = hora + ' h'
            } else if (minuto > 0) {
            	format = minuto + ' min'
            } else {
            	format = segundo + ' s';
            }
            
            return format;
    	}
    });
    
    module.filter('timeFormat', function () {
        return function (input) {
            var segundos = parseInt(input, 10); 
            var segundo = parseInt(segundos % 60, 10); 
            var minutos = parseInt(segundos / 60, 10); 
            var minuto = parseInt(minutos % 60, 10); 
            var horas = parseInt(minutos / 60, 10);
            var hora = parseInt(horas % 24, 10);
            var dias = parseInt(horas / 24, 10); 
            var dia = parseInt(dias, 10);
            
            var format = '';

            /*
            if (dia > 1) {
                format = dia + ' dias '
            } else if (dia == 1) {
                format = dia + ' dia '
            } */
            
            format += horas + ':' + ("0" + minuto).slice(-2) + ':' + ("0" + segundo).slice(-2) + ' h';
            
            /*if (hora > 0) {
                format += hora + ' h '
            } 
            if (minuto > 0) {
                format += minuto + ' min '
            } 
            if (segundo > 0) {
                format += segundo + ' s';
            }
            format += ' [' + input + ']'*/

            return format;
        }
    });

    module.config(function ($routeProvider) {
        $routeProvider.when('/home', {templateUrl: '/pages/home.tpl.html'});
        $routeProvider.when('/index', {templateUrl: '/template/index.html'});
        $routeProvider.when('/main', {templateUrl: '/pages/main.tpl.html'});
        $routeProvider.when('/dashboard', {templateUrl: '/pages/dashboard.html'});
        $routeProvider.when('/maquina/:codigo', {templateUrl: '/pages/maquina.html'});
        $routeProvider.when('/almoxarifado', {templateUrl: '/pages/almoxarifado.html'});
        $routeProvider.otherwise({redirectTo: '/almoxarifado'});
    });

    module.service('ActiveMQ', function() {

        var client; // = Stomp.client( "ws://localhost:61614/stomp", "v11.stomp" );

        function connect() {
            //client = Stomp.overTCP('localhost', 61612);
            client = Stomp.client( "ws://localhost:61614/stomp");
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

        //connect();

        this.send = function(topic, msg) {
            return client.send(topic, { }, msg);
        }

    });

    // servico de inventario
    module.service('Inventario', ['$http', function ($http) {
        this.getAll = function () {
            return $http.get('/materialInventarios');
        }
        this.get = function (tipo, numero) {
            return $http.get('/materialInventarios/search/findByIdTipoAndIdNumero?tipo=' + tipo + '&numero=' + numero);
        }
        this.medidas = function (tipo, numero) {
            return $http.get('/materialInventarioMedidas/search/findByIdTipoAndIdNumero?tipo=' + tipo + '&numero=' + numero);
        }
        this.movimentacao = function (movimentacao) {
            return $http.post('/material/movimentacao', movimentacao);
        }
    }]);

    // servico de materiais
    module.service('Material', function ($http) {
        this.getAll = function () {
            return $http.get('/materials');
        }
        this.get = function (codigo) {
            return $http.get('/materials/' + codigo);
        }
    });

    module.controller('AlmoxarifadoCtrl', ['$scope', '$document', 'ActiveMQ', 'Inventario', 'Material', function($scope, $document, ActiveMQ, Inventario, Material) {
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
                                            medidas: $scope.lote.material.medidas.length ? $scope.lote.material.medidas : []
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
                                            local:"?",
                                            medidas: /*$scope.lote.material.medidas.length ? $scope.lote.material.medidas :*/
                                                    [
                                                        { medida: "quantidade", unidade: "pc", valor: $scope.lote.quantidade},
                                                        /*
                                                        { medida: "largura", unidade: "mm", valor: 85.000000},
                                                        { medida: "espessura", unidade: "mm", valor: 1.800000},
                                                        { medida: "quantidade", unidade: "pc", valor: 1},
                                                        { medida: "peso", unidade: "kg", valor: 2250.0000000000}
                                                        */
                                                    ]
                                          }]
                                        };

                    var json = JSON.stringify( movimentacao, function( key, value ) {
                        if( key === "$$hashKey" ) {
                            return undefined;
                        } else if (key === "id") {
                            return undefined;
                        } else if (key.substr(0, 1) === '_') {
                            return undefined;
                        }

                        return value;
                    });

                    console.log(json);

                    //Inventario.post($scope.lote);
                    //ActiveMQ.send("/queue/IHM-MATERIAL-MOVIMENTACAO", json);
                    Inventario.movimentacao(json);
            
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
                                            for( var i = 0; i < $scope.lote.material.medidas.length; ++i ) {
                                                $scope.lote.material.medidas[i].medida = $scope.lote.material.medidas[i].id.medida;
                                                delete $scope.lote.material.medidas[i].id;
                                            }
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

}());