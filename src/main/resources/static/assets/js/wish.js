  function sendIt() {
    var f = document.getElementById("searchForm");
    f.action = "/hotel/hotelList";
    f.submit();
  };
  
  
$(function() {
  $(".basket-btn").on("click", function() {
    var hotel_id = $(this).find('.addBasket').val();
    var userid = '${session.userid}';

    alert(hotel_id + ", " + userid);
   
    $.ajax({
        url: "addBasket/" + userid,
        
        type: "POST",
        data: { 
            hotel_id: hotel_id,
            userid: userid
        },
        success: function(data) {        
        }
    });

  });
});

