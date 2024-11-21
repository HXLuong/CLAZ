var app = angular.module('app', ['ngRoute']);
app.controller('ctrl', function($scope, $http, $routeParams) {
	$scope.items = [];
	$scope.form = {};
	$scope.cutomer = {};
	$scope.error = '';
	$scope.products = [];
	$scope.comments = [];
	$scope.listItem = [];
	$scope.username = "";
	$scope.email = "";
	$scope.totalPrice = 0;
	$scope.totalQuantity = 0;
	$scope.replyContent = '';
	$scope.commentLimit = 5;
	$scope.replyContent = {};

	var element = angular.element(document.getElementById('container'));

	$scope.loadAccount = function() {
		$http.get('/rest/customer/current').then(resp => {
			$scope.form = resp.data || {};
			$scope.username = resp.data.username;
			$scope.email = resp.data.email;
			$scope.loadCart();
			$scope.checkIfRated();
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

	$scope.isValidPassword = function(password) {
		const passwordRegex = /^(?=.*[\d!@#$%^&*()_+{}\[\]:;"'<>,.?/-])[A-Za-z\d!@#$%^&*()_+{}\[\]:;"'<>,.?/-]{8,}$/;
		return passwordRegex.test(password);
	};

	$scope.isValidUsername = function(username) {
		const usernameRegex = /[^a-zA-Z0-9]/;
		return usernameRegex.test(username);
	};

	$scope.emailSentAccount = false;
	$scope.sendCreateMail = function() {
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

		if (!$scope.form.username || $scope.form.username.length < 8 || $scope.isValidUsername($scope.form.username)) {
			Swal.fire({
				title: "Lỗi",
				text: "Tên đăng nhập phải có ít nhất 8 ký tự và không chứa ký tự đặc biệt",
				icon: "error"
			});
			return;
		}
		if (!$scope.form.password || !$scope.isValidPassword($scope.form.password)) {
			Swal.fire({
				title: "Lỗi",
				text: "Mật khẩu phải có ít nhất 8 ký tự, bao gồm ít nhất 1 số hoặc 1 ký tự đặc biệt.",
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

		// Send Mail
		const data = {
			sendmail: $scope.form.email
		};

		Swal.fire({
			icon: "success",
			title: "Mã xác nhận đang được gửi",
			text: "Vui lòng chờ trong giây lát.",
			showConfirmButton: false,
		});

		$http.post('/rest/customer/send/mail', data)
			.then(resp => {
				$scope.emailSentAccount = true;
				Swal.fire({
					title: "Thành công",
					text: "Mã xác nhận đã được gửi. Vui lòng kiểm tra hộp thư của bạn.",
					icon: "success"
				});
				$('#VerifyCreateAccount').modal('show');
			})
			.catch(error => {
				console.error("Error:", error);
				let errorMessage = "Có lỗi xảy ra khi gửi email.";
				if (error.data) {
					errorMessage = error.data.error;
				}
				Swal.fire({
					title: "Lỗi",
					text: errorMessage,
					icon: "error"
				});
				$scope.emailSentAccount = false;
			});
	};

	$scope.CreateAccount = function() {
		const verificationData = {
			email: $scope.form.email,
			code: $scope.form.code
		};
		if (!verificationData.email || !verificationData.code) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng nhập đầy đủ email và mã xác nhận.",
				icon: "error"
			});
			return;
		}
		$http.post('/rest/customer/verify/code', verificationData)
			.then(response => {
				Swal.fire({
					title: "Thành công",
					text: "Mã xác nhận chính xác!",
					icon: "success"
				}).then(() => {
					$('#VerifyCreateAccount').modal('hide');

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
					}).catch(err => {
						Swal.fire({
							title: "Lỗi",
							text: "Username hoặc Email đã tồn tại. Vui lòng sử dụng Username hoặc Email khác",
							icon: "error"
						});
					});
				});
			})
			.catch(error => {
				console.error("Error:", error);
				let errorMessage = "Mã xác nhận không chính xác.";
				if (error && error.data && error.data.error) {
					errorMessage = error.data.error;
				}
				Swal.fire({
					title: "Lỗi",
					text: errorMessage,
					icon: "error"
				});
			});
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
		if (!$scope.form.password || !$scope.isValidPassword($scope.form.password)) {
			Swal.fire({
				title: "Lỗi",
				text: "Mật khẩu phải có ít nhất 8 ký tự, bao gồm ít nhất 1 số hoặc 1 ký tự đặc biệt.",
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
					title: "Thành công",
					text: "Sửa mật khẩu thành công",
					icon: "success"
				});
			}).catch(err => {
				Swal.fire({
					title: "Lỗi",
					text: "Email đã tồn tại. Vui lòng sử dụng Email khác",
					icon: "error"
				});
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

	$scope.emailSent = false;
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

		Swal.fire({
			icon: "success",
			title: "Mã xác nhận đang được gửi",
			text: "Vui lòng chờ trong giây lát.",
			showConfirmButton: false,
		});

		$http.post('/send/mail', data)
			.then(resp => {
				$scope.emailSent = true;
				Swal.fire({
					title: "Thành công",
					text: "Mã xác nhận đã được gửi. Vui lòng kiểm tra hộp thư của bạn.",
					icon: "success"
				});
			})
			.catch(error => {
				console.error("Error:", error);
				let errorMessage = "Có lỗi xảy ra khi gửi email.";
				if (error.data) {
					errorMessage = error.data.error;
				}
				Swal.fire({
					title: "Lỗi",
					text: errorMessage,
					icon: "error"
				});
				$scope.emailSent = false;
			});
	};

	$scope.verifyCode = function() {
		const verificationData = {
			email: $scope.form.email,
			code: $scope.form.code
		};
		if (!verificationData.email || !verificationData.code) {
			Swal.fire({
				title: "Lỗi",
				text: "Vui lòng nhập đầy đủ email và mã xác nhận.",
				icon: "error"
			});
			return;
		}
		$http.post('/verify/code', verificationData)
			.then(response => {
				Swal.fire({
					title: "Thành công",
					text: "Mã xác nhận chính xác!",
					icon: "success"
				}).then(() => {
					$('#staticBackdrop').modal('hide');
					$('#changePassModal').modal('show');
				});
			})
			.catch(error => {
				console.error("Error:", error);
				let errorMessage = "Mã xác nhận không chính xác.";
				if (error && error.data && error.data.error) {
					errorMessage = error.data.error;
				}
				Swal.fire({
					title: "Lỗi",
					text: errorMessage,
					icon: "error"
				});
			});
	};

	$scope.resetPassword = function() {
		if (!$scope.form.password || $scope.form.password.length < 6) {
			Swal.fire({
				title: "Lỗi",
				text: "Mật khẩu mới phải có ít nhất 6 ký tự.",
				icon: "error"
			});
			return;
		}
		if ($scope.form.password !== $scope.form.confirmPassword) {
			Swal.fire({
				title: "Lỗi",
				text: "Xác nhận mật khẩu không trùng khớp.",
				icon: "error"
			});
			return;
		}

		const resetData = {
			newpass: $scope.form.password
		};

		$http.post('/reset/password', resetData)
			.then(response => {
				Swal.fire({
					title: "Thành công",
					text: "Mật khẩu đã được cập nhật thành công.",
					icon: "success"
				}).then(() => {
					$('#staticBackdrop').modal('hide');
				});
				$('#changePassModal').modal('hide');
			})
			.catch(error => {
				console.error("Error:", error);
				let errorMessage = "Có lỗi xảy ra khi cập nhật mật khẩu.";
				if (error && error.data && error.data.error) {
					errorMessage = error.data.error;
				}
				Swal.fire({
					title: "Lỗi",
					text: errorMessage,
					icon: "error"
				});
			});
	};


	$scope.loadAccount();


	// Cart
	$scope.loadCart = function(product) {
		$http.get(`/rest/carts/${$scope.username}`).then(resp => {
			$scope.listItem = resp.data;
			$scope.caculateTotal();
		}).catch(err => {
			console.error('Lỗi khi tải giỏ hàng:', err);
			Swal.fire({
				icon: "error",
				title: "Lỗi",
				text: "Không thể tải giỏ hàng.",
			});
		});
	}

	$scope.loadProducts = function() {
		$http.get('/rest/products').then(resp => {
			$scope.products = resp.data || [];
		}).catch(err => {
			console.error('Lỗi khi tải sản phẩm:', err);
			Swal.fire({
				icon: "error",
				title: "Lỗi",
				text: "Không thể tải sản phẩm.",
			});
		});
	};

	$scope.loadProducts();

	// Thêm giỏ hàng
	$scope.addItem = function(productID) {
		if (!$scope.username) {
			Swal.fire({
				icon: "error",
				title: "Bạn chưa đăng nhập",
				text: "Vui lòng đăng nhập trước khi thêm sản phẩm.",
			});
			return;
		}
		$http.post(`/rest/carts/add?productID=${productID}&username=${$scope.username}`).then(resp => {
			const item = $scope.listItem.find(item => item.productID === productID);
			const product = $scope.products.find(p => p.id === productID);
			if (item) {
				item.quantity += 1;
				if (item.quantity > product.quantity) {
					Swal.fire({
						icon: "warning",
						title: "Không thể thêm sản phẩm trong giỏ hàng",
						text: "Số lượng sản phẩm đã đến giới hạn",
					});
					item.quantity = product.quantity;
					return;
				}
			}
			$scope.caculateTotal();
			Swal.fire({
				icon: "success",
				title: "Thành công",
				text: "Sản phẩm đã được thêm vào giỏ hàng",
			});
			$scope.loadCart();
		}).catch(err => {
			console.error('Lỗi khi thêm sản phẩm:', err);
		});
	};

	// Mua ngay
	$scope.buyNow = function(productID) {
		if (!$scope.username) {
			Swal.fire({
				icon: "error",
				title: "Bạn chưa đăng nhập",
				text: "Vui lòng đăng nhập trước khi thêm sản phẩm.",
			});
			return;
		}
		$http.post(`/rest/carts/add?productID=${productID}&username=${$scope.username}`).then(resp => {
			const item = $scope.listItem.find(item => item.productID === productID);
			const product = $scope.products.find(p => p.id === productID);
			if (item) {
				item.quantity += 1;
				if (item.quantity > product.quantity) {
					Swal.fire({
						icon: "warning",
						title: "Không thể thêm sản phẩm trong giỏ hàng",
						text: "Số lượng sản phẩm đã đến giới hạn",
					});
					item.quantity = product.quantity;
					return;
				}
			}
			$scope.caculateTotal();
			window.location.href = 'cart-index';
			$scope.loadCart();
		}).catch(err => {
			console.error('Lỗi khi thêm sản phẩm:', err);
		});
	};

	// Tăng số lượng giỏ hàng
	$scope.increaseQty = function(productID) {
		if (!$scope.username) {
			Swal.fire({
				icon: "error",
				title: "Bạn chưa đăng nhập",
				text: "Vui lòng đăng nhập trước khi thêm sản phẩm.",
			});
			return;
		}
		$http.post(`/rest/carts/add?productID=${productID}&username=${$scope.username}`).then(resp => {
			const item = $scope.listItem.find(item => item.productID === productID);
			const product = $scope.products.find(p => p.id === productID);
			if (item) {
				item.quantity += 1;
				if (item.quantity > product.quantity) {
					Swal.fire({
						icon: "warning",
						title: "Không thể thêm sản phẩm trong giỏ hàng",
						text: "Số lượng sản phẩm đã đến giới hạn",
					});
					item.quantity = product.quantity;
					return;
				}
			}
			$scope.caculateTotal();
			$scope.loadCart();
		}).catch(err => {
			console.error('Lỗi khi thêm sản phẩm:', err);
		});
	};

	$scope.deleteItem = function(itemID) {
		Swal.fire({
			title: "Bạn có chắc muốn xoá sản phẩm này khỏi giỏ hàng không?",
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
					text: "Sản phẩm đã được xóa khỏi giỏ hàng",
				});
				const username = $scope.username;
				$http.delete(`/rest/carts/delete/${itemID}`).then(resp => {
					$scope.loadCart(username);
				})
			}
		});
	}

	$scope.updateItem = function(itemID) {
		const username = $scope.username;
		$http.put(`/rest/carts/update/${itemID}`).then(resp => {
			$scope.loadCart(username);
			console.log('update success')
		})
	}

	$scope.onQuantityChange = function(item) {
		item.quantity = Number(item.quantity);

		if (item.quantity < 0 || isNaN(item.quantity)) {
			item.quantity = 1;
			Swal.fire({
				icon: "warning",
				title: "Số lượng không hợp lệ",
				text: "Số lượng không thể nhỏ hơn 1",
			});
		}

		const product = $scope.products.find(p => p.id === item.productID);

		if (product && item.quantity > product.quantity) {
			item.quantity = product.quantity;
			Swal.fire({
				icon: "warning",
				title: "Số lượng vượt quá số lượng có sẵn",
				text: "Bạn không thể chọn nhiều hơn số lượng sản phẩm có trong kho",
			});
		}

		$scope.caculateTotal();
	};



	$scope.caculateTotal = function() {
		$scope.totalPrice = 0;
		$scope.totalQuantity = 0;
		for (let i = 0; i < $scope.listItem.length; i++) {
			$scope.totalPrice += $scope.listItem[i].price * (1 - $scope.listItem[i].discount / 100) * $scope.listItem[i].quantity;
			$scope.totalQuantity += $scope.listItem[i].quantity;
		}
	}


	// Comment
	$scope.generateRandomId = function(length) {
		let result = '';
		for (let i = 0; i < length; i++) {
			result += Math.floor(Math.random() * 10);
		}
		return result;
	};

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

	$scope.submitComment = function(commentContent, productId) {
		if (!$scope.username) {
			Swal.fire({
				icon: "error",
				title: "Lỗi",
				text: "Bạn cần đăng nhập để bình luận.",
			});
			return;
		}
		if ($scope.commentId) {
			$http.get(`/comments/${$scope.commentId}`)
				.then(resp => {
					const existingComment = resp.data;
					existingComment.content = commentContent;
					$http.put(`/comments/${existingComment.id}`, existingComment)
						.then(resp => {
							$scope.commentContent = '';
							$scope.commentId = null;
							Swal.fire({
								icon: "success",
								title: "Thành công",
								text: "Bình luận đã được cập nhật.",
							});
							$scope.loadComments();
						})
						.catch(err => {
							console.error('Lỗi khi cập nhật bình luận:', err);
							Swal.fire({
								icon: "error",
								title: "Lỗi",
								text: "Có lỗi xảy ra khi cập nhật bình luận.",
							});
						});
				})
				.catch(err => {
					console.error('Lỗi khi lấy bình luận:', err);
					Swal.fire({
						icon: "error",
						title: "Lỗi",
						text: "Có lỗi xảy ra khi lấy bình luận.",
					});
				});
		} else {
			var item = angular.copy($scope.form);
			item.id = $scope.generateRandomId(6);
			const commentDTO = {
				id: item.id,
				productId: productId,
				content: commentContent.new,
				username: $scope.username
			};
			$http.post('/comments/add', commentDTO)
				.then(resp => {
					$scope.commentContent = '';
					Swal.fire({
						icon: "success",
						title: "Thành công",
						text: "Bình luận đã được thêm.",
					});
					$scope.loadComments();
				})
				.catch(err => {
					console.error('Lỗi khi thêm bình luận:', err);
					Swal.fire({
						icon: "error",
						title: "Lỗi",
						text: "Có lỗi xảy ra khi thêm bình luận.",
					});
				});
		}
	};

	$scope.deleteComment = function(item) {
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
						var index = $scope.comments.findIndex(c => c.id == item.id);
						if (index > -1) {
							$scope.comments.splice(index, 1);
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
					});
			}
		});
	};

	$scope.loadComments = function() {
		$http.get(`/comments/comments`)
			.then(resp => {
				$scope.comments = resp.data;
			})
			.catch(err => {
				console.error('Lỗi khi tải bình luận:', err);
			});
	};

	// Replies
	$scope.replies = {};

	$scope.loadReplies = function(commentId) {
		$http.get(`/comments/${commentId}/replies`)
			.then(function(response) {
				$scope.replies[commentId] = response.data;
			});
	};

	$scope.addReply = function(commentId, replyContent) {
		if (!$scope.username) {
			Swal.fire({
				icon: "error",
				title: "Lỗi",
				text: "Bạn cần đăng nhập để phản hồi.",
			});
			return;
		}
		var item = angular.copy($scope.form);
		item.id = $scope.generateRandomId(6);

		const replyDTO = {
			id: item.id,
			commentId: commentId,
			content: replyContent,
			username: $scope.username
		};

		$http.post(`/comments/${commentId}/replies`, replyDTO)
			.then(resp => {
				$scope.loadReplies(commentId);
				$scope.replyContent = '';
				Swal.fire({
					icon: "success",
					title: "Thành công",
					text: "Phản hồi đã được thêm.",
				});
				const commentItem = $scope.comments.find(c => c.id === commentId);
				if (commentItem) commentItem.showReplyInput = false;
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
		if (!$scope.username) {
			Swal.fire({
				icon: "error",
				title: "Lỗi",
				text: "Bạn cần đăng nhập để cập nhật phản hồi.",
			});
			return;
		}

		const replyDTO = {
			id: replyId,
			content: replyContent,
			username: $scope.username
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
				$scope.loadComments();

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
						$scope.loadComments();
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

	$scope.showMoreComments = function() {
		$scope.commentLimit += 5;
	};

	$scope.toggleReplyInput = function(commentItem, reply) {
		if (!$scope.username) {
			Swal.fire({
				icon: "error",
				title: "Lỗi",
				text: "Bạn cần đăng nhập để có thể trả lời.",
			});
			return;
		}

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
	$scope.loadComments();


	// Rating
	$scope.ratingValue = 0;
	$scope.hasRated = false;
	$scope.canRateAgain = false;

	$scope.canRate = false;

	$scope.checkPurchaseStatus = function(callback) {
		$http.get('/ratings/checkPurchaseStatus', {
			params: { username: $scope.username }
		}).then(function(response) {
			console.log("Purchase Status Response: ", response.data);
			$scope.canRate = response.data;
			if (callback) callback();
		});
	};

	$scope.checkIfRated = function() {
		$http.get('/ratings/checkIfRated', {
			params: { username: $scope.username }
		}).then(function(response) {
			console.log("Check If Rated Response: ", response.data);

			$scope.hasRated = response.data.hasRated;
			$scope.canRateAgain = response.data.canRateAgain;
			if (!$scope.hasRated) {
				$scope.checkPurchaseStatus();
			}
		}).catch(err => {
			console.error('Error checking if rated:', err);
		});
	};
	$scope.setRating = function(value) {
		$scope.ratingValue = value;
	};
	$scope.addRating = function(productId) {
		$scope.checkPurchaseStatus(() => {
			if ($scope.ratingValue <= 0) {
				Swal.fire({
					icon: "error",
					title: "Lỗi",
					text: "Bạn cần chọn ít nhất 1 sao để đánh giá sản phẩm.",
				});
				return;
			}
			const ratingDTO = {
				id: $scope.generateRandomId(6),
				productId: productId,
				numberStars: $scope.ratingValue,
				username: $scope.username
			};
			$http.post('/ratings/add', ratingDTO)
				.then(resp => {
					Swal.fire({
						icon: "success",
						title: "Thành công",
						text: "Đánh giá sản phẩm thành công.",
					});
					$scope.loadRating();
					$scope.checkIfRated();
					$scope.ratingValue = 0;
				})
				.catch(err => {
					console.error('Lỗi khi thêm đánh giá:', err);
					Swal.fire({
						icon: "error",
						title: "Lỗi",
						text: "Có lỗi xảy ra khi đánh giá.",
					});
				});
		});
	};
	$scope.rating = [];
	$scope.loadRating = function() {
		$http.get(`/ratings/rating`)
			.then(resp => {
				$scope.rating = resp.data;
				$scope.calculateTotalAndAverageRatings();
			});
	};
	$scope.calculateTotalAndAverageRatings = function() {
		if ($scope.rating.length === 0) {
			$scope.totalRatings = 0;
			$scope.averageRating = 0;
			return;
		}
		let total = 0;
		$scope.rating.forEach(function(rating) {
			total += rating.number_Stars;
		});
		$scope.totalRatings = $scope.rating.length;
		$scope.averageRating = (total / $scope.rating.length).toFixed(1);
	};
	$scope.loadRating();

	// Payment VNPay
	$scope.paymentByVNPay = function() {
		const requestData = {
			totalPrice: $scope.totalPrice,
			username: $scope.username
		};

		if ($scope.totalPrice > 20000000) {
			Swal.fire({
				title: "Lỗi",
				text: "Đơn hàng của bạn không được vượt quá 20,000,000đ",
				icon: "warning"
			});
			return;
		}

		$http.post('/rest/carts/pay', requestData)
			.then(function(response) {
				console.log('Dữ liệu phản hồi:', response.data);
				if (response.data) {
					window.location.href = response.data;
				} else {
					alert('Không nhận được URL thanh toán. Vui lòng thử lại.');
				}
			})
			.catch(function(error) {
				console.error('Lỗi khi thực hiện thanh toán:', error);
				alert('Có lỗi xảy ra trong quá trình thanh toán. Vui lòng thử lại.');
			});

	}
});