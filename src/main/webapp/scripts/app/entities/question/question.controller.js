'use strict';

angular.module('onlineExamSystemApp')
    .controller('QuestionController', function ($scope, Question, ParseLinks) {
        $scope.questions = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Question.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.questions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Question.update($scope.question,
                function () {
                    $scope.loadAll();
                    $('#saveQuestionModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Question.get({id: id}, function(result) {
                $scope.question = result;
                $('#saveQuestionModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Question.get({id: id}, function(result) {
                $scope.question = result;
                $('#deleteQuestionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Question.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteQuestionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.question = {questionNo: null, questionName: null, questionType: null, subject: null, createTime: null, lastUpdateTime: null, deleted: null, code: null, author: null, optionA: null, optionB: null, optionC: null, optionD: null, answer: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
