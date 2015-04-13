'use strict';

angular.module('onlineExamSystemApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


