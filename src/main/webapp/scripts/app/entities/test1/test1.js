'use strict';

angular.module('onlineExamSystemApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('test1', {
                parent: 'entity',
                url: '/test1',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'onlineExamSystemApp.test1.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test1/test1s.html',
                        controller: 'Test1Controller'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test1');
                        return $translate.refresh();
                    }]
                }
            })
            .state('test1Detail', {
                parent: 'entity',
                url: '/test1/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'onlineExamSystemApp.test1.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/test1/test1-detail.html',
                        controller: 'Test1DetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('test1');
                        return $translate.refresh();
                    }]
                }
            });
    });
