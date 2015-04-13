'use strict';

angular.module('onlineExamSystemApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('test2', {
                parent: 'entity',
                url: '/test2',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'onlineExamSystemApp.test2.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test2/test2s.html',
                        controller: 'Test2Controller'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test2');
                        return $translate.refresh();
                    }]
                }
            })
            .state('test2Detail', {
                parent: 'entity',
                url: '/test2/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'onlineExamSystemApp.test2.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test2/test2-detail.html',
                        controller: 'Test2DetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test2');
                        return $translate.refresh();
                    }]
                }
            });
    });
