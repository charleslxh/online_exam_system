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

        $scope.showAdd = function() {
            $('#saveUserTipsModal').modal('hide');
            $('#saveUserModal').modal('show');
        }

        $scope.create = function() {
            var roles = [];
            roles.push($scope.user.roles);
            $scope.user.roles = roles;
            $scope.user.password = 'abc123_'
            $scope.user.langKey = 'en'

            Auth.createAccount($scope.user).then(function () {
                    $scope.loadAll();
                    $scope.showTips = 'success';
                    $('#saveUserModal').modal('hide');
                    $('#saveUserTipsModal').modal('show');
                    $scope.clear();
                }).catch(function (response) {
                    $scope.showTips = 'failed';
                    $('#saveUserModal').modal('hide');
                    $('#saveUserTipsModal').modal('show');
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
            $scope.user = {email: null, login: null, password: null, roles: null, userNo: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        }
    });
