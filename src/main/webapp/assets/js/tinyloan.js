
"use strict";

angular.module('tinyloan', ['ui.bootstrap'])

    .controller('TyModalCtrl', ['$scope', '$modal', function ($scope, $modal) {
        var balance = jQuery('#balance').text();

        $scope.openRechargeModal = function (size) {
            var modalInstance = $modal.open({
                animation: true,
                templateUrl: '/templates/recharge.html',
                controller: 'RechargeCtrl',
                size: size,
                resolve: {
                    opts: function () {
                        return {
                            balance: balance
                        }
                    }
                }
            });
        };

        $scope.openAdvanceModal = function (size) {
            var modalInstance = $modal.open({
                animation: true,
                templateUrl: '/templates/modalAdvance.html',
                controller: 'AdvanceCtrl',
                size: size,
                resolve: {
                    opts: function () {
                        return {
                            balance: balance
                        }
                    }
                }
            });
        }
    }])

    .controller('RechargeCtrl', ['$scope', '$modalInstance', 'opts', function ($scope, $modalInstance, opts) {

        $scope.balance = opts.balance;

        $scope.payMethods = [
            { name: '支付宝', value: 'ZFB' },
            { name: '财付通', value: 'CFT' },
            { name: '网银',   value: 'WY'  }
        ];
        $scope.pay = $scope.payMethods[0].value;

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        $scope.recharge = function () {

            if ($scope.charge === undefined) {
                jQuery('#modal-error').text('请输入正确的金额!');
                return;
            }

            jQuery.post('/rest/asset/recharge', { charge: $scope.charge, pay: $scope.pay }, function () {
                $scope.cancel();
                //刷新该网页, 重新载入数据
                window.location.href = window.location.href.replace(/\?.*/g, '') + "?refresh=true";
            });
        }
    }])

    .controller('AdvanceCtrl', ['$scope', '$modalInstance', 'opts', function ($scope, $modalInstance, opts) {

        $scope.balance = opts.balance;

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        $scope.advance = function () {

            var _modalError = jQuery('#modal-error');
            var amount = parseFloat($scope.amount);
            if (isNaN(amount) || amount < 0) {
                _modalError.text('请正确输入提现金额');
                return;
            }
            if (amount > $scope.balance) {
                _modalError.text('您的提现金额超出您的余额, 请重新输入!');
                return;
            }

            if ($scope.credit === undefined) {
                _modalError.text('请输入正确的提现银行卡号');
                return;
            }

            jQuery.post('/rest/asset/advance', { amount: amount, credit: $scope.credit }, function () {
                $scope.cancel();
                window.location.href = window.location.href.replace(/\?.*/g, '') + "?refresh=true";
            });
        }
    }])


    .controller('AmountFlowCtrl', ['$scope', function ($scope) {

        function PageCreater(d) {
            d = d || {};
            return {
                totalNumber: d['totalNumber'] || 0,
                pageSize: d['pageSize'] || 0,
                pageNumber: d['pageNumber'] || 1,
                content: d['content'] || [],
                offset: function () {
                    return (this.pageNumber - 1) * this.pageSize;
                }
            }
        }

        $scope.page = new PageCreater();

        function loadAmountFlows(page) {
            jQuery.post('/rest/user/afs', { page: page }, function (data) {
                var d = JSON.parse(data);

                $scope.page = new PageCreater(d);

                if (d['totalNumber'] === 0) {
                    jQuery('#info').css('display', 'block');
                    jQuery('#detail').css('display', 'none');
                } else {
                    jQuery('#info').css('display', 'none');
                    jQuery('#detail').css('display', 'block');
                }

                $scope.$apply(function ($scope) {
                    $scope.page = new PageCreater(d);
                });
            });
        }

        $scope.pageChanged = function() {
            loadAmountFlows($scope.page.pageNumber);
        };

        $scope.pageChanged();

    }])

