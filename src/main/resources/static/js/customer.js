var app = angular.module('app', ['ngRoute']);
app.controller('ctrl', function ($scope, $http) {
	$scope.items = [];
	$scope.form = {};
	$scope.cutomer = {};
	$scope.error = '';

	$scope.loadAccount = function () {
		$http.get('/rest/customer/current').then(resp => {
			$scope.form = resp.data || {};
		});
	};

	$scope.reset = function () {
		$scope.form = {};
	};

	$scope.isValidEmail = function (email) {
		const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
		return emailPattern.test(email);
	};

	$scope.create = function () {
		$scope.form = $scope.form || {};

		if (!$scope.form.fullname) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng nhập họ và tên.",
				icon: "error"
			});
			return;
		}
		if (!$scope.form.email || !$scope.isValidEmail($scope.form.email)) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng nhập email hợp lệ.",
				icon: "error"
			});
			return;
		}
		if (!$scope.form.username) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng nhập tên đăng nhập.",
				icon: "error"
			});
			return;
		}
		if (!$scope.form.password || $scope.form.password.length < 6) {
			Swal.fire({
				title: "Lỗi",
				text: "Đăng ký không thành công. Mật khẩu phải có ít nhất 6 ký tự.",
				icon: "error"
			});
			return;
		}
		if ($scope.form.password !== $scope.form.confirmPassword) {
			Swal.fire({
				title: "Lỗi",
				text: "Đăng ký không thành công. Xác nhận mật khẩu không trùng khớp.",
				icon: "error"
			});
			return;
		}

		var item = angular.copy($scope.form);
		$http.post(`/rest/customer`, item).then(resp => {
			$scope.items.push(resp.data);
			Swal.fire({
				title: "Good job!",
				text: "Đăng ký thành công",
				icon: "success"
			});
			$scope.reset();
		})
	};


	$scope.updateAccount = function () {
		var phonePattern = /^[0-9]{10}$/;
		if (!$scope.form.phone || !phonePattern.test($scope.form.phone)) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng nhập số điện (10 chữ số)",
				icon: "error"
			});
			return;
		} else {
			$http.put('/rest/customer/current', $scope.form)
				.then(resp => {
					$scope.form = resp.data;
					Swal.fire({
						title: "Good job!",
						text: "Sửa tài khoản thành công",
						icon: "success"
					});
				})
		}
	};


	$scope.updateAccountPass = function () {
		if ($scope.form.password.length < 6) {
			$scope.error = 'Mật khẩu phải có ít nhất 6 ký tự';
			Swal.fire({
				title: "Lỗi",
				text: "Mật khẩu phải có ít nhất 6 ký tự.",
				icon: "error"
			});
			return;
		}
		// Kiểm tra xác nhận mật khẩu
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
		$http.put('/rest/customer/current', $scope.form)
			.then(resp => {
				$scope.form = resp.data;
				Swal.fire({
					title: "Success",
					text: "Sửa mật khẩu thành công",
					icon: "success"
				});
			})
	};
	$scope.imageChaged = function (files) {
		var data = new FormData();
		data.append('file', files[0]);

		$http.post('/rest/upload/image', data, {
			transformRequest: angular.identity,
			headers: { 'Content-Type': undefined }
		}).then(resp => {
			$scope.form.image = resp.data.name; 
			$scope.updateAccountimg(); 
		}).catch(error => {
			alert("Lỗi hình ảnh!");
			console.log(error);
		});
	};

	$scope.updateAccountimg = function () {
		$http.put('/rest/customer/currentimg', $scope.form)
			.then(resp => {
				$scope.form = resp.data; // Cập nhật dữ liệu mới từ server
			})
			.catch(error => {
				alert("Lỗi cập nhật thông tin!");
				console.log(error);
			});
	};


	$scope.loadAccount();
});