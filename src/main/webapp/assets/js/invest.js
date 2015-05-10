
"use strict";

// JQuery 不使用 $ 作为前缀.
$.noConflict();

var invest = angular.module('invest', ['ui.bootstrap', 'tinyloan']);

invest.controller('NavigationCtrl', ['$scope', function ($scope) {

    // TODO 这部分代码重复, 如何重构?
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
}]);

invest.controller('ModalCtrl', ['$scope', '$modal', function ($scope, $modal) {
    $scope.balance = jQuery('#balance').val();
    $scope.open = function ($event, size) {
        var modalInstance = $modal.open({
            animation: true,
            templateUrl: '/templates/modalInvest.html',
            controller: 'InvestModalCtrl',
            size: size,
            resolve: {
                opts: function () {
                    return {
                        balance: $scope.balance,
                        id: $event.target.id,
                        quantity: $event.target.value
                    }
                }
            }
        });
    }
}]);

invest.controller('InvestModalCtrl', ['$scope', '$modalInstance', 'opts', function ($scope, $modalInstance, opts) {

    $scope.balance  = opts.balance;
    $scope.id       = opts.id;
    $scope.quantity = opts.quantity;

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };

    $scope.invest = function () {

        var _error = angular.element('#error');
        var amount = parseInt($scope.amount);

        if (isNaN(amount) || amount < 1 || amount > $scope.quantity) {
            _error.removeClass('toggled');
            _error.text("请正确输入 1 至 " + $scope.quantity + " 之间的整数");
            return;
        }

        if ($scope.balance < amount * 1000.00) {
            _error.removeClass('toggled');
            _error.text("您账户余额不足, 请充值后操作!");
            return;
        }

        jQuery.post('/invest/action', { id: $scope.id, quantity: amount }, function () {
            $scope.cancel();
            //刷新该网页, 重新载入数据
            window.location.href = window.location.href.replace(/\?.*/g, '') + "?refresh=true";
        });
    }
}]);

invest.controller('RefundCtrl', ['$scope', function ($scope) {

    $scope.page = new PageCreater();

    $scope.formatTime = formatTime;

    function loadInvestorProfits(page) {
        jQuery.get('/rest/investor/profit', { page: page }, function (data) {
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

invest.controller('InvestCtrl', ['$scope', function ($scope) {

    $scope.page = new PageCreater();

    $scope.formatTime = formatTime;

    function loadInvestorProfits(page) {
        jQuery.get('/rest/investor/my_investment', { page: page }, function (data) {
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