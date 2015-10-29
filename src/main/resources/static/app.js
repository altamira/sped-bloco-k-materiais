/* global angular,hljs */
(function () {
    var springCloudAws = angular.module('SpringCloudAws', ['ngRoute']);

    // Global stuff
    springCloudAws.directive('active', function ($location) {
        return {
            link: function (scope, element) {
                function makeActiveIfMatchesCurrentPath() {
                    if ($location.path().indexOf(element.find('a').attr('href').substr(1)) > -1) {
                        element.addClass('active');
                    } else {
                        element.removeClass('active');
                    }
                }

                scope.$on('$routeChangeSuccess', function () {
                    makeActiveIfMatchesCurrentPath();
                });
            }
        };
    });

    // SQS
    springCloudAws.service('SqsService', function ($http) {
        this.sendMessage = function (message) {
            return $http.post('/api/0.0.1-SNAPSHOT/lead', message);
        };
    });

    springCloudAws.controller('SqsCtrl', function (SqsService, $scope) {
        var self = this;
        self.model = {};
        self.responses = [];

        function initMessageToProcess() {
            self.model.messageToProcess = undefined;
        }

        self.sendMessage = function () {
        	if (confirm("Confirma o envio ?")) {
	            SqsService.sendMessage(self.model.messageToProcess);
	            initMessageToProcess();
        	}
        };

        function initView() {
            initMessageToProcess();

            var sock = new SockJS('/notify');
            sock.onmessage = function (e) {
                var jsonResponse = JSON.parse(e.data);
                self.responses.reverse().push(jsonResponse);

                if (self.responses.length > 10) {
                    self.responses = self.responses.slice(self.responses.length - 10);
                }

                self.responses = self.responses.reverse();
                $scope.$apply();
                
                //alert('O status da maquina foi atualizado !');
            };
        }

        initView();
    });

    springCloudAws.filter('modoOperacao', function () {
        return function (input) {
            switch (input) {
            case 0: return 'Parada Indeterminado';
            case 1: return 'Ociosa';
            case 2: return 'Produzindo';
            case 3: return 'Manual';
            case 4: return 'Preparacao';
            case 5: return 'Manutencao';
            }
        }
    });
    
    springCloudAws.config(function ($routeProvider) {
        $routeProvider.when('/main', {templateUrl: 'pages/main.tpl.html'});
        $routeProvider.otherwise({redirectTo: '/main'});
    });
}());