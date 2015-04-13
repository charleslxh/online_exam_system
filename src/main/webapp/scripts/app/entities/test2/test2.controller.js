'use strict';

angular.module('onlineExamSystemApp')
    .controller('Test2Controller', function ($scope, Test2, Test1, ParseLinks) {
        $scope.test2s = [];
        $scope.test1s = Test1.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Test2.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.test2s = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Test2.update($scope.test2,
                function () {
                    $scope.loadAll();
                    $('#saveTest2Modal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Test2.get({id: id}, function(result) {
                $scope.test2 = result;
                $('#saveTest2Modal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Test2.get({id: id}, function(result) {
                $scope.test2 = result;
                $('#deleteTest2Confirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Test2.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTest2Confirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.test2 = {test2: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
