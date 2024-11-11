var app = angular.module('reportApp', ['ngRoute']);

app.controller('reportCtrl', function($scope, $http) {

    $scope.totalRevenue = 0;
    $scope.totalUsers = 0;
    $scope.totalProduct = 0;
    $scope.selectedYear = new Date().getFullYear();
    $scope.revenueChart = null;

    $scope.fetchTotalRevenue = function() {
        $http.get('/api/revenue/total')
            .then(function(response) {
                $scope.totalRevenue = response.data;
            })
            .catch(function(error) {
                console.error("Có lỗi xảy ra khi gọi API tổng doanh thu: ", error);
            });
    };

    $scope.fetchTotalUsers = function() {
        $http.get('/api/customer/total')
            .then(function(response) {
                $scope.totalUsers = response.data;
            })
            .catch(function(error) {
                console.error("Có lỗi xảy ra khi gọi API tổng người dùng: ", error);
            });
    };

    $scope.fetchTotalProduct = function() {
        $http.get('/api/product/total')
            .then(function(response) {
                $scope.totalProduct = response.data;
            })
            .catch(function(error) {
                console.error("Có lỗi xảy ra khi gọi API tổng sản phẩm: ", error);
            });
    };

    $scope.createChart = function(months, revenues) {
        var ctxRevenue = document.getElementById('revenueChart').getContext('2d');

        if ($scope.revenueChart) {
            $scope.revenueChart.destroy();
        }

        $scope.revenueChart = new Chart(ctxRevenue, {
            type: 'bar',
            data: {
                labels: months,
                datasets: [{
                    label: 'Doanh Thu (VNĐ)',
                    data: revenues,
                    backgroundColor: 'rgba(54, 162, 235, 0.6)',
                    borderColor: 'rgba(54, 162, 235, 1)',
                    borderWidth: 2,
                    fill: false
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    y: {
                        beginAtZero: true,
                        title: {
                            display: true,
                        }
                    },
                    x: {
                        title: {
                            display: true,
                        }
                    }
                }
            }
        });
    };

    $scope.fetchRevenueData = function() {
        $http.get('/api/revenue')
            .then(function(response) {
                var revenueData = response.data;
                var months = Object.keys(revenueData);
                var revenues = Object.values(revenueData);
                $scope.createChart(months, revenues);
            })
            .catch(function(error) {
                console.error("Có lỗi xảy ra khi gọi API doanh thu: ", error);
            });
    };

    $scope.applyFilter = function() {

        var start = new Date($scope.startDate).toISOString().split("T")[0]; // YYYY-MM-DD
        var end = new Date($scope.endDate).toISOString().split("T")[0];

        $http.get('/api/revenue/filter', { params: { start: start, end: end } })
            .then(function(response) {
                var revenueData = response.data;
                var months = Object.keys(revenueData);
                var revenues = Object.values(revenueData);
                $scope.createChart(months, revenues);
            })
            .catch(function(error) {
                console.error("Có lỗi xảy ra khi gọi API doanh thu theo khoảng thời gian: ", error);
            });
    };

    $scope.fetchTotalRevenue();
    $scope.fetchTotalUsers();
    $scope.fetchTotalProduct();
    $scope.fetchRevenueData();
});
