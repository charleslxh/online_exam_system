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

        $scope.create = function() {
            console.log($scope.user);
        };

        $scope.update = function() {
            console.log('user update');
        };

        $scope.delete = function(login) {
            User.get(login, function(result) {
              $scope.user = result;
              $('#deleteUserConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function(login) {
            User.delete({login: login},
                function () {
                    $scope.loadAll();
                    $('#deleteUserConfirmation').modal('hide');
                    $scope.clear();
                }
            );
        }

        $scope.clear = function() {
            console.log('user clear');
        }
    });
