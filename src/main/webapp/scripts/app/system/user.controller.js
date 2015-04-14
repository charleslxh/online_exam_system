'use strict';

angular.module('onlineExamSystemApp')
    .controller('UserController', function ($scope, Principal, ParseLinks, User) {
        $scope.users = [];
        $scope.page = 1;

        $scope.loadAll = function() {
            User.query(function(result, headers) {
                console.log(result)
        		// $scope.links = ParseLinks.parse(headers('link'));
                $scope.users = result;
            });
        };

        $scope.loadAll();
    });
