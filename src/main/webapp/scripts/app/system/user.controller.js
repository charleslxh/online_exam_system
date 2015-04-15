'use strict';

angular.module('onlineExamSystemApp')
    .controller('UserController', function ($scope, Principal, ParseLinks, User) {
        $scope.users = [];
        $scope.page = 1;

        $scope.loadAll = function() {
            User.query(function(result, headers) {
                $scope.users = result;
            });
        };

        $scope.loadAll();
    });
