'use strict';

angular.module('onlineExamSystemApp')
    .controller('Test1Controller', function ($scope, Test1, Test2, ParseLinks) {
        $scope.test1s = [];
        $scope.test2s = Test2.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Test1.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.test1s = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Test1.update($scope.test1,
                function () {
                    $scope.loadAll();
                    $('#saveTest1Modal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Test1.get({id: id}, function(result) {
                $scope.test1 = result;
                $('#saveTest1Modal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Test1.get({id: id}, function(result) {
                $scope.test1 = result;
                $('#deleteTest1Confirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Test1.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTest1Confirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.test1 = {testname: null, password: null, fullName: null, createTime: null, laseUpdateTime: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
