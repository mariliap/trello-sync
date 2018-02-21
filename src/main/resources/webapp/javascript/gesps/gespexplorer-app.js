/**
 * Created by Marilia Portela on 26/03/2017.
 */
'use strict';

var gesptrellosyncApp = angular.module(
    'trelloGespSyncApp',
    ['ngRoute', 'ngResource']);

gesptrellosyncApp.config(function($routeProvider) {

    $routeProvider.when('/', {
        templateUrl : 'partials/welcome.html',
        controller: 'DemoCtrl'
    }).when('/app', {
        templateUrl : 'partials/listagem.html',
        controller: 'DemoCtrl'
    }).otherwise({
        redirectTo : 'partials/welcome.htm',
        controller: 'DemoCtrl'
    });

});//'http://localhost:12345/rest/gespAuth/:token'

gesptrellosyncApp.factory('GespAuthRest',
    function($resource) {
        return $resource(
            'http://localhost:12345/rest/trelloGespSync/autenticar/gesp',
            {token: '@tokenGesp'},
            {'update': {method: 'POST'}});
    });

//gesptrellosyncApp.factory('TrelloGespSyncRest',
//    function($resource) {
//        return $resource(
//            'http://localhost:12345/rest/trelloGespSync/:tokenGesp/:tokenTrello',
//            {tokenGesp: '@tokenGesp', tokenTrello: '@tokenTrello'}, {'update': {method: 'PUT'}});
//    });

gesptrellosyncApp.controller(
    'DemoCtrl',
    function($scope, $http, $rootScope, $timeout,
                 $location, $filter, GespAuthRest) {

        $scope.subjects = ['Todos os GESPs AGUARDANDO ATENDIMENTO',
            'Todos os GESPs EM ATENDIMENTO'];
        //$scope.subjects;
        $scope.selectedItem =  $scope.subjects[0];



        $scope.logInTrello = function() {

            //$scope.items = GespAuthRest.query();

            $http({method: 'GET', url: 'http://localhost:12345/rest/trelloGespSync/everybody'})
                .then(function successCallback(response) {
                    $scope.errauthc = false;
                    $scope.entries = response.data;
                    $location.path('/app');
                }, function errorCallback(response) {
                    $scope.errauthc = true;
                });


        }

        $scope.dropboxitemselected = function (item) {

            $scope.selectedItem = item;
            $scope.logInTrello();
        }

        $scope.logInGesp = function() {
            var trelloGespSyncRest = new GespAuthRest(
                    {loginGesp: $scope.tokenGesp.loginGesp,
                    passwordGesp: $scope.tokenGesp.passwordGesp});

            trelloGespSyncRest.$save(
                function(data) {
                    // Success.
                    //$scope.items.push(data);
                    //$scope.newToDoDescription = '';

                    console.log(data.response);
                    $scope.errauthc = false;
                    $location.path('/app');
                },
                function(error) {
                    // Error.
                    //growl.addErrorMessage(error.data.pop().message);
                    $scope.errauthc = true;
                });
        };

    });