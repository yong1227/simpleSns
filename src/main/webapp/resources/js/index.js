$(document).ready(function() {

	var token;
	if (document.cookie.includes("accesstoken")) {
		token = document.cookie.split('token=')[1];
	}

	var userId;
	if (document.cookie.includes("userId")) {
		userId = document.cookie.split('userId=')[1];
	}

	if (userId) {
		$.ajax({
			url : "/user?id=" + getCookie("userId")
		}).then(function(data) {
			console.log(data);
			console.log(data.data.username);
			$('#username').text(data.data.username);
		}, function(err) {
			console.log(err.responseJSON);
		});
	}

	function getCookie(cname) {
		var name = cname + "=";
		var decodedCookie = decodeURIComponent(document.cookie);
		var ca = decodedCookie.split(';');
		for (var i = 0; i < ca.length; i++) {
			var c = ca[i];
			while (c.charAt(0) == ' ') {
				c = c.substring(1);
			}
			if (c.indexOf(name) == 0) {
				return c.substring(name.length, c.length);
			}
		}
		return "";
	}
	
	$('#header_logout_btn').click(function(){
		document.cookie = "accesstoken=; expires=Thu, 01 Jan 1970 00:00:01 GMT;";
		document.cookie = "userId=; expires=Thu, 01 Jan 1970 00:00:01 GMT;";
		window.location.href = '/login';
	});
	
	
});
