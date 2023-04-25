  function searchForm111() {
    var f = document.getElementById("searchForm");
    f.action = "/hotel/hotelList";
    f.submit();
  };
  
  
  
  
$(function() {
  $(".basket-btn").on("click", function() {
    var addBasket = $(this).find('.addBasket_hotel_id').val();
    var userid = $(this).find('.addBasket_userid').val();

    $(this).toggleClass("active");
    if ($(this).hasClass("active")) {
      // 찜하기 클릭 이벤트
      $(this).text("♥찜해제♥");
      $.ajax({
        url: "/hotel/addBasket",
        type: "post",
        data: { hotel_id: addBasket , userid: userid },
        success: function(data) {       
        }
      });
    } else {
      // 찜해제 클릭 이벤트
		$(this).removeClass("active");
		$(this).text("♡찜하기♡");
		$.ajax({
		  url: "/hotel/removeBasket",
		  type: "post",
		  data: { hotel_id: removeBasket , userid: userid },
		  success: function(data) {       
		  }
		});
    }
  });
});


