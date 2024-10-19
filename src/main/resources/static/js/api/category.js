var app = angular.module("categoryApp", []);
app.controller("categoryCtrl", function($scope, $http) {
	$scope.form = {};
	$scope.items = [];
	$scope.products = [];
	$scope.genreProducts = []; // Khai báo biến này nếu chưa có
	$scope.galaries = [];

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
		$http.get("/rest/products").then(resp => {
			$scope.products = resp.data;
		})
		$http.get("/rest/genre_products").then(resp => {
			$scope.genreProducts = resp.data;
		});
		$http.get("/rest/galaries").then(resp => {
			$scope.galaries = resp.data;
		});

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
				text: "Vui lòng nhập tên danh mục",
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
		/*Validate*/
		if (!$scope.form.name) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng nhập tên danh mục",
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
		$http.put(`/rest/categories/${item.id}`, item).then(resp => {
			var index = $scope.items.findIndex(p => p.id == item.id);
			$scope.items[item] = item
			$scope.reset();
			Swal.fire({
				icon: "success",
				title: "Cập nhật Thành công",
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
		Swal.fire({
			title: "Bạn có chắc muốn xoá danh mục sản phẩm này không?",
			text: "Tất cả sản phẩm và các liên kết sẽ bị xóa!",
			icon: "warning",
			showCancelButton: true,
			confirmButtonColor: "#3085d6",
			cancelButtonColor: "#d33",
			confirmButtonText: "Có, Xóa ngay!"
		}).then((result) => {
			if (result.isConfirmed) {
				// Tìm tất cả Products liên quan
				const relatedProducts = $scope.products.filter(p => p.category.id === item.id);

				// Tạo các promise để xóa tất cả sản phẩm và liên kết
				const deletePromises = relatedProducts.map(p => {
					// Xóa genreProducts liên quan
					const deleteGenrePromises = $scope.genreProducts
						.filter(gp => gp.product.id === p.id)
						.map(gp => $http.delete(`/rest/genre_products/${gp.id}`));

					return Promise.all(deleteGenrePromises).then(() => {
						// Xóa galaries liên quan
						const deleteGalleryPromises = $scope.galaries
							.filter(g => g.product.id === p.id)
							.map(g => $http.delete(`/rest/galaries/${g.id}`));
						return Promise.all(deleteGalleryPromises).then(() => {
							// Sau khi đã xóa genreProducts và galaries, xóa sản phẩm
							return $http.delete(`/rest/products/${p.id}`);
						});
					});
				});

				// Khi tất cả sản phẩm đã được xóa, xóa danh mục
				Promise.all(deletePromises).then(() => {
					return $http.delete(`/rest/categories/${item.id}`);
				}).then(resp => {
					const index = $scope.items.findIndex(p => p.id === item.id);
					if (index !== -1) {
						$scope.items.splice(index, 1);
					}

					Swal.fire({
						icon: "success",
						title: "Xóa Thành công",
						text: "Danh mục sản phẩm và tất cả sản phẩm liên quan đã được xóa.",
					});

					$('#staticBackdrop').modal('hide');
					$scope.load_all();
				}).catch(error => {
					Swal.fire({
						icon: "error",
						title: "Lỗi",
						text: "Có lỗi xảy ra khi xóa sản phẩm hoặc danh mục: " + (error.data ? error.data.message : error.message),
					});
				});
			}
		});
	};


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
