'use strict';

angular.module('onlineExamSystemApp')
    .controller('STUDENTDetailController', function ($scope, $stateParams, STUDENT) {
        $scope.sTUDENT = {};
        $scope.load = function (id) {
            STUDENT.get({id: id}, function(result) {
              $scope.sTUDENT = result;
            });
        };
        $scope.load($stateParams.id);
    });
