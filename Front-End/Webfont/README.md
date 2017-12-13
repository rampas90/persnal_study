## 웹폰트 Guide

### 1) 웹폰트 기본 개념

1. 인터넷에 떠도는 폰트파일들을 보면 기본적으로 eot, woff, otf 등이 존재한다.
	> 이는 브라우져, 혹은 OS 별로 지원하는 폰트의확장자로 기본적으로  eot, otf, woff등은 지원을 해줘야 한다.
	> woff2 같은 경우는 woff2 의 확장버전으로 압축율이 더 좋으므로 보통 woff 보다 상단에 선언한다.
2. 확장자별 지원범위
| 확장자 | IE | 파폭 | 크롬 | 사파리 | 오페라
|--------|--------|----|---|---|
| .eot |ie4 ~ ie8 | X |  X |  X |  X | 
| .woff |ie9 ~  | 지원 | 지원 | 지원 | 지원 | 

**otf 파일은 OS X 에서 사용**


### 2) CDN
1. 기본적인 웹폰트는[Google Webfont](https://www.google.com/fonts)에서 지원여부 확인가능
	- 한글 폰트의 경우 earlyaccess 항목 참고
	- 구글 CDN URl
	```css
	@ import url(http://fonts.googleapis.com/earlyaccess/nanumbrushscript.css);
@ import url(http://fonts.googleapis.com/earlyaccess/nanumgothic.css);
@ import url(http://fonts.googleapis.com/earlyaccess/nanumgothiccoding.css);
@ import url(http://fonts.googleapis.com/earlyaccess/nanummyeongjo.css);
@ import url(http://fonts.googleapis.com/earlyaccess/nanumpenscript.css);
	```

2. 그외 CDN은[jsdelivr](http://www.jsdelivr.com/) 참조
	- 사용자가 서브셋 제거 버전이나 임의로 올린 버전들이 있다.(쉽게말해 공식지원이 아님)
	- 공식지원이 아닌만큼 커스텀 된 경량화 버전등이 존재할 수 있음 (존재여부는 `jsdelivr` 사이트에서 검색)
	- jsdelivr CDN URl
	```css
나눔고딕
//cdn.jsdelivr.net/font-nanum/1.0/nanumgothic/nanumgothic.css
나눔바른고딕
//cdn.jsdelivr.net/font-nanum/1.0/nanumbarungothic/nanumbarungothic.css
나눔고딕(코딩) : 
//cdn.jsdelivr.net/font-nanum/1.0/nanumgothiccoding/nanumgothiccoding.css
나눔명조 
//cdn.jsdelivr.net/font-nanum/1.0/nanummyeongjo/nanummyeongjo.css
나눔펜(손글씨) 
//cdn.jsdelivr.net/font-nanum/1.0/nanumpenscript/nanumpenscript.css
나눔브러시(손글씨)
//cdn.jsdelivr.net/font-nanum/1.0/nanumbrushscript/nanumbrushscript.css
	```
	
3. 응답장애시 CDN 서버에 영향을 받고 속도가 느리므로 비추천

### 3) 서브셋

1. 서브셋이란?
> 웹폰트에는 서브셋이라는 개념이 있으며, 서브셋은 말 그대로 특정폰트의 하위 집합을 말한다.
따라서, 필요한 하위 집합만을 추려서 용량을 엄청 줄일 수 있다.
(기본적으로 구글웹폰트 역시 서브셋을 지원한다.)

2. 서브셋 지정하기
- 완성형일 경우 `엄청난 양의 한글`이 필요하지만, 사실 모든 문자셋을 사용할 경우는 극히 드물다.
- [KS X 1001 표준](https://ko.wikipedia.org/wiki/KS_X_1001)에는 자주 쓰이는 한글 2,350자를 정의하고 있는데 이 한글만 추려내면 용량을 많이 줄일 수 있다.

3. 서브셋 프로그램
- [서브셋 폰트메이커](http://opentype.jp/subsetfontmk.htm)
- [WOFF 컨버터](http://opentype.jp/woffconv.htm)


### 4) 사용법

#### font.css
> 보통 아래와 같이 weight로 구분하여 사용한다.
> **단 IE8에서 weight 값이 제대로 렌더링 되지 않는 경우가 있는데 이때는 각 weight 별로 별도의 `@font-face`를 만들어서 사용한다**
> ** 예) Noto Sans Regular, Noto Sans Medium .....**

```css
@charset "utf-8";

@font-face {
	font-family: 'Noto Sans';	//font-family css 로 사용할 이름을 지정(마음대로 지정가능)
    font-style: normal;
    font-weight: 100;
    src: local('※'), local('Noto Sans Thin');
    src: url(NotoSans-Thin.eot);
    src: url(NotoSans-Thin.eot?#iefix) format('embedded-opentype'),
		 url(NotoSans-Thin.woff2) format('woff2'),		// woff 보다 상위에 선언
		 url(NotoSans-Thin.woff) format('woff'),
		 url(NotoSans-Thin.otf) format('truetype');
}


@font-face {
	font-family: 'Noto Sans';
    font-style: normal;
    font-weight: 400;
    src: local('※'), local('Noto Sans Regular');
    src: url(NotoSans-Regular.eot);
    src: url(NotoSans-Regular.eot?#iefix) format('embedded-opentype'),
		 url(NotoSans-Regular.woff2) format('woff2'),
		 url(NotoSans-Regular.woff) format('woff'),
		 url(NotoSans-Regular.otf) format('truetype');
}
```

### style.css

```css
// font.css 파일에서 지정한 font-family 이름으로 폰트를 사용한다.
.font {font-family:'Noto Sans'}

// 각 굵기(regular, bold 등등..)별 사용은 아래와 같이 font-weight 속성으로 구분하여 사용한다.
.thin {font-weight:100}
.light {font-weight:300}
.regular {font-weight:400}
.medium {font-weight:500}
.bold {font-weight:700;}
```


### HTML

```xml
<div class="font">
		<h1>Noto Sans</h1>
		<h2 class=" thin">Thin </h2>
		<p class=" thin">국회의 정기회는 법률이 정하는 바에 의하여 매년 1회 집회되며, 국회의 임시회는 대통령 또는 국회재적의원 4분의 1 이상의 요구에 의하여 집회된다. 모든 국민은 그 보호하는 자녀에게 적어도 초등교육과 법률이 정하는 교육을 받게 할 의무를 진다.</p>
		
		<h2 class=" light">Light </h2>
		<p class=" light">대통령의 선거에 관한 사항은 법률로 정한다. 국회는 의원의 자격을 심사하며, 의원을 징계할 수 있다. 군사법원의 조직·권한 및 재판관의 자격은 법률로 정한다.모든 국민은 고문을 받지 아니하며, 형사상 자기에게 불리한 진술을 강요당하지 아니한다. 군인은 현역을 면한 후가 아니면 국무총리로 임명될 수 없다.</p>
		
		<h2 class=" regular">Regular </h2>
		<p class=" regular">군인·군무원·경찰공무원 기타 법률이 정하는 자가 전투·훈련등 직무집행과 관련하여 받은 손해에 대하여는 법률이 정하는 보상외에 국가 또는 공공단체에 공무원의 직무상 불법행위로 인한 배상은 청구할 수 없다.</p>
		
		<h2 class=" medium">Medium </h2>
		<p class=" medium">헌법개정안이 제2항의 찬성을 얻은 때에는 헌법개정은 확정되며, 대통령은 즉시 이를 공포하여야 한다. 국무총리 또는 행정각부의 장은 소관사무에 관하여 법률이나 대통령령의 위임 또는 직권으로 총리령 또는 부령을 발할 수 있다.</p>
		
		<h2 class=" bold">Bold </h2>
		<p class=" bold">국회나 그 위원회의 요구가 있을 때에는 국무총리·국무위원 또는 정부위원은 출석·답변하여야 하며, 국무총리 또는 국무위원이 출석요구를 받은 때에는 국무위원 또는 정부위원으로 하여금 출석·답변하게 할 수 있다.</p>
	</div>
```
