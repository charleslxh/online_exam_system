'use strict';

angular.module('onlineExamSystemApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('system', {
                parent: 'site',
                url: '/',
                data: {
                    roles: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/system/users.html',
                        controller: 'UserController'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        $translatePartialLoader.addPart('users');
                        return $translate.refresh();
                    }]
                }
            });
    });