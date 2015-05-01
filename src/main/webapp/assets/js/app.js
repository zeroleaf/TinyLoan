
"use strict";

var myApp = angular.module('myApp', []);

myApp.controller('RegisterCtrl', function ($scope) {
    $scope.fields = [
        { identify: 'nick',     label: '用户名',   type: 'text'     },
        { identify: 'password', label: '密码',     type: 'password' },
        { identify: 'cfm_pass', label: '确认密码', type: 'password' }
    ];
});