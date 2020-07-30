$(document).ready(function() {
	$('.delete-book').on('click', function (){
	    var path = 'http://localhost:8080/admin/book/remove';
		
		var id=$(this).attr('id');
		console.log(id);
		bootbox.confirm({
			message: "Are you sure to remove this book? It can't be undone.",
			buttons: {
				cancel: {
					label:'<i class="fa fa-times"></i> Cancel',
					className: 'btn-success'
				},
				confirm: {
					label:'<i class="fa fa-check"></i> Confirm',
					className: 'btn-danger'
				}
			},
			callback: function(confirmed) {
				if(confirmed) {
					$.ajax({
						type: 'POST',
						url: path,
						headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
						data: {
							idbook : id
						},
						success: function(res) {
							if(res == "ok"){
								location.reload();
							}
						}
					});
				}
			}
		});
	});
	
	$('.delete-user').on('click', function (){
	    var path = 'http://localhost:8080/admin/user/remove';
		
		var id=$(this).attr('id');
		console.log(id);
		bootbox.confirm({
			message: "Are you sure to remove this user? It can't be undone.",
			buttons: {
				cancel: {
					label:'<i class="fa fa-times"></i> Cancel',
					className: 'btn-success'
				},
				confirm: {
					label:'<i class="fa fa-check"></i> Confirm',
					className: 'btn-danger'
				}
			},
			callback: function(confirmed) {
				if(confirmed) {
					$.ajax({
						type: 'POST',
						url: path,
						headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
						data: {
							iduser : id
						},
						success: function(res) {
							if(res == "ok"){
								location.reload();
							}
						}
					});
				}
			}
		});
	});
	
	$('.delete-order').on('click', function (){
	    var path = 'http://localhost:8080/admin/order/remove';
		
		var id=$(this).attr('id');
		console.log(id);
		bootbox.confirm({
			message: "Are you sure to remove this order? It can't be undone.",
			buttons: {
				cancel: {
					label:'<i class="fa fa-times"></i> Cancel',
					className: 'btn-success'
				},
				confirm: {
					label:'<i class="fa fa-check"></i> Confirm',
					className: 'btn-danger'
				}
			},
			callback: function(confirmed) {
				if(confirmed) {
					$.ajax({
						type: 'POST',
						url: path,
						headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
						data: {
							idorder : id
						},
						success: function(res) {
							if(res == "ok"){
								location.reload();
							}
						}
					});
				}
			}
		});
	});
	
	$('.delete-category').on('click', function (){
	    var path = 'http://localhost:8080/admin/category/remove';
		
		var id=$(this).attr('id');
		console.log(id);
		bootbox.confirm({
			message: "Are you sure to remove this category? It can't be undone.",
			buttons: {
				cancel: {
					label:'<i class="fa fa-times"></i> Cancel',
					className: 'btn-success'
				},
				confirm: {
					label:'<i class="fa fa-check"></i> Confirm',
					className: 'btn-danger'
				}
			},
			callback: function(confirmed) {
				if(confirmed) {
					$.ajax({
						type: 'POST',
						url: path,
						headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
						data: {
							idcategory : id
						},
						success: function(res) {
							if(res == "ok"){
								location.reload();
							}
						}
					});
				}
			}
		});
	});
	
	$('.delete-cmrt').on('click', function (){
	    var path = 'http://localhost:8080/admin/cmrt/remove';
		
		var id=$(this).attr('id');
		console.log(id);
		bootbox.confirm({
			message: "Are you sure to remove this review? It can't be undone.",
			buttons: {
				cancel: {
					label:'<i class="fa fa-times"></i> Cancel',
					className: 'btn-success'
				},
				confirm: {
					label:'<i class="fa fa-check"></i> Confirm',
					className: 'btn-danger'
				}
			},
			callback: function(confirmed) {
				if(confirmed) {
					$.ajax({
						type: 'POST',
						url: path,
						headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
						data: {
							idcmrt : id
						},
						success: function(res) {
							if(res == "ok"){
								location.reload();
							}
						}
					});
				}
			}
		});
	});
	

	
	$('#deleteBookSelected').click(function() {
		var idList= $('.checkboxBook');
		var bookIdList=[];
		for (var i = 0; i < idList.length; i++) {
			if(idList[i].checked==true) {
				bookIdList.push(idList[i]['id'])
			}
		}
		
		console.log(bookIdList);
		
		
	    var path = 'http://localhost:8080/admin/book/removeList';
	  
	    bootbox.confirm({
			message: "Are you sure to remove all selected books? It can't be undone.",
			buttons: {
				cancel: {
					label:'<i class="fa fa-times"></i> Cancel'
				},
				confirm: {
					label:'<i class="fa fa-check"></i> Confirm'
				}
			},
			callback: function(confirmed) {
				if(confirmed) {
					$.ajax({
						type: 'POST',
						url: path,
						headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
						data: JSON.stringify(bookIdList),
						contentType: "application/json",
						success: function(res) {
							console.log(res); 
							location.reload()
							},
						error: function(res){
							console.log(res); 
							}
					});
				}
			}
		});
	});
	
	
	$('#deleteCategorySelected').click(function() {
		var idList= $('.checkboxCategory');
		var categoryIdList=[];
		for (var i = 0; i < idList.length; i++) {
			if(idList[i].checked==true) {
				categoryIdList.push(idList[i]['id'])
			}
		}
		
		console.log(categoryIdList);
		
		
	    var path = 'http://localhost:8080/admin/category/removeList';
	  
	    bootbox.confirm({
			message: "Are you sure to remove all selected categories? It can't be undone.",
			buttons: {
				cancel: {
					label:'<i class="fa fa-times"></i> Cancel'
				},
				confirm: {
					label:'<i class="fa fa-check"></i> Confirm'
				}
			},
			callback: function(confirmed) {
				if(confirmed) {
					$.ajax({
						type: 'POST',
						url: path,
						headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
						data: JSON.stringify(categoryIdList),
						contentType: "application/json",
						success: function(res) {
							console.log(res); 
							location.reload()
							},
						error: function(res){
							console.log(res); 
							}
					});
				}
			}
		});
	});
	
	$("#selectAllBooks").click(function() {
		if($(this).prop("checked")==true) {
			$(".checkboxBook").prop("checked",true);
		} else if ($(this).prop("checked")==false) {
			$(".checkboxBook").prop("checked",false);
		}
	})
	
	
	$("#selectAllCategory").click(function() {
		if($(this).prop("checked")==true) {
			$(".checkboxCategory").prop("checked",true);
		} else if ($(this).prop("checked")==false) {
			$(".checkboxCategory").prop("checked",false);
		}
	})	
		
	
	
	$('.ban-user').on('click', function (){
	    var path = 'http://localhost:8080/admin/user/ban';
		
		var id=$(this).attr('id');
		console.log(id);
		bootbox.confirm({
			message: "Are you sure to ban this user?",
			buttons: {
				cancel: {
					label:'<i class="fa fa-times"></i> Cancel',
					className: 'btn-success'
				},
				confirm: {
					label:'<i class="fa fa-check"></i> Confirm',
					className: 'btn-danger'
				}
			},
			callback: function(confirmed) {
				if(confirmed) {
					$.ajax({
						type: 'POST',
						url: path,
						headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
						data: {
							iduser : id
						},
						success: function(res) {
							if(res == "ok"){
								location.reload();
							}
						}
					});
				}
			}
		});
	});
	$('.unban-user').on('click', function (){
	    var path = 'http://localhost:8080/admin/user/unban';
		
		var id=$(this).attr('id');
		console.log(id);
		bootbox.confirm({
			message: "Are you sure to unban this user?",
			buttons: {
				cancel: {
					label:'<i class="fa fa-times"></i> Cancel',
					className: 'btn-success'
				},
				confirm: {
					label:'<i class="fa fa-check"></i> Confirm',
					className: 'btn-danger'
				}
			},
			callback: function(confirmed) {
				if(confirmed) {
					$.ajax({
						type: 'POST',
						url: path,
						headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
						data: {
							iduser : id
						},
						success: function(res) {
							if(res == "ok"){
								location.reload();
							}
						}
					});
				}
			}
		});
	});
	
	$('.dlv-order').on('click', function (){
	    var path = 'http://localhost:8080/admin/order/delivered';
		
		var id=$(this).attr('id');
		console.log(id);
		bootbox.confirm({
			message: "Are you sure to delivered this order?",
			buttons: {
				cancel: {
					label:'<i class="fa fa-times"></i> Cancel',
					className: 'btn-success'
				},
				confirm: {
					label:'<i class="fa fa-check"></i> Confirm',
					className: 'btn-danger'
				}
			},
			callback: function(confirmed) {
				if(confirmed) {
					$.ajax({
						type: 'POST',
						url: path,
						headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
						data: {
							idorder : id
						},
						success: function(res) {
							if(res == "ok"){
								location.reload();
							}
						}
					});
				}
			}
		});
	});
	
	$('.ndlv-order').on('click', function (){
	    var path = 'http://localhost:8080/admin/order/notyet';
		
		var id=$(this).attr('id');
		console.log(id);
		bootbox.confirm({
			message: "Are you sure to not delivered yet this order?",
			buttons: {
				cancel: {
					label:'<i class="fa fa-times"></i> Cancel',
					className: 'btn-success'
				},
				confirm: {
					label:'<i class="fa fa-check"></i> Confirm',
					className: 'btn-danger'
				}
			},
			callback: function(confirmed) {
				if(confirmed) {
					$.ajax({
						type: 'POST',
						url: path,
						headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
						data: {
							idorder : id
						},
						success: function(res) {
							if(res == "ok"){
								location.reload();
							}
						}
					});
				}
			}
		});
	});
	
	
	
	
});






















