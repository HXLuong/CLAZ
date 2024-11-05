var app = angular.module("commentApp", []);
app.controller("commentCtrl", function($scope, $http) {
	$scope.form = {};
	$scope.items = [];
	$scope.username = "";
	$scope.replyContent = '';
	$scope.replyContent = {};

	// Hàm tạo ID ngẫu nhiên
	$scope.generateRandomId = function(length) {
		let result = '';
		for (let i = 0; i < length; i++) {
			result += Math.floor(Math.random() * 10);
		}
		return result;
	};

	$scope.loadStaff = function() {
		var url = `/rest/staffs/current`;
		$http.get(url).then(resp => {
			$scope.items = resp.data;
			$scope.username = resp.data.username;
			$scope.image = resp.data.image;
		}).catch(error => {
			console.log("Error", error);
		});
	}

	$scope.load_all = function() {
		$http.get(`/comments`).then(resp => {
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
		$scope.loadReplies(item.id);
	};

	$scope.delete = function(item) {
		Swal.fire({
			title: "Bạn có chắc muốn xóa bình luận này không?",
			text: "Bạn sẽ không thể hoàn tác lại bình luận này!",
			icon: "warning",
			showCancelButton: true,
			confirmButtonColor: "#3085d6",
			cancelButtonColor: "#d33",
			confirmButtonText: "Có, Xóa ngay!"
		}).then((result) => {
			if (result.isConfirmed) {
				$http.delete(`/comments/${item.id}`)
					.then(resp => {
						var index = $scope.items.findIndex(c => c.id == item.id);
						if (index > -1) {
							$scope.items.splice(index, 1);
						}
						Swal.fire({
							icon: "success",
							title: "Xóa thành công",
							text: "Bình luận và các phản hồi đã được xóa.",
						});
					}).catch(error => {
						Swal.fire({
							icon: "error",
							title: "Lỗi",
							text: "Có lỗi xảy ra khi xóa bình luận.",
						});
						console.log("sasdasdaf " + error);
					});
			}
		});
	};

	$scope.replies = {};

	$scope.editComment = function(commentItem) {
		commentItem.isEditing = true;
		$scope.commentContent = commentItem.content;
		$scope.commentId = commentItem.id;
	};


	$scope.cancelEdit = function(commentItem) {
		commentItem.isEditing = false;
	};

	$scope.toggleReplyEdit = function(reply) {
		reply.isEditing = !reply.isEditing;
		if (reply.isEditing) {
			$scope.replyContent[reply.id] = reply.content;
		} else {
			delete $scope.replyContent[reply.id];
		}
	};

	$scope.cancelReply = function(reply) {
		reply.isEditing = false;
	};

	$scope.loadReplies = function(commentId) {
		$http.get(`/comments/${commentId}/replies`)
			.then(function(response) {
				$scope.replies[commentId] = response.data;
			});
	};

	$scope.addReply = function(commentId, replyContent) {
		if (!replyContent) {
			Swal.fire({
				icon: "error",
				title: "Lỗi",
				text: "Nội dung phản hồi không được bỏ trống.",
			});
			return;
		}

		const replyDTO = {
			id: $scope.generateRandomId(6),
			commentId: commentId,
			content: replyContent,
			username_staff: $scope.username
		};

		$http.post(`/comments/${commentId}/Staffreplies`, replyDTO)
			.then(resp => {
				$scope.loadReplies(commentId);
				$scope.replyContent = '';
				Swal.fire({
					icon: "success",
					title: "Thành công",
					text: "Phản hồi đã được thêm.",
				});
				$scope.loadReplies();
				$scope.load_all();
				$('#staticBackdrop').modal('hide');
			})
			.catch(err => {
				Swal.fire({
					icon: "error",
					title: "Lỗi",
					text: "Có lỗi xảy ra khi thêm phản hồi.",
				});
			});
	};

	$scope.updateReply = function(replyId, replyContent) {
		const replyDTO = {
			id: replyId,
			content: replyContent,
			username_staff: $scope.username
		};

		$http.put(`/comments/replies/${replyId}`, replyDTO)
			.then(resp => {
				let commentId;
				for (let key in $scope.replies) {
					if ($scope.replies[key].some(reply => reply.id === replyId)) {
						commentId = key;
						break;
					}
				}
				$scope.loadReplies();
				$scope.load_all();
				$('#staticBackdrop').modal('hide');
				Swal.fire({
					icon: "success",
					title: "Thành công",
					text: "Phản hồi đã được cập nhật.",
				});

			})
			.catch(err => {
				Swal.fire({
					icon: "error",
					title: "Lỗi",
					text: "Có lỗi xảy ra khi cập nhật phản hồi.",
				});
				console.log(err);
			});
	};

	$scope.deleteReply = function(reply) {
		Swal.fire({
			title: "Bạn có chắc muốn xóa phản hồi này không?",
			text: "Bạn sẽ không thể hoàn tác lại phản hồi này!",
			icon: "warning",
			showCancelButton: true,
			confirmButtonColor: "#3085d6",
			cancelButtonColor: "#d33",
			confirmButtonText: "Có, Xóa ngay!"
		}).then((result) => {
			if (result.isConfirmed) {
				$http.delete(`/comments/replies/${reply.id}`)
					.then(resp => {
						Swal.fire({
							icon: "success",
							title: "Xóa thành công",
							text: "Phản hồi đã được xóa.",
						});
						$scope.loadReplies();
						$scope.load_all();
						$('#staticBackdrop').modal('hide');
					}).catch(error => {
						Swal.fire({
							icon: "error",
							title: "Lỗi",
							text: "Có lỗi xảy ra khi xóa phản hồi.",
						});
					});
			}
		});
	};

	$scope.toggleReplyInput = function(commentItem, reply) {
		commentItem.showReplyInput = !commentItem.showReplyInput;

		if (commentItem.showReplyInput) {
			if (reply) {
				if (reply.customer) {
					$scope.replyContent = '@' + reply.customer.fullname + ' ';
				} else if (reply.staff) {
					$scope.replyContent = '@' + reply.staff.fullname + ' ';
				}
			} else {
				$scope.replyContent = (commentItem.customer.fullname ? '@' + commentItem.customer.fullname + ' ' : '');
			}
		} else {
			$scope.replyContent = '';
		}
	};

	$scope.loadReplies();
	$scope.loadStaff();
	$scope.load_all();
	$scope.reset();
});