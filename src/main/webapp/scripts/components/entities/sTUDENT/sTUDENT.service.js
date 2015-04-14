'use strict';

angular.module('onlineExamSystemApp')
    .factory('STUDENT', function ($resource) {
        return $resource('api/sTUDENTs/:id', {}, {
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
