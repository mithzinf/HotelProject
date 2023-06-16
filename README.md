# 호텔 예약 웹사이트 '서울어때' 🏨
- 프로젝트 기간 : 2023.4.3 ~ 2023.5.1
## 목차
1. 개발목적 및 목표
2. 팀원 소개
3. 개발 환경
4. DB 구조
5. 페이지별 설명 및 개발한 기능
6. 프로젝트 후 느낀 점 및 배운 것
7. 개선사항
---
## 개발목적 및 목표

#### 목적
- 숙박객들이 편리하게 숙박시설을 예약할 수 있도록 개발하게 되었습니다
#### 목표
- SpringBoot를 프레임워크를 효율적으로 이용해보기
- ajax를 통해 웹페이지 새로고침, 화면 깜빡이는 경우를 최대한 줄여보기(편리함, 효율성 ↑)

---
## 팀원소개
- 송찬호(팀장)
- 김어진
- 김은영
- 강병한

---
## 개발환경

#### 운영체제
- Windows 10

#### 데이터 베이스
- Oracle
- Mybatis

#### WAS
- Apache Tomcat

#### 개발도구
- STS3
- Gradle
- SpringBoot

#### 사용언어
* Front-end 
  - Html
  - css
  - javascript
  - jQuery
  -  Bootstrap  
* Back-end
  - Java
  - ThymeLeaf

#### 형상관리
- Git
- Github

---
## DB 구조
![DB 다이어그램](https://github.com/mithzinf/HotelProject/assets/124668883/ba2dcf9c-3892-419b-9433-46c4eb4f4844)
크게 호텔 관련, 회원 관련, 예약 관련으로 분류하여 hotel 테이블과 member 테이블을 중심으로 데이터베이스를 설계하였습니다.

---
## 페이지 및 구현 기능 설명
1. 메인 페이지
![008](https://github.com/mithzinf/HotelProject/assets/124668883/1838c547-a000-4a95-b49a-fa5dfbff069f)
- 사용자들에게 서울시 관련 행사 정보를 제공하기 위하여 캐러셀 슬라이드를 활용하였습니다.
- 사용자들이 왼쪽, 오른쪽 화살표 버튼을 통해 수동으로 탐색할 수 있습니다.
2. 객실 상세 페이지

**해당 호텔을 소개하는 페이지이자 해당 호텔의 시설, 해당 호텔 객실 시설 정보 제공하는 페이지입니다**
![003](https://github.com/mithzinf/HotelProject/assets/124668883/248b39b2-6dca-4aab-8f52-a8a323d07ea3)
- 해당 호텔 시설 정보, 객실 시설 정보를 볼 수 있습니다.
- 좌측의 숙소 찾아보기 박스를 활용하여, 장소와 날짜 검색을 객실 상세 페이지 내에서도 할 수 있습니다.
![004](https://github.com/mithzinf/HotelProject/assets/124668883/76f3511d-af66-4f20-a98e-6cb45524ff67)
- 날짜 검색 기능을 활용하여, 객실 예약 조회 및 검색할 수도 있습니다.
```
<script>

	

    $.datepicker.setDefaults({
        dateFormat: 'yy-mm-dd',
        prevText: '이전 달',
        nextText: '다음 달',
        monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
        monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
        dayNames: ['일', '월', '화', '수', '목', '금', '토'],
        dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
        dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
        showMonthAfterYear: true,
        yearSuffix: '년'
    });
	
    $(function() {
		//today : 현재 날짜,시간 가진 Date 객체, selectedDate : 체크인날짜 저장하기 위한 변수
    	  var today = new Date();
    	  var selectedDate;
    	  //dp1, dp2에 noConflict()를 해줘야 datePicker의 충돌이 일어나지 않는다!
    	  var dp1 = jQuery.noConflict();
    	  var dp2 = jQuery.noConflict();
    	  //checkinDate : #datepicker1 요소 값 가지고와서 저장하는 변수, 체크인 날짜
    	  var checkinDate = $("#datepicker1").val();
    	  var oneDay = 24 * 60 * 60 * 1000; // 1일을 밀리초로 계산하기
    	  var checkoutDate = new Date(new Date(checkinDate).getTime() + oneDay);
    	  //checkoutDate : 체크인 날짜(checkinDate)에 1일을 더한 날짜를 계산하여 저장하는 변수
    	  
    	  //datepicker1 : 오늘 날짜, selectedDate : 오늘날짜(datepicker1)값을 저장하는 변수, 오늘 날짜를 디폴트값으로 정할때,,datepicker1,2
    	  $("#datepicker1").datepicker({
    	    minDate: today,
    	    onSelect: function(date) {
    	    	selectedDate = new Date(date); // 문자열로 된 날짜를 Date 객체로 변환
    	        selectedDate.setDate(selectedDate.getDate() + 1); // 다음날로 설정
    	        $("#datepicker2").datepicker("option", "minDate", selectedDate); // datepicker2의 minDate 업데이트
    	    }
    	  });
    	  
    	  $("#datepicker2").datepicker({
    		minDate: checkoutDate
    	  });
    	  
    	  //새로운 변수 selectedDateRoom,dp3,dp4 생성하여 jQuery 충돌 회피
    	  var selectedDateRoom;
    	  var dp3 = jQuery.noConflict();
    	  var dp4 = jQuery.noConflict();
    	  //checkinDateRoom :datepicker3 요소 값을 가져와 checkinDateRoom 변수에 저장(사용자가 직접 날짜를 지정할때)
    	  var checkinDateRoom = $("#datepicker3").val();
    	  var checkoutDateRoom = new Date(new Date(checkinDateRoom).getTime() + oneDay);
    	  
    	  //상세 객실 날짜의 초기값(초기값은 오늘날짜, 오늘+1일)을 설정하기
    	  $("#datepicker3").datepicker({
    	    minDate: today,
    	    onSelect: function(date) {
    	    	selectedDateRoom = new Date(date); // 문자열로 된 날짜를 Date 객체로 변환
    	    	selectedDateRoom.setDate(selectedDateRoom.getDate() + 1); // 다음날로 설정
    	        $("#datepicker4").datepicker("option", "minDate", selectedDateRoom); // datepicker2의 minDate 업데이트
    	    }
    	  });
    	  
    	  $("#datepicker4").datepicker({
    		minDate: checkoutDateRoom
    	  });
    	  
    	  
    	  
    	});
        
//숙박객들이 쉽게 객실 날짜 검색 및 조회를 할 수 있도록 객실 예약 기본 체크인, 체크아웃 날짜를 오늘 날짜와 오늘 날짜 + 1일로 기본 설정해두었습니다.
//datepicker3, datepicker4를 활용하여 오늘 외의 다른 상세 객실 예약 날짜를 선택할 수 있는 기능을 구현하였습니다.
위의 기능은 JQuery의 datepicker 기능을 통해 구현하였습니다.
```
- 호텔 객실 스탠다드, 스위트, 디럭스 종류 별 사진 데이터를 불러와 출력하는 기능을 구현하였습니다. 객실 사진의 갯수가 1개일 때, 1개가 아닐 때(2개 이상일 때)로 나누어 사진 갯수가 1개가 아닐 때에는 캐러셀 슬라이드쇼를 통해 모든 사진을 띄울 수 있도록 타임리프의 조건문 (th:if , th: unless)을 사용하여 기능 구현을 완성하였습니다.

```
//객실 사진 데이터 불러오는 코딩(스탠다드룸 ver) 
            <div class="container" style="width: 300px; height: 70px;">
							<!-- lists.size(getStandard)의 사이즈가 1인 경우 -->
								  <div th:if="${#lists.size(getStandard)} == 1">
								    <!-- If getStandard has only one element, display a single image -->
								    <img th:src="'/hotel/' + ${hotel_id} + '_standard.jpg'" style="width:300px; height: 220px;">
								  </div>
								<!-- lists.size(getStandard)의 사이즈가 1가 아닌 경우 -->
								  <div th:unless="${#lists.size(getStandard)} == 1">
								    <!-- If getStandard has more than one element, display a carousel -->
								    <div id="myCarousel1" class="carousel slide" style="width: 300px; height: 70px;" data-interval="false">
								      <!-- Indicators -->
								      <ol class="carousel-indicators">
								        <li data-target="#myCarousel1" data-slide-to="0" class="active"></li>
								        <!-- Add more indicators for additional images -->
								        <th:block th:each="i, index : ${#numbers.sequence(1, getStandard.size())}">
								          <li data-target="#myCarousel1" data-slide-to="${index}"></li>
								        </th:block>
								      </ol>
								
								      <!-- Wrapper for slides -->
								      <div class="carousel-inner">
								        <div class="item active">
								          <img th:src="'/hotel/' + ${hotel_id} + '_standard1.jpg'" style="width:300px; height: 220px;">
								        </div>
								        <!-- Add more items for additional images -->
								        <th:block th:each="standard : ${getStandard}">
								          <div class="item">
								            <img th:src="'/hotel/' + @{${standard.url}}" style="width:300px; height: 220px;">
								          </div>
								        </th:block>
								      </div>
								
								      <!-- Left and right controls -->
								      <a class="left carousel-control" href="#myCarousel1" data-slide="prev">
								        <span class="glyphicon glyphicon-chevron-left"></span>
								        <span class="sr-only">Previous</span>
								      </a>
								      <a class="right carousel-control" href="#myCarousel1" data-slide="next">
								        <span class="glyphicon glyphicon-chevron-right"></span>
								        <span class="sr-only">Next</span>
								      </a>
								    </div>
								    <!-- myCarousel1 div 태그 -->
								  </div>
								  <!-- th:if lists.size ==1 div 태그 -->
							</div>
```
- 호텔의 객실 종류 별로 잔여 객실 갯수를 계산하는 코드를 프로그래밍하여, 객실 정보에 '객실 여유' , '잔여 객실 1개 남았습니', '객실 만실' 문구를 객실 별로 띄우는 기능을 구현하였습니다.
- '만실'일 때는 '예약하기' 버튼이 비활성화 / '객실 여유', '만실 임박'일 때는 '예약하기' 버튼이 활성화되도록 타임리프 th:if를 사용하여 기능 구현하였습니다. 
```
                                      //스탠다드룸 ver

                                        <th:block th:if="${resultMap1.standard}=='만실'">
                                                		<span id="standard2" style="font-size: 18px; color: red; font-weight: bold; margin-top: 10px;">이 객실은 만실입니다.</span>
	                                                	<button type="submit" id="standardButton" class="btn btn-disabled1" disabled="disabled" 
	                                                	style="font-size: 20px; font-weight: bold; width: 180px; height: 50px; float: right; text-align: center;">
	                                                	&nbsp;예약불가&nbsp; </button>
                                        </th:block>
                                        <th:block th:if="${resultMap1.standard}=='만실 임박'">
														                        <span id="standard2" style="font-size: 18px; color: red; font-weight: bold; margin-top: 10px;">잔여 객실이 1개 남았습니다.</span>
	                                                	<button type="submit" id="standardButton" class="btn btn-primary1"
	                                                	style="font-size: 20px; font-weight: bold; width: 180px; height: 50px; float: right; text-align: center;"
	                                                	onclick="reservation1();">
	                                                	&nbsp;예약하기&nbsp; </button>
                                        </th:block>
                                        <th:block th:if="${resultMap1.standard}=='여유'">
                                                		<span id="standard2" style="font-size: 18px; color: red; font-weight: bold; margin-top: 10px;"></span>
	                                                	<button type="submit" id="standardButton" class="btn btn-primary1"
	                                                	style="font-size: 20px; font-weight: bold; width: 180px; height: 50px; float: right; text-align: center;"
	                                                	onclick="reservation1();">
	                                                	&nbsp;예약하기&nbsp; </button>
                                        </th:block>
```

![005](https://github.com/mithzinf/HotelProject/assets/124668883/3a014742-c2c6-4d05-b4af-8deba4eb64d5)

- Javascript와 지도 API를 활용하여 지도 위에 호텔 위치를 표시하는 기능을 구현하였습니다.
(사용자들이 호텔의 위치를 간편히 확인할 수 있도록)
```

```

![010](https://github.com/mithzinf/HotelProject/assets/124668883/1682842d-649d-418f-8b26-3d7e25fe5748)

---


### 구현 기능
- Main Page : 
- HotelDetail Page : 
- Notice board : 

### 구현 기능 상세
- 메인 페이지
- 호텔 상세 설명 페이지, 예약 기능
- 공지사항 게시판
