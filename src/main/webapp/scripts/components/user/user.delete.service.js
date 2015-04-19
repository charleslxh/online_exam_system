'use strict';

angular.module('onlineExamSystemApp')
    .factory('userDelete', function ($resource) {
        return $resource('api/users/delete/:id', {}, {
        });
    });