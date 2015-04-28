'use strict';

angular.module('onlineExamSystemApp')
    .factory('Question', function ($resource) {
        return $resource('api/questions/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createTime = new Date(data.createTime);
                    data.lastUpdateTime = new Date(data.lastUpdateTime);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
