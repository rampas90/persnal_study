## 레이어팝업

> HTML

```html
<div class="dim"></div>
<div class="modal_box">
	<div class="alert_con">
		<div class="alert_msg"><h3>LayerPopup</h3></div>
		<button onclick="confirm();" class="btn_confirm btn_default btn1">확인</button>
		<button class="close btn_default btn1">닫기</button>
	</div>
</div>
<div class="info_btn">
	<a href="" onclick="layer_pop(); return false;"><button class="btn_default">Click</button></a>
</div>
```

> CSS

```CSS
.dim {position:absolute; left:0; top:0;  z-index:99;  background:#000; display:none; }
.modal_box {width:250px; height:170px; display:none; position:absolute; left:50%; top:50%; margin-left:-125px; margin-top:-85px; z-index:100;}
```

- `left : 50%`
- `top :50%`
- `margin-left:'모달박스 width값의 반만큼 빼기`
- `margin-top:'모달박스 height값의 반만큼 빼기`
일반적인 경우  위 형식으로 레이어 팝업의 위치를 중앙정렬 시킬수 있지만 스크롤이 있는 경우나 기타 특별한 경우 height값에서 문제가 생기므로 그럴경우엔 스크립트로 제어해서 중앙정렬 시키면 된다.


> Script

```javascript
function layer_pop(){
	// 상황 별로 left,top 50% 후 margin값으로 중앙정렬 가능할 경우는 아래처럼 사용
	// 그게 힘들경우는 offsetWidth, ScrollTop, 등으로 교체하여 사용

	var docWidth = $(document).width();
	var docHeight = $(document).height();
	
	$('.dim').css( {'width' : docWidth, 'height' : docHeight } );
	$('.dim').fadeTo('slow',0.65);
	$('.modal_box').show();
}

function confirm(){
	// 확인 메시지
	alert('confirm event');	
};

$(document).ready(function(){
	// 닫기버튼 클릭시 닫기 이벤트
	$('.close').click(function(e){
		e.preventDefault();
		$('.dim, .modal_box').fadeOut('slow');
	});
	
	// dim 클릭시 닫기 이벤트
	$('.dim').click(function () {  
		$(this).fadeOut('slow');  
		$('.modal_box').fadeOut('slow');  
	});
});

// 터치이벤트시 이벤트 발생하지 않도록 unbinding 처리
$('.dim').one('touchstart', function () {  
	 $(this).unbind('click');
});


// 브라우져 크기가 조정될때 dim 크기 조절
$(window).resize(function(){
	var width = $(document).width();
	var height = $(document).height();
	$(".dim").width(width).height(height);	
});
```

