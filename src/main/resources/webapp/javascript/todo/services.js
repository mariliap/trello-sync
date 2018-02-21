'use strict';

var todoServices = angular.module('todoServices', ['ngResource']);

todoServices.factory('ToDo', ['$resource',
    function($resource) {
        return $resource(
                'http://localhost:12345/rest/todo/:userId/:itemId',
                {userId: 'nicole', itemId: '@id'}, {'update': {method: 'PUT'}});
    }]);