'use strict';

angular.module('onlineExamSystemApp')
    .controller('TEACHERDetailController', function ($scope, $stateParams, TEACHER, T_USE) {
        $scope.tEACHER = {};
        $scope.load = function (id) {
            TEACHER.get({id: id}, function(result) {
              $scope.tEACHER = result;
            });
        };
        $scope.load($stateParams.id);
    });
