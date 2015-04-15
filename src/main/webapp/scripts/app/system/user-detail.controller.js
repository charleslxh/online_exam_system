'use strict';

angular.module('onlineExamSystemApp')
    .controller('UserDetailController', function ($scope, $stateParams, User) {
        $scope.user = {};
        $scope.load = function (login) {
            User.get({login: login}, function(result) {
              console.log(result);
              $scope.user = result;
            });
        };
        $scope.load($stateParams.login);
    });