'use strict';

angular.module('onlineExamSystemApp')
    .factory('Test2', function ($resource) {
        return $resource('api/test2s/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
