'use strict';

angular.module('onlineExamSystemApp')
    .factory('TEACHER', function ($resource) {
        return $resource('api/tEACHERs/:id', {}, {
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
