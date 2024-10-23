var app = angular.module('galaryApp', []);
app.controller('galaryCtrl', function($scope, $http) {
	$scope.form = {};
	$scope.items = [];
	$scope.products = [];

	// Hàm tạo ID ngẫu nhiên
	$scope.generateRandomId = function(length) {
		let result = '';
		for (let i = 0; i < length; i++) {
			result += Math.floor(Math.random() * 10);
		}
		return result;
	};

	$scope.load_all = function() {
		var url = `/rest/galaries`;
		$http.get(url).then(resp => {
			$scope.items = resp.data;
		}).catch(error => {
			console.log("Error", error);
		});
		$http.get(`/rest/products`).then(resp => {
			$scope.products = resp.data;
		})
	}
	$scope.reset = function() {
		$scope.form = {
			created_at: new Date,
			image: "dowloadProd.png"
		};
		$scope.key = null;
	}

	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
	};

	$scope.create = function() {
		/*Validate*/
		if ($scope.form.image == 'dowloadProd.png') {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng nhập chọn ảnh sản phẩm",
				icon: "error"
			});
			return;
		}
		if ($scope.form.product == null) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng chọn sản phẩm",
				icon: "error"
			});
			return;
		}

		var item = angular.copy($scope.form);
		item.id = $scope.generateRandomId(6);
		var url = `/rest/galaries`;
		$http.post(url, item).then(resp => {
			$scope.items.push(item);
			console.log("Success", resp);
			Swal.fire({
				icon: "success",
				title: "Thêm Thành công",
				text: "Ảnh sản phẩm mới đã được thêm vào danh sách",
			});
			$scope.load_all();
			$scope.reset();
		}).catch(error => {
			Swal.fire({
				icon: "error",
				title: "Lỗi",
				text: "Thêm mới ảnh sản phẩm gặp trục trặc",
			});
		});
	}

	$scope.update = function() {
		var item = angular.copy($scope.form);
		var url = `/rest/galaries/${$scope.form.id}`;
		$http.put(url, item).then(resp => {
			var index = $scope.items.findIndex(item => item.id == $scope.form.id);
			$scope.items[index] = resp.data;
			console.log("Success", resp);
			Swal.fire({
				icon: "success",
				title: "Cập nhật Thành công",
				text: "Ảnh sản phẩm đã được cập nhật thành công",
			});
			$scope.load_all();
			$scope.reset();
		}).catch(error => {
			Swal.fire({
				icon: "error",
				title: "Lỗi",
				text: "Cập nhật ảnh sản phẩm gặp trục trặc",
			});
		});
	}

	$scope.delete = function(item) {
		Swal.fire({
			title: "Bạn có chắc muốn xoá ảnh sản phẩm này không?",
			text: "Bạn sẽ không thể hoàn tác lại ảnh sản phẩm này!",
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
					text: "Ảnh sản phẩm đã được xóa khỏi trang web",
				});
				$http.delete(`/rest/galaries/${item.id}`).then(resp => {
					var index = $scope.items.findIndex(p => p.id == item.id);
					$scope.items.splice(index, 1);
					$scope.reset();
				}).catch(error => {
					Swal.fire({
						icon: "error",
						title: "Lỗi",
						text: "Xóa ảnh sản phẩm gặp trục trặc",
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