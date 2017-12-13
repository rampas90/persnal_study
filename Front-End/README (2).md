## jQuery Plug-in

### Accodian

### slide 





## CSS Naming Guide


### id/class 규칙
- **id** : Camelcase 방식
- **class** :  under_score 방식
- **helper-class** : hyphen 방식
- **HTML5 사용자 정의 attribute** : hyphen 방식

> 예)
```java
<div id="boardView">
<div class="board_view">
<div class="text-overflow">
<div data-target="board-list">
```

123123

### prefix/subfix/suffix 정의

#### prefix : 접두사, 주로 형태를 나타내는데 사용한다.
- 기본 프리픽스는 형태별로 통일
- 서브프리픽스가 필요한경우 `_`(underscore)로 구분하여 표기 `(btn / btn_dash)`



| 분류 | prefix | 설명 |
|--------|--------|---------|
| 타이틀 | tit     | 각종 타이틀 |
| 영역 | section  | h1~h6 태그를 지닌 영역구분 |
| 영역 | wrap     | 요소들의 묶음 |
| 영역 | inner    | 부모 wrap이 존재하며 자식 묶음이 별도로 필요할경우 사용 |
| 네비게이션 | gnb  | 전체 메뉴목록 (글로벌) |
| 네비게이션 | lnb     | 지역 네비게이션 (페이지별 메뉴) |
| 네비게이션 | snb    | 사이드 네비게이션(일반적으로 좌측메뉴) |
| 폼 | tf | textfield (input 타입 text / textarea )
| 폼 | inp | input 타입 radio, checkbox file
| 폼 | opt | selectbox
| 탭 | tab | 일반적인 탭
| 테이블 | tbl | 테이블 클래스 부여시 
| 목록 | list | ul,ol 등의 목록
| 버튼 | btn | 버튼
| 박스 | box | 박스모델
| 배경 | bg | 배경 관련
| 썸네일 | thumb | 썸네일 이미지
| 텍스트 | txt | 일반텍스트
| 텍스트 | num | num1, num2...숫자 사용시 `_`언더스코어 사용하지 않는다
| 강조 | emph | 강조할 항목
| 링크 | link | 일반링크
| 링크 | link_more | 더보기 링크
| 순서 | fst, mid, lst | 일반링크
| 상세내용 | desc | 배경이미지(IR 기법시에도 사용가능)


#### subfix : 하부 기호로서 subfix는 prefix와 함께 부가설명 용도로만 사용

```css
ex) ico_arr_news.jpg
```

| 분류 | subfix | 설명 |
|--------|--------|---------|
| 공용 | comm | 전역으로만 사용 |
| 위치 | top/mid/bot/left/right | 위치를 표시 |
| 순서변화 | fst / lst | 이벤트 제어시 주로 사용 |
| 버튼상태변화 | nor | 이벤트 제어시 사용 |



#### subfix : 접미사, prefix와 함께 부가설명 용도로만 사용하며 주로 `상태`를 나타내는데 사용
```java
ex) btn_confirm_on, 
    btn_prev, btn_next
```

| 분류 | subfix | 설명 |
|--------|--------|---------|
| 상태변화 | _on / _off / _over / _hit / _focus | 주로 마우스, 키보드 이벤트 |
| 위치변화 | _top / _mid / _bot / _left / _right | js animation |
| 순서변화 | _fst / _lst | 이벤트 제어시 주로 사용 |
| 이전/다음 | _prev / _next | 버튼의 상태에 주로 사용 |
