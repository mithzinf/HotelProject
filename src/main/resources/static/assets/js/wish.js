  function searchForm111() {
    var f = document.getElementById("searchForm");
    f.action = "/hotel/hotelList";
    f.submit();
  };
  
  
  
  
$(function() {
	$(".basket-btn").on("click", function() {
		
		var $clickedBtn = $(this);
		var hotel_id = $(this).closest('.meta').find('.hotel_id_value').val();
		var user_id = $("#user_id").val();
		
		if(user_id === ""){
		
			swal({
        		title: "로그인 필요",
        		text: "로그인 후 이용가능합니다.",
        		icon: "warning",
        		button: "확인",
        	}).then(function() {
        		window.location.href = "/login/login";
        	});

		}

		// 찜하기 클릭 이벤트
		if ($(this).text() === "♡찜하기♡") {
			
			$.ajax({
				url: "./hotel/addBasket",
				type: "post",
				data: { hotel_id: hotel_id },
				success: function(data) {       
					swal({
	            		title: "찜 추가 완료",
	            		text: "찜 목록에 추가되었습니다!",
	            		icon: "success",
	            		button: "확인",
	            	}).then(function() {
	            		$clickedBtn.text("♥찜해제♥");
	            	});
        		}
        	});
        
        } else {
        
	        $.ajax({
				url: "./hotel/removeBasket",
				type: "post",
				data: { hotel_id: hotel_id },
				success: function(data) {       
					swal({
	            		title: "찜 삭제 완료",
	            		text: "찜 목록에서 삭제되었습니다.",
	            		icon: "success",
	            		button: "확인",
	            	}).then(function() {
	            		$clickedBtn.text("♡찜하기♡");
	            	});
        		}
        	});
			
			
    	}
  });
});


