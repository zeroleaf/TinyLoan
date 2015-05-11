
"use strict";

var debt = angular.module('debt', ['ui.bootstrap', 'tinyloan']);

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
        { identify: 'password',     label: '新密码' },
        { identify: 'confirm',      label: '确认密码'}
    ];
});


debt.controller('LoanCtrl', ['$scope', function ($scope) {
    $scope.timeLimits = [
        { limit: 3,  rate: 8,  interest: 1000 * 0.08 * 3 / 12  },
        { limit: 6,  rate: 9,  interest: 1000 * 0.09 * 6 / 12  },
        { limit: 12, rate: 12, interest: 1000 * 0.12 * 12 / 12 }
    ];

    $scope.timeLimit = $scope.timeLimits[0];
}]);


debt.controller('IndexCtrl', ['$scope', function ($scope) {

    $scope.refund = function ($event) {
        var $btn = jQuery($event.target);
        var balance = parseFloat(jQuery('#balance').text());
        if (parseFloat($btn.data('refund-balance')) > balance) {
            alert('您的账户余额不足, 请充值后重试');
            return;
        }

        var refId = $btn.data('ref-id');
        jQuery.post('/debt/refund', { refId: refId }, function () {
            refresh();
        });
    }
}]);

debt.controller('DebtCtrl', ['$scope', function ($scope) {
    $scope.page = new PageCreater();

    $scope.formatTime = formatTime;

    function loadInvestorProfits(page) {
        jQuery.get('/rest/debtor/my_debt', { page: page }, function (data) {
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