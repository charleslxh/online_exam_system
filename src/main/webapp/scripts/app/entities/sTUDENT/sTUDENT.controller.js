'use strict';

angular.module('onlineExamSystemApp')
    .controller('STUDENTController', function ($scope, STUDENT, ParseLinks) {
        $scope.sTUDENTs = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            STUDENT.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.sTUDENTs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            STUDENT.update($scope.sTUDENT,
                function () {
                    $scope.loadAll();
                    $('#saveSTUDENTModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            STUDENT.get({id: id}, function(result) {
                $scope.sTUDENT = result;
                $('#saveSTUDENTModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            STUDENT.get({id: id}, function(result) {
                $scope.sTUDENT = result;
                $('#deleteSTUDENTConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            STUDENT.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteSTUDENTConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.sTUDENT = {user_no: null, gender: null, age: null, avatar_url: null, school: null, classess: null, address: null, phone: null, description: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
