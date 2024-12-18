var app = angular.module("genreApp", []);
app.controller("genreCtrl", function($scope, $http) {
	$scope.form = {};
	$scope.items = [];

	// Hàm tạo ID ngẫu nhiên
	$scope.generateRandomId = function(length) {
		let result = '';
		for (let i = 0; i < length; i++) {
			result += Math.floor(Math.random() * 10);
		}
		return result;
	};

	$scope.load_all = function() {
		$http.get(`/rest/genres`).then(resp => {
			$scope.items = resp.data;
			console.log("Success", resp);
		}).catch(error => {
			console.log("Error", error);
		})
	}
	$scope.reset = function() {
		$scope.form = {};
		$scope.key = null;
	}

	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
	};

	$scope.create = function() {
		/*Validate*/
		if (!$scope.form.name) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng nhập tên thể loại",
				icon: "error"
			});
			return;
		}
		if (!$scope.form.decription) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng nhập mô tả",
				icon: "error"
			});
			return;
		}

		var item = angular.copy($scope.form);
		item.id = $scope.generateRandomId(6); // Tạo ID ngẫu nhiên 6 chữ số
		$http.post(`/rest/genres`, item).then(resp => {
			$scope.items.push(resp.data);
			$scope.reset();
			Swal.fire({
				icon: "success",
				title: "Thêm Thành công",
				text: "Thể loại sản phẩm mới đã được thêm vào danh sách",
			});
		}).catch(error => {
			Swal.fire({
				icon: "error",
				title: "Lỗi",
				text: "Thêm mới thể loại sản phẩm gặp trục trặc",
			});
		});
	}

	$scope.update = function() {
		/*Validate*/
		if (!$scope.form.name) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng nhập tên thể loại",
				icon: "error"
			});
			return;
		}
		if (!$scope.form.decription) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng nhập mô tả",
				icon: "error"
			});
			return;
		}

		var item = angular.copy($scope.form);
		$http.put(`/rest/genres/${item.id}`, item).then(resp => {
			var index = $scope.items.findIndex(p => p.id == item.id);
			$scope.items[item] = item
			$scope.reset();
			Swal.fire({
				icon: "success",
				title: "Cập nhật Thành công",
				text: "Thể loại sản phẩm đã được cập nhật",
			});

			$('#staticBackdrop').modal('hide');
			$scope.load_all();
		}).catch(error => {
			Swal.fire({
				icon: "error",
				title: "Lỗi",
				text: "Cập nhật thể loại sản phẩm gặp trục trặc",
			});
		})
	};


	$scope.delete = function(item) {
		Swal.fire({
			title: "Bạn có chắc muốn xoá thể loại sản phẩm này không?",
			text: "Bạn sẽ không thể hoàn tác lại thể loại sản phẩm này!",
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
					text: "Thể loại sản phẩm đã được xóa khỏi danh sách",
				});
				$http.delete(`/rest/genres/${item.id}`).then(resp => {
					var index = $scope.items.findIndex(p => p.id == item.id);
					$scope.items.splice(index, 1);
					$scope.reset();

					$('#staticBackdrop').modal('hide');
					$scope.load_all();
				}).catch(error => {
					Swal.fire({
						icon: "error",
						title: "Lỗi",
						text: "Xóa thể loại sản phẩm gặp trục trặc",
					});
				});
			}
		});
	}

	/*$scope.pager = {
		page: 0,
		size: 5,
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
