'use strict';

angular.module('onlineExamSystemApp')
    .controller('UserDetailController', function ($scope, $stateParams, User) {
        $scope.user = {};
        $scope.load = function (login) {
            console.log(login)
            User.get({login: login}, function(result) {
              $scope.user = result;
            });
        };
        $scope.load($stateParams.login);
    });
