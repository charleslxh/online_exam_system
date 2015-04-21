'use strict';

angular.module('onlineExamSystemApp')
    .controller('UserController', function ($scope, Principal, ParseLinks, User, Auth, userDelete, userUpdate) {
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
            $scope.user.deleted = 0
            console.log($scope.user)
            Auth.createAccount($scope.user).then(function () {
                    $scope.loadAll();
                    $scope.showTips = 'success';
                    $('#saveUserModal').modal('hide');
                    $('#saveUserTipsModal').modal('show');
                    $scope.clear();
                }).catch(function (response) {
                    console.log(response);
                    // $scope.user.roles = roles[0
                    $scope.showTips = 'failed';
                    $('#saveUserModal').modal('hide');
                    $('#saveUserTipsModal').modal('show');
                }
            );
        };

        $scope.update = function(login) {
            User.get(login, function(result) {
              console.log(result)
              $scope.user = result;
              $('#editUserModal').modal('show');
            });
        };

        $scope.confirmUpdate = function() {
            var roles = [];
            roles.push($scope.user.roles);
            $scope.user.roles = roles;
            $scope.user.password = 'updatePassword'
            $scope.user.langKey = 'en'
            $scope.user.gender = parseInt($scope.user.gender)
            console.log($scope.user)
            User.update($scope.user,
                function(result) {
                    $scope.loadAll();
                    $scope.editTips = 'success';
                    $('#editUserModal').modal('hide');
                    $('#editUserTipsModal').modal('show');
                    $scope.clear();
                },
                function(err) {
                    $scope.editTips = 'failed';
                    $('#editUserModal').modal('hide');
                    $('#editUserTipsModal').modal('show');
                }
            )
        }

        $scope.delete = function(login) {
            User.get(login, function(result) {
              $scope.user = result;
              $('#deleteUserConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function(id) {
            userDelete.get({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteUserConfirmation').modal('hide');
                    $scope.clear();
                }
            );
        };

        $scope.clear = function() {
            $scope.user = {email: null, login: null, password: null, roles: null, userNo: null};
            $scope.saveForm.$setPristine();
            $scope.saveForm.$setUntouched();
        };
    });
