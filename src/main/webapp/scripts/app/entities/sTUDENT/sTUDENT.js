'use strict';

angular.module('onlineExamSystemApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('sTUDENT', {
                parent: 'entity',
                url: '/sTUDENT',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'onlineExamSystemApp.sTUDENT.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/sTUDENT/sTUDENTs.html',
                        controller: 'STUDENTController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sTUDENT');
                        return $translate.refresh();
                    }]
                }
            })
            .state('sTUDENTDetail', {
                parent: 'entity',
                url: '/sTUDENT/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'onlineExamSystemApp.sTUDENT.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/sTUDENT/sTUDENT-detail.html',
                        controller: 'STUDENTDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sTUDENT');
                        return $translate.refresh();
                    }]
                }
            });
    });
