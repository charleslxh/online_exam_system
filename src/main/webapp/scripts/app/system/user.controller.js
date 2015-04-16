'use strict';

angular.module('onlineExamSystemApp')
    .controller('UserController', function ($scope, Principal, ParseLinks, User, Auth) {
        $scope.users = [];
        $scope.page = 1;

        $scope.loadAll = function() {
            User.query(function(result, headers) {
                $scope.users = result;
            });
        };

        $scope.loadAll();

        $scope.create = function() {
            $scope.user.roles = [$scope.user.roles]
            Auth.createAccount($scope.user).then(function () {
                    $scope.success = 'OK';
                }).catch(function (response) {
                    console.log(response)
                    $scope.success = null;
                    if (response.status === 400 && response.data === 'login already in use') {
                        $scope.errorUserExists = 'ERROR';
                    } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                        $scope.errorEmailExists = 'ERROR';
                    } else if (response.status === 400 && response.data === 'student number already in use') {
                        $scope.errorUserNoExists = 'ERROR';
                    } else {
                        $scope.error = 'ERROR';
                    }
                }
            );
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
