'use strict';

angular.module('onlineExamSystemApp')
    .controller('QuestionDetailController', function ($scope, $stateParams, Question) {
        $scope.question = {};
        $scope.load = function (id) {
            Question.get({id: id}, function(result) {
              $scope.question = result;
            });
        };
        $scope.load($stateParams.id);
    });
