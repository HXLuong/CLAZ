var app = angular.module('adminApp', ['ngRoute']);
app.controller('adminCtrl', function($scope, $http) {
	$scope.form = {};
	$scope.cutomer = {};
	$scope.error = '';
	
	$scope.loadAccount = function() {
		$http.get('/rest/staffs/current').then(resp => {
			$scope.form = resp.data || {};
		});
	};
	
	$scope.update = function() {
		var item = angular.copy($scope.form);
		var url = `/rest/staffs/${$scope.form.username}`;
		$http.put(url, item).then(resp => {
			var index = $scope.items.findIndex(item => item.username == $scope.form.username);
			$scope.items[index] = resp.data;
			console.log("Success", resp);

			$('#staticBackdrop').modal('hide');
		}).catch(error => {
			Swal.fire({
				icon: "success",
				title: "Cập nhật Thành công",
				text: "Tài khoản đã được cập nhật thành công",
			});
			$scope.loadAccount();
		});
	}
	
	$scope.imageChaged = function(files) {
		var data = new FormData();
		data.append('file', files[0]);
		$http.post('/rest/upload/images', data, {
			transformRequest: angular.identity,
			headers: { 'Content-Type': undefined }
		}).then(resp => {
			$scope.form.image = resp.data.name;
			$scope.updateAccountimg();
		}).catch(error => {
			Swal.fire({
				title: "Lỗi",
				text: "Lỗi hình ảnh",
				icon: "error"
			});
		});
	};
	
	$scope.updateAccountimg = function() {
		$http.put('/rest/staffs/currentimg', $scope.form)
			.then(resp => {
				$scope.form = resp.data; 
			})
			.catch(error => {
				Swal.fire({
					title: "Lỗi",
					text: "Lỗi cập nhật thông tin",
					icon: "error"
				});
				console.log(error);
			});
	};
	
	$scope.updateAccountPass = function() {
			if ($scope.form.password.length < 6) {
				$scope.error = 'Mật khẩu phải có ít nhất 6 ký tự';
				Swal.fire({
					title: "Lỗi",
					text: "Mật khẩu phải có ít nhất 6 ký tự.",
					icon: "error"
				});
				return;
			}
			if ($scope.form.password !== $scope.form.confirmPassword) {
				$scope.error = 'Mật khẩu không trùng khớp';
				Swal.fire({
					title: "Lỗi",
					text: "Xác nhận mật khẩu không trùng khớp",
					icon: "error"
				});
				container.classList.add("active");
				return;
			}
			$http.put('/rest/staffs/current', $scope.form)
				.then(resp => {
					$scope.form = resp.data;
					Swal.fire({
						title: "Success",
						text: "Sửa mật khẩu thành công",
						icon: "success"
					});
				})
		};

	$scope.loadAccount();
});