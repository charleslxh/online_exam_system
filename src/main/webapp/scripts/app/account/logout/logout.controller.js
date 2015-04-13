'use strict';

angular.module('onlineExamSystemApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
