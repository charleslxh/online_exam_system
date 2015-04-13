'use strict';

angular.module('onlineExamSystemApp')
    .factory('Test1', function ($resource) {
        return $resource('api/test1s/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createTime = new Date(data.createTime);
                    data.laseUpdateTime = new Date(data.laseUpdateTime);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
