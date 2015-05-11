
"use strict";

var admin = angular.module('admin', ['tinyloan']);

//TODO - Point 服务端能接收 POST 请求的当前做法, 略显麻烦
admin.config(['$httpProvider', function ($httpProvider) {
    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded; charset=UTF-8';
}]);

admin.directive('tyAudit', ['$http', function ($http) {
    return {
        link: function (scope, element) {
            element.bind('click', function () {
                var type = element.hasClass('pass') ? 2 : 4;
                var val  = element.val();

                $http
                    .post('/rest/laf/status', "id=" + val + "&status=" + type)
                    .success(function () {
                        var children = element.parent().children();
                        angular.forEach(children, function (value) {
                            value.setAttribute('disabled', 'disabled');
                        });
                });
            })
        }
    }
}]);

admin.controller('DebtRecordCtrl', ['$scope', function ($scope) {

    $scope.page = new PageCreater();

    $scope.formatTime = formatTime;

    function loadInvestorProfits(page) {
        jQuery.get('/rest/platform/debt_record', { page: page }, function (data) {
            var d = JSON.parse(data);

            if (d['totalNumber'] === 0) {
                jQuery('#info').css('display', 'block');
                jQuery('#detail').css('display', 'none');
            } else {
                jQuery('#info').css('display', 'none');
                jQuery('#detail').css('display', 'block');
            }

            $scope.$apply(function () {
                $scope.page = new PageCreater(d);
            });
        });
    }

    $scope.pageChanged = function() {
        loadInvestorProfits($scope.page.pageNumber);
    };

    $scope.pageChanged();
}]);

admin.controller('RaRecordCtrl', ['$scope', function ($scope) {

    $scope.page = new PageCreater();

    $scope.formatTime = formatTime;

    function loadInvestorProfits(page) {
        jQuery.get('/rest/platform/ra_record', { page: page }, function (data) {
            var d = JSON.parse(data);

            if (d['totalNumber'] === 0) {
                jQuery('#info').css('display', 'block');
                jQuery('#detail').css('display', 'none');
            } else {
                jQuery('#info').css('display', 'none');
                jQuery('#detail').css('display', 'block');
            }

            $scope.$apply(function () {
                $scope.page = new PageCreater(d);
            });
        });
    }

    $scope.pageChanged = function() {
        loadInvestorProfits($scope.page.pageNumber);
    };

    $scope.pageChanged();
}]);