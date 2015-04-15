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
            }).state('userDetail', {
                parent: 'site',
                url: '/user/:login',
                data: {
                    roles: [],
                    pageTitle: 'onlineExamSystemApp.users.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/system/user-detail.html',
                        controller: 'UserDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('users');
                        return $translate.refresh();
                    }]
                }
            });;
    });