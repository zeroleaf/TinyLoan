
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
    }]);

