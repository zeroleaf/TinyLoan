
"use strict";

var myApp = angular.module('myApp', ['ngAnimate', 'toastr', 'ui.bootstrap', 'tinyloan']);

myApp.controller('RegisterCtrl', function ($scope) {
    $scope.fields = [
        { identify: 'nick',     label: '用户名',   type: 'text'     },
        { identify: 'password', label: '密码',     type: 'password' },
        { identify: 'cfm_pass', label: '确认密码', type: 'password' }
    ];
});

myApp.controller('PasswordCtrl', ['$scope', 'toastr', function ($scope, toastr) {
    $scope.fields = [
        { identify: 'old_password', label: '旧密码' },
        { identify: 'password',     label: '新密码' },
        { identify: 'confirm',      label: '确认密码'}
    ];

    //TODO 更好的显示该提示信息
    var success = document.getElementById("success");
    if (success) {
        toastr.success('密码修改成功', '提示');
    }
}]);
