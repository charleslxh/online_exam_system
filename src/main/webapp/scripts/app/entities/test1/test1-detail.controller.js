'use strict';

angular.module('onlineExamSystemApp')
    .controller('Test1DetailController', function ($scope, $stateParams, Test1, Test2) {
        $scope.test1 = {};
        $scope.load = function (id) {
            Test1.get({id: id}, function(result) {
              $scope.test1 = result;
            });
        };
        $scope.load($stateParams.id);
    });
