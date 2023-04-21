function sliderOn () {
 const slides = document.querySelector('.slides'); // 슬라이드뼈대 감지
 const Content = document.querySelector('.Thumbnail'); //썸네일 뼈대 감지
 const item = slides.getElementsByClassName('slide_item'); // 슬라이드 아이템 획득
 const thumbnail = Content.getElementsByClassName('thumbnail_item'); //썸네일 아이템 획득
 
 const firstEle = item[0]; // 첫번째 슬라이드 아이템
 const firstThumb = thumbnail[0]; // 첫번째 썸네일 아이템
 firstEle.classList.add('ontheSlide'); //첫번째 슬라이드 아이템에 ontheSlide 클래스 추가
  firstThumb.classList.add('ontheThumbnail'); // 첫번째 썸네일 아이템에 ontheThumbnail 클래스 추가
  const gogogo = setInterval(sliderGo, 4000);
  function sliderGo (isThat) {//isThat을 받아오기
    
    if (isThat) {//isThat이 지정되어 있다면
      var nextOr = 'previous'; //이전 버튼을 누른 것.
    }
    else { //isThat이 지정되지 않았다면
        var nextOr = 'next'; //다음 버튼을 누른 것.
    }
    
    const currentItem = document.querySelector('.ontheSlide');
    const currentThumb = document.querySelector('.ontheThumbnail');
    currentItem.classList.remove('ontheSlide')
    currentThumb.classList.remove('ontheThumbnail')
  if (nextOr === 'next'){  // 다음버튼을 눌렀다면
  if (!currentItem.nextElementSibling) {//마지막 아이템일 경우
      item[0].classList.add('ontheSlide') //처음으로 슬라이드아이템 이동
      thumbnail[0].classList.add('ontheThumbnail')//처음으로 썸네일 아이템 이동
    }   
   else { //그 외의 경우
     currentItem.nextElementSibling.classList.add('ontheSlide') //다음으로 슬라이드 아이템 이동
     currentThumb.nextElementSibling.classList.add('ontheThumbnail')//다음으로 썸네일 아이템 이동
        }   
  }
   else if (nextOr === 'previous') { // 이전 버튼을 눌렀다면
       if (!currentItem.previousElementSibling) { //첫번재 아이템일 경우
      item[item.length-1].classList.add('ontheSlide') //마지막 슬라이드로 이동
       thumbnail[thumbnail.length-1].classList.add('ontheThumbnail')  //마지막 썸네일로 이동
    }   
   else { //다른 경우
     currentItem.previousElementSibling.classList.add('ontheSlide') //이전 슬라이드 아이템으로 이동
     currentThumb.previousElementSibling.classList.add('ontheThumbnail')//이전 썸네일 아이템으로 이동
        }   
   } 
    
  }
 const nextButton = document.querySelector('.nextButton');//다음버튼 감지
 const prevButton = document.querySelector('.prevButton');//이전버튼 감지
nextButton.addEventListener('click',function(){//다음 버튼을 누른다면
  clearInterval(gogogo)//자동이동을 중지하고
  sliderGo();//수동으로 다음이동
})
prevButton.addEventListener('click',function(){//이전 버튼을 누른다면
  clearInterval(gogogo)//자동이동을 중지하고
  sliderGo('1');//수동으로 이전 이동
})  
for (var i=0; i<thumbnail.length; i++) { // 썸네일 갯수만큼 for문 발동  
  thumbnail[i].setAttribute('data-number',i); // 썸네일에 data-number를 설정해 몇번째 아이템인지 설정
thumbnail[i].addEventListener('click',function(event){ //썸네일 아이템을 클릭한다면
  clearInterval(gogogo) // 자동이동을 멈춘다.
   const currentItem = document.querySelector('.ontheSlide'); //현재 아이템 감지
    const currentThumb = document.querySelector('.ontheThumbnail'); //현재 썸네일 감지
    currentItem.classList.remove('ontheSlide') // 현재 아이템 비활성화
currentThumb.classList.remove('ontheThumbnail') // 현재 썸네일 비활성화
const number = event.target.getAttribute('data-number') // 몇 번째 썸네일을 클릭했는지 감지
  item[number].classList.add('ontheSlide')   // 썸네일과 같은 숫자의 아이템을 활성화
  event.target.classList.add('ontheThumbnail') // 클릭한 썸네일을 활성화
  
})  
}
  
  
}
sliderOn();