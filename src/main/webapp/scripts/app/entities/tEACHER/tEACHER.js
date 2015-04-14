'use strict';

angular.module('onlineExamSystemApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('tEACHER', {
                parent: 'entity',
                url: '/tEACHER',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'onlineExamSystemApp.tEACHER.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tEACHER/tEACHERs.html',
                        controller: 'TEACHERController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('tEACHER');
                        return $translate.refresh();
                    }]
                }
            })
            .state('tEACHERDetail', {
                parent: 'entity',
                url: '/tEACHER/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'onlineExamSystemApp.tEACHER.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tEACHER/tEACHER-detail.html',
                        controller: 'TEACHERDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('tEACHER');
                        return $translate.refresh();
                    }]
                }
            });
    });
