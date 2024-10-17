var app = angular.module('app', ['ngRoute']);
app.controller('ctrl', function($scope, $http) {
	$scope.items = [];
	$scope.form = {};
	$scope.cutomer = {};
	$scope.error = '';
	var element = angular.element(document.getElementById('container'));

	$scope.loadAccount = function() {
		$http.get('/rest/customer/current').then(resp => {
			$scope.form = resp.data || {};
		});
	};

	$scope.reset = function() {
		$scope.form = {};
	};

	$scope.isValidEmail = function(email) {
		const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
		return emailPattern.test(email);
	};

	$scope.isValidPhone = function(phone) {
		const phonePattern = /^(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})$/;
		return phonePattern.test(phone);
	};

	$scope.create = function() {
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
			element.removeClass('active');
			$scope.reset();
		})
	};


	$scope.updateAccount = function() {
		if (!$scope.form.phone || !$scope.isValidPhone($scope.form.phone)) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng nhập số điện thoại hợp lệ",
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
		$http.put('/rest/customer/currentimg', $scope.form)
			.then(resp => {
				$scope.form = resp.data; // Cập nhật dữ liệu mới từ server
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
	$scope.sendResetEmail = function() {
	    if (!$scope.isValidEmail($scope.form.email)) {
	        Swal.fire({
	            title: "Lỗi",
	            text: "Vui lòng nhập email hợp lệ.",
	            icon: "error"
	        });
	        return;
	    }
	    const data = {
	        sendmail: $scope.form.email
	    };
	    $http.post('http://localhost:8080/send/mail', data)
	    .then(response => {
	        console.log("Response:", response); 
	        Swal.fire({
	            title: "Thành công",
	            text: "Email đã được gửi. Vui lòng kiểm tra hộp thư của bạn.",
	            icon: "success"
	        });
	        $scope.form.email = '';
	    })
	    .catch(error => {
	        console.error("Error:", error);
	            Swal.fire({
	                title: "Thành công",
	                text: "Email đã được gửi. Vui lòng kiểm tra hộp thư của bạn.",
	                icon: "success"
	            });
	    });
	};

	$scope.loadAccount();
});