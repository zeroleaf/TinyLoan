
"use strict";

var debt = angular.module('debt', ['ui.bootstrap']);

debt.controller('NavigationCtrl', function ($scope) {

    $scope.items = [
        'The first choice!',
        'And another choice for you.',
        'but wait! A third!'
    ];

    $scope.status = {
        isopen: false
    };

    $scope.toggled = function(open) {
        $log.log('Dropdown is now: ', open);
    };

    $scope.toggleDropdown = function($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.status.isopen = !$scope.status.isopen;
    };
});

debt.controller('PasswordCtrl', function ($scope) {
    $scope.fields = [
        { identify: 'old_password', label: '旧密码' },
        { identify: 'password',     label: '新密码', },
        { identify: 'confirm',      label: '确认密码'}
    ];
});
