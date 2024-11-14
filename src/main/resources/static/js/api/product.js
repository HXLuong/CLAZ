var app = angular.module("productApp", []);
app.controller("productCtrl", function($scope, $http) {
	$scope.form = {};
	$scope.items = [];
	$scope.cates = [];
	$scope.genres = [];
	$scope.genreProducts = [];
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
		$http.get(`/rest/products`).then(resp => {
			$scope.items = resp.data;
			console.log("Success", resp);
		}).catch(error => {
			console.log("Error", error);
		})
		$http.get("/rest/categories").then(resp => {
			$scope.cates = resp.data;
		})
		$http.get("/rest/genre_products").then(resp => {
			$scope.genreProducts = resp.data;
		})
		$http.get("/rest/galaries").then(resp => {
			$scope.galaries = resp.data;
		})

		$scope.loadGenres();
	}

	$scope.reset = function() {
		$scope.form = {
			created_at: new Date(),
			image: 'dowloadProd.png',
		};
		$scope.key = null;
	}

	$scope.edit = function(item) {
		$scope.form = angular.copy(item);

		// Lấy các thể loại đã liên kết với sản phẩm
		var relatedGenres = $scope.genreProducts
			.filter(gp => gp.product.id === item.id)
			.map(gp => gp.genre.name); // Lấy tên thể loại

		// Gán tên các thể loại vào form.selectedGenresString
		$scope.form.selectedGenresString = relatedGenres.join(', ');
	};

	$scope.loadGenres = function() {
		$http.get("/rest/genres").then(resp => {
			$scope.genres = resp.data;
		}).catch(error => {
			console.log("Error loading genres", error);
		});
	};

	// Đảm bảo mở modal đúng cách
	$scope.openGenreModal = function() {
		// Tải lại genres nếu cần thiết
		$scope.loadGenres();
		$('#chooseGenre').modal('show');
	};

	// Lưu thể loại đã chọn
	$scope.saveSelectedGenres = function() {
		var selectedGenres = $scope.genres.filter(g => g.selected);
		if (selectedGenres.length > 0) {
			$scope.form.selectedGenres = selectedGenres.map(g => ({
				id: g.id,
				name: g.name
			}));
		} else {
			$scope.form.selectedGenres = [];
		}
		$scope.form.selectedGenresString = selectedGenres.map(g => g.name).join(', ');
	};

	$scope.loadGenres();

	$scope.isValidPrice = function(price) {
		const pricePattern = /^[0-9]*$/;
		return pricePattern.test(price);
	};

	$scope.isValidDiscount = function(discount) {
		const discountPattern = /^(100|[1-9][0-9]|\d)$/;
		return discountPattern.test(discount);
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
		if (!$scope.form.name) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng nhập tên sản phẩm",
				icon: "error"
			});
			return;
		}
		if (!$scope.form.price || !$scope.isValidPrice($scope.form.price)) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng nhập giá là số nguyên dương",
				icon: "error"
			});
			return;
		}
		if (!$scope.form.discount || !$scope.isValidDiscount($scope.form.discount)) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng nhập phần trăm giảm giá là từ 0 - 100",
				icon: "error"
			});
			return;
		}
		if (!$scope.form.quantity || !$scope.isValidPrice($scope.form.quantity)) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng nhập số lượng là số nguyên dương",
				icon: "error"
			});
			return;
		}
		if ($scope.form.category == null) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng chọn danh mục",
				icon: "error"
			});
			return;
		}
		if (!$scope.form.selectedGenres || $scope.form.selectedGenres.length === 0) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng chọn ít nhất một thể loại sản phẩm",
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
		$http.post(`/rest/products`, item).then(resp => {
			$scope.items.push(resp.data);

			// thể loại đã chọn
			var selectedGenres = $scope.genres.filter(g => g.selected);
			if (selectedGenres.length > 0) {
				$scope.form.selectedGenres = selectedGenres.map(g => ({
					id: g.id,
					name: g.name
				}));
			} else {
				$scope.form.selectedGenres = [];
			}

			// lưu thể loại cho sản phẩm
			if ($scope.form.selectedGenres && $scope.form.selectedGenres.length > 0) {
				$scope.form.selectedGenres.forEach(genre => {
					var genreProduct = {
						id: $scope.generateRandomId(6), // Tạo ID ngẫu nhiên cho genreProduct
						name: genre.name, // Lưu tên thể loại
						product: { id: resp.data.id }, // ID sản phẩm vừa tạo
						genre: { id: genre.id }// ID thể loại đã chọn
					};

					$http.post(`/rest/genre_products`, genreProduct).then(re => {
						$scope.genreProducts.push(re.data);
						console.log("Đã lưu thể loại thành công");
					}).catch(error => {
						console.log("Lỗi khi lưu thể loại", error);
					});
				});
			}

			$scope.reset();
			Swal.fire({
				icon: "success",
				title: "Thêm Thành công",
				text: "Sản phẩm mới đã được thêm vào danh sách",
			});
		}).catch(error => {
			Swal.fire({
				icon: "error",
				title: "Lỗi",
				text: "Thêm mới sản phẩm gặp trục trặc",
			});
			console.log("cc");
		});
	}

	$scope.update = function() {
		/*Validate*/
		if ($scope.form.image == 'dowloadProd.png') {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng nhập chọn ảnh sản phẩm",
				icon: "error"
			});
			return;
		}
		if (!$scope.form.name) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng nhập tên sản phẩm",
				icon: "error"
			});
			return;
		}
		if (!$scope.form.price || !$scope.isValidPrice($scope.form.price)) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng nhập giá là số nguyên dương",
				icon: "error"
			});
			return;
		}
		if (!$scope.form.discount || !$scope.isValidDiscount($scope.form.discount)) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng nhập phần trăm giảm giá là từ 0 - 100",
				icon: "error"
			});
			return;
		}
		if (!$scope.form.quantity || !$scope.isValidPrice($scope.form.quantity)) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng nhập số lượng là số nguyên dương",
				icon: "error"
			});
			return;
		}
		if ($scope.form.category == null) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng chọn danh mục",
				icon: "error"
			});
			return;
		}
		if (!$scope.form.selectedGenres || $scope.form.selectedGenres.length === 0) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng chọn lại ít nhất một thể loại sản phẩm",
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
		// Xóa các genreProducts hiện tại
		const relatedGenreProducts = $scope.genreProducts.filter(gp => gp.product.id === item.id);
		const deletePromises = relatedGenreProducts.map(gp => {
			return $http.delete(`/rest/genre_products/${gp.id}`);
		});

		Promise.all(deletePromises).then(() => {
			return $http.put(`/rest/products/${item.id}`, item);
		}).then(resp => {
			var index = $scope.items.findIndex(p => p.id == item.id);
			$scope.items[index] = item;

			// Cập nhật thể loại mới
			var selectedGenres = $scope.genres.filter(g => g.selected);
			$scope.form.selectedGenres = selectedGenres.map(g => ({
				id: g.id,
				name: g.name
			}));

			if ($scope.form.selectedGenres.length > 0) {
				$scope.form.selectedGenres.forEach(genre => {
					var genreProduct = {
						id: $scope.generateRandomId(6), // Tạo ID ngẫu nhiên cho genreProduct
						name: genre.name,
						product: { id: resp.data.id },
						genre: { id: genre.id }
					};

					return $http.post(`/rest/genre_products`, genreProduct).then(re => {
						$scope.genreProducts.push(re.data);
						$scope.load_all();
					}).catch(error => {
						console.log("Lỗi khi lưu thể loại", error);
					});
				});
			}

			$scope.reset();
			Swal.fire({
				icon: "success",
				title: "Cập nhật sản phẩm Thành công",
				text: "Sản phẩm đã được cập nhật",
			});
			$('#staticBackdrop').modal('hide');
			$scope.load_all();
		}).catch(error => {
			Swal.fire({
				icon: "error",
				title: "Lỗi",
				text: "Cập nhật sản phẩm gặp trục trặc",
			});
		})
	};


	$scope.delete = function(item) {
		Swal.fire({
			title: "Bạn có chắc chắn muốn xóa sản phẩm này không?",
			text: "Bạn sẽ không thể hoàn tác lại hành động này!",
			icon: "warning",
			showCancelButton: true,
			confirmButtonColor: "#3085d6",
			cancelButtonColor: "#d33",
			confirmButtonText: "Có, xóa ngay!"
		}).then((result) => {
			if (result.isConfirmed) {
				$http.delete(`/rest/products/${item.id}`).then(resp => {
					var index = $scope.items.findIndex(p => p.id == item.id);
					$scope.items.splice(index, 1);
					Swal.fire({
						icon: "success",
						title: "Xóa thành công",
						text: "Sản phẩm đã được xóa khỏi danh sách",
					});
					$('#staticBackdrop').modal('hide');
					$scope.load_all();
				}).catch(error => {
					Swal.fire({
						icon: "error",
						title: "Lỗi",
						text: "Có lỗi xảy ra khi xóa sản phẩm.",
					});
				});
			}
		});
	};

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

	// Hàm sắp xếp
	$scope.sortColumn = 'id';
	$scope.reverse = false;
	$scope.sortBy = function(column) {
		if ($scope.sortColumn === column) {
			$scope.reverse = !$scope.reverse;
		} else {
			$scope.sortColumn = column;
			$scope.reverse = false;
		}
	};

	$scope.pager = {
		page: 0,
		size: 10,
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