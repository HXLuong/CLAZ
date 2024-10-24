var app = angular.module('slideApp', []);
app.controller('slideCtrl', function($scope, $http) {
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
		var url = `/rest/slides`;
		$http.get(url).then(resp => {
			$scope.items = resp.data;
		}).catch(error => {
			console.log("Error", error);
		});
	}
	$scope.reset = function() {
		$scope.form = {
			created_at: new Date,
			image: "dowloadSlide.png"
		};
		$scope.key = null;
	}

	$scope.edit = function(item) {
		$scope.form = angular.copy(item);
	};

	$scope.create = function() {
		/*Validate*/
		if ($scope.form.image == 'dowloadSlide.png') {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng nhập chọn ảnh slide",
				icon: "error"
			});
			return;
		}
		
		if ($scope.items.length > 17) {
			Swal.fire({
				title: "Lỗi",
				text: "Số lượng slide đã tới giới hạn",
				icon: "error"
			});
			return;
		}

		var item = angular.copy($scope.form);
		item.id = $scope.generateRandomId(6);
		var url = `/rest/slides`;
		$http.post(url, item).then(resp => {
			$scope.items.push(item);
			console.log("Success", resp);
			Swal.fire({
				icon: "success",
				title: "Thêm Thành công",
				text: "Slide mới đã được thêm vào trang web",
			});
			$scope.reset();
		}).catch(error => {
			Swal.fire({
				icon: "error",
				title: "Lỗi",
				text: "Thêm mới slide gặp trục trặc",
			});
		});
	}

	$scope.update = function() {
		var item = angular.copy($scope.form);
		var url = `/rest/slides/${$scope.form.id}`;
		$http.put(url, item).then(resp => {
			var index = $scope.items.findIndex(item => item.id == $scope.form.id);
			$scope.items[index] = resp.data;
			console.log("Success", resp);
			Swal.fire({
				icon: "success",
				title: "Cập nhật Thành công",
				text: "Slide đã được cập nhật thành công",
			});
		}).catch(error => {
			Swal.fire({
				icon: "error",
				title: "Lỗi",
				text: "Cập nhật slide gặp trục trặc",
			});
		});
	}

	$scope.delete = function(item) {
		Swal.fire({
			title: "Bạn có chắc muốn xoá slide này không?",
			text: "Bạn sẽ không thể hoàn tác lại slide này!",
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
					text: "Slide đã được xóa khỏi trang web",
				});
				$http.delete(`/rest/slides/${item.id}`).then(resp => {
					var index = $scope.items.findIndex(p => p.id == item.id);
					$scope.items.splice(index, 1);
					$scope.reset();
				}).catch(error => {
					Swal.fire({
						icon: "error",
						title: "Lỗi",
						text: "Xóa slide gặp trục trặc",
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