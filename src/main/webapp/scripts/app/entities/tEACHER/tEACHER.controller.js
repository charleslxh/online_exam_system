'use strict';

angular.module('onlineExamSystemApp')
    .controller('TEACHERController', function ($scope, TEACHER, T_USE, ParseLinks) {
        $scope.tEACHERs = [];
        $scope.t_uses = T_USE.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            TEACHER.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.tEACHERs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            TEACHER.update($scope.tEACHER,
                function () {
                    $scope.loadAll();
                    $('#saveTEACHERModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            TEACHER.get({id: id}, function(result) {
                $scope.tEACHER = result;
                $('#saveTEACHERModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            TEACHER.get({id: id}, function(result) {
                $scope.tEACHER = result;
                $('#deleteTEACHERConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            TEACHER.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTEACHERConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.tEACHER = {teacher_no: null, gender: null, age: null, avatar_url: null, school: null, classes: null, address: null, phone: null, description: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
