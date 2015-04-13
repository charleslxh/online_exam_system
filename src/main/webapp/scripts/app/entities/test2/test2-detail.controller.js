'use strict';

angular.module('onlineExamSystemApp')
    .controller('Test2DetailController', function ($scope, $stateParams, Test2, Test1) {
        $scope.test2 = {};
        $scope.load = function (id) {
            Test2.get({id: id}, function(result) {
              $scope.test2 = result;
            });
        };
        $scope.load($stateParams.id);
    });
