var app = angular.module('dashApp', ['ngRoute']);
app.controller('dashCtrl', function($scope, $http) {
	$http.get('/api/dashboard/orders-today')
		.then(function(response) {
			$scope.ordersToday = response.data;
		});

	$http.get('/api/dashboard/revenue-today')
		.then(function(response) {
			$scope.revenueToday = response.data;
		});

	$http.get('/api/dashboard/inventory-count')
		.then(function(response) {
			$scope.inventoryCount = response.data;
		});

	$http.get('/api/dashboard/total-customers')
		.then(function(response) {
			$scope.totalCustomers = response.data;
		});

	$http.get('/api/dashboard/customers/order-count')
		.then(function(response) {
			$scope.customerOrderCounts = response.data;
		})
		.catch(function(error) {
			console.log('Error fetching customer order counts: ', error);
		});
});
