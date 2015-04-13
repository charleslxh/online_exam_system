'use strict';

angular.module('onlineExamSystemApp')
    .factory('Password', function ($resource) {
        return $resource('api/account/change_password', {}, {
        });
    });
