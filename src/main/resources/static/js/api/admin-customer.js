var app = angular.module('customerApp', []);
app.controller('customerCtrl', function($scope, $http) {
	$scope.form = {};
	$scope.items = [];
	$scope.carts = [];
	$scope.comments = [];
	$scope.rating = [];
	$scope.replies = [];
	$scope.orders = [];
	$scope.orderDetails = [];
	
	$scope.load_all = function() {
		var url = `/rest/customers`;
		$http.get(url).then(resp => {
			$scope.items = resp.data;
		}).catch(error => {
			console.log("Error", error);
		});
	}
	$scope.reset = function() {
		$scope.form = {
			created_at: new Date,
			image: "profile.jpg"
		};
		$scope.key = null;
	}
	$scope.edit = function(username) {
		var url = `/rest/customers/${username}`;
		$http.get(url).then(resp => {
			$scope.form = resp.data;
			console.log("Success", resp);
		}).catch(error => {
			console.log("Error", error);
		});
	}
	$scope.isValidEmail = function(email) {
		const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
		return emailPattern.test(email);
	};
	$scope.isValidPhone = function(phone) {
		const phonePattern = /^(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})$/;
		return phonePattern.test(phone);
	};
	$scope.create = function() {
		/*Validate*/
		if (!$scope.form.username) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng nhập tên đăng nhập",
				icon: "error"
			});
			return;
		}
		if (!$scope.form.fullname) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng nhập họ và tên",
				icon: "error"
			});
			return;
		}
		if (!$scope.form.password || $scope.form.password.length < 6) {
			Swal.fire({
				title: "Lỗi",
				text: "Đăng ký không thành công. Mật khẩu phải có ít nhất 6 ký tự",
				icon: "error"
			});
			return;
		}
		if (!$scope.form.email || !$scope.isValidEmail($scope.form.email)) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng nhập email hợp lệ",
				icon: "error"
			});
			return;
		}
		if (!$scope.form.phone || !$scope.isValidPhone($scope.form.phone)) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng nhập số điện thoại hợp lệ",
				icon: "error"
			});
			return;
		}
		if ($scope.form.gender == null) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng chọn giới tính",
				icon: "error"
			});
			return;
		}

		var item = angular.copy($scope.form);
		var url = `/rest/customers`;
		$http.post(url, item).then(resp => {
			$scope.items.push(item);
			console.log("Success", resp);
			Swal.fire({
				icon: "success",
				title: "Thêm Thành công",
				text: "Tài khoản mới đã được thêm vào danh sách",
			});
			$scope.reset();
		}).catch(error => {
			Swal.fire({
				title: "Lỗi",
				text: "Username hoặc Email đã tồn tại. Vui lòng sử dụng Username hoặc Email khác",
				icon: "error"
			});
		});
	}

	$scope.update = function() {
		/*Validate*/
		if (!$scope.form.username) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng nhập tên đăng nhập",
				icon: "error"
			});
			return;
		}
		if (!$scope.form.fullname) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng nhập họ và tên",
				icon: "error"
			});
			return;
		}
		if (!$scope.form.password || $scope.form.password.length < 6) {
			Swal.fire({
				title: "Lỗi",
				text: "Đăng ký không thành công. Mật khẩu phải có ít nhất 6 ký tự",
				icon: "error"
			});
			return;
		}
		if (!$scope.form.email || !$scope.isValidEmail($scope.form.email)) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng nhập email hợp lệ",
				icon: "error"
			});
			return;
		}
		if (!$scope.form.phone || !$scope.isValidPhone($scope.form.phone)) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng nhập số điện thoại hợp lệ",
				icon: "error"
			});
			return;
		}
		if ($scope.form.gender == null) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng chọn giới tính",
				icon: "error"
			});
			return;
		}
		var item = angular.copy($scope.form);
		var url = `/rest/customers/${$scope.form.username}`;
		$http.put(url, item).then(resp => {
			var index = $scope.items.findIndex(item => item.username == $scope.form.username);
			$scope.items[index] = resp.data;
			console.log("Success", resp);
			Swal.fire({
				icon: "success",
				title: "Cập nhật Thành công",
				text: "Tài khoản đã được cập nhật thành công",
			});
			$('#staticBackdrop').modal('hide');
			$scope.load_all();
		}).catch(error => {
			Swal.fire({
				title: "Lỗi",
				text: "Email đã tồn tại. Vui lòng sử dụng Email khác",
				icon: "error"
			});
		});
	}
	$scope.delete = function(username) {
		Swal.fire({
			title: "Bạn có chắc muốn xoá tài khoản này không?",
			text: "Bạn sẽ không thể hoàn tác lại tài khoản này!",
			icon: "warning",
			showCancelButton: true,
			confirmButtonColor: "#3085d6",
			cancelButtonColor: "#d33",
			confirmButtonText: "Có, Xóa ngay!"
		}).then((result) => {
			if (result.isConfirmed) {
				Swal.fire({
					icon: "success",
					title: "Xóa Thành công",
					text: "Tài khoản đã được xóa khỏi danh sách",
				});
				var url = `/rest/customers/${username}`;
				$http.delete(url).then(resp => {
					var index = $scope.items.findIndex(item => item.username == username);
					$scope.items.splice(index, 1);
					$scope.reset();
					$('#staticBackdrop').modal('hide');
					$scope.load_all();
				}).catch(error => {
					Swal.fire({
						icon: "error",
						title: "Lỗi",
						text: "Xóa tài khoản gặp trục trặc",
					});
				});
			}
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
		}).catch(error => {
			Swal.fire({
				icon: "error",
				title: "Lỗi",
				text: "Lỗi hình ảnh",
			});
		})
	}
	/*$scope.pager = {
		page: 0,
		size: 3,
		get items() {
			var start = this.page * this.size;
			return $scope.items.slice(start, start + this.size);
		},
		get count() {
			return Math.ceil(1.0 * $scope.items.length / this.size);
		},
		first() {
			this.page = 0;
		},
		prev() {
			this.page--;
			if (this.page < 0) {
				this.last();
			}
		},
		next() {
			this.page++;
			if (this.page >= this.count) {
				this.first();
			}
		},
		last() {
			this.page = this.count - 1;
		}
	}*/
	$scope.load_all();
	$scope.reset();
});