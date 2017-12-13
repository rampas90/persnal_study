유튜브 동영상 공유 소스 코드의 예.



```xml

<iframe width="560" height="315" src="https://www.youtube.com/embed/xPvyrmPszlY" frameborder="0" allowfullscreen></iframe>

```

> 일반적으로 위소스처럼 넓이는 560, 높이는 315등등 사이즈가 고정되어 있을경우 해결 방법.


`<div>` 클래스와  CSS 스타일로 해결 가능.  유튜브 동영상을 반응형으로 알맞게 보여주기 위한 클래스를 css 파일에 정의하여 적용합니다. 코드는 아래 참조~







```css

.embed-container {
   position: relative;
   padding-bottom: 56.25%;
   height: 0;
   overflow: hidden;
}

.embed-container iframe, .embed-container object, .embed-container embed {
   position: absolute;
   top: 0;
   left: 0;
   width: 100%;
   height: 100%;
}

```






```xml

<div class='embed-container'>

<iframe width="560" height="315" src="https://www.youtube.com/embed/-kypVh6MM4U" frameborder="0" allowfullscreen></iframe>

</div>

```







`.padding-bottom: 56.25%`의 의미



#### 16:9 와이드 비율


`9 / 16 = 0.5625 = 56.25%`


가로 폭은 가변형이므로 padding-bottom 수치를 퍼센트로 지정하여 16:9의 와이드 화면 비율로 보여주는 방식. 위 아래의 검은 색 레터박스 없이 동영상이 재생된다.


#### 4:3 표준 비율

`3 / 4 = 0.75 = 75%`



와이드 화면이 아닌 표준 비율의 동영상이라면 padding-bottom 값을 75%로 바꿔 주시면 됩니다. 클래스를 각각 따로 정의하여 동영상 비율에 맞게 넣으면 된다


```css
.embed-container-169 {
  padding-bottom: 56.25%;
}

.embed-container-43 {
  padding-bottom: 75%;
}
```

> 만약 `16:9 동영상`임에도 위 아래로 조금씩 검은색 여백이 발생한다면  padding-top: 30px; 의 값을 0으로 바꾸거나 변경

