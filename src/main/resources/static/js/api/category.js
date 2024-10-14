var app = angular.module("myapp", []);
app.controller("myctrl", function($scope, $http) {
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
		$http.get(`/rest/categories`).then(resp => {
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
		var item = angular.copy($scope.form);
		item.id = $scope.generateRandomId(6); // Tạo ID ngẫu nhiên 6 chữ số
		$http.post(`/rest/categories`, item).then(resp => {
			$scope.items.push(resp.data);
			$scope.reset();
			Swal.fire({
				icon: "success",
				title: "Thêm Thành công",
				text: "Danh mục sản phẩm mới đã được thêm vào danh sách",
			});
		}).catch(error => {
			Swal.fire({
				icon: "error",
				title: "Lỗi",
				text: "Thêm mới danh mục sản phẩm gặp trục trặc",
			});
		});
	}

	$scope.update = function() {
		var item = angular.copy($scope.form);
		$http.put(`/rest/categories/${item.id}`, item).then(resp => {
			var index = $scope.items.findIndex(p => p.id == item.id);
			$scope.items[item] = item
			$scope.reset();
			Swal.fire({
				icon: "success",
				title: "Cập nhật danh mục sản phẩm Thành công",
				text: "Danh mục sản phẩm đã được cập nhật",
			});

			$('#staticBackdrop').modal('hide');
			$scope.load_all();
		}).catch(error => {
			Swal.fire({
				icon: "error",
				title: "Lỗi",
				text: "Cập nhật danh mục sản phẩm gặp trục trặc",
			});
		})
	};


	$scope.delete = function(item) {
		$http.delete(`/rest/categories/${item.id}`).then(resp => {
			var index = $scope.items.findIndex(p => p.id == item.id);
			$scope.items.splice(index, 1);
			$scope.reset();
			Swal.fire({
				icon: "success",
				title: "Xóa danh mục sản phẩm Thành công",
				text: "danh mục sản phẩm đã được xóa khỏi danh sách",
			});

			$('#staticBackdrop').modal('hide');
			$scope.load_all();
		}).catch(error => {
			Swal.fire({
				icon: "error",
				title: "Lỗi",
				text: "Xóa danh mục sản phẩm gặp trục trặc",
			});
		});
	}

	$scope.pager = {
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
	}


	$scope.load_all();
	$scope.reset();
});
