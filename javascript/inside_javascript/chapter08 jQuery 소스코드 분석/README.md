## 8. jQuery 소스 코드 분석

- jQuery 1.0의 소스코드 구조
- jQuery 1.0의 ID 셀렉터 동작 분석
- jQuery 1.0의 이벤트 핸들러 분석

#### 8.1.1 jQuery 함수 객체
- new 연산자를 이용해 **`jQjery` 객체를 생성하는 기능**

```javascript
// If the context is global, return a new object
if ( window == this )
	return new jQuery(a,c);
```


#### 8.1.2 변수 `$` 를 `jQuery()` 함수로 맵핑

```javascript
// Map the jQuery namespace to the '$' one
var $ = jQuery;
```

#### 8.1.3 jQuery.prototype 객체 변경
- 모든 함수 객체는 prototype 프로퍼티가 있다. 
- 특히 prototype 프로퍼티가 가리키는 프로토 타입 객체는 함수가 생성자로 동작할 때, 이를통해 새로 생성된 객체의 부모 역할을 한다.

> 즉 new 연산자로 jQeury() 생성자 함수를호출할 경우~
> 생성된 jQuery 객체는 [[prototype]]링크로 자신의 프로토타입인 jQuery.prototype객체에 접근 가능하다.


```javascript
jQuery.fn = jQuery.prototype = {
	jquery: "$Rev: 509 $",

	size: function() {
		return this.length;
	},
}
```

jQuery의 프로토타입을 jQuery.fn 프로퍼티가 참조하게 하고.
이제 이후에 생성된 jQuery 객체 인스턴스는 **변경된 프로토타입 객체**에 정의된
다양한 메소드를 프로토타입 체이닝으로 사용할 수 있다.

#### 8.1.4 객체 확장 - extend() 메소드
- jQuery 소스코드에서는 다음 코드와 같이 다른 객체의 프로퍼티아 메소드 복사등으로 객체의 기능을 추가하는 데 사용가능한 `extend()메소드`를 제공한다.
- jQuery 소스코드 곳곳에 jQuery 객체 및 jQuery.prototype 객체를 확장하는 부분으로 볼 수 있다.


```javascript
jQuery.extend = jQuery.fn.extend = function(obj,prop) {
	if ( !prop ) { prop = obj; obj = this; }		//1.
	for ( var i in prop ) obj[i] = prop[i];		//2.
	return obj;
};
```

1) 함수를 호출할 때 obj 인자 하나만을 넘겨서 호출하는 경우,
prop 인자가 undefind 값을 가지므로  !prop가 참이 되면서 if 문 이하 출력

if 문 내부 코드를 살펴보면 obj 인자로 전달된 객체를 prop 매개변수에 저장한다.
그리고 obj 매개변수에는 this를 저장한다. 
여기서 함수 호출 패턴에 따라, 함수 호출 extend()메소드가 어디서 호출되는지에 따라서 다르게 바인딩된다.

> jQuery 함수 객체에서 extend()메소드가 호출될 경우 **this** -> **jQuery 함수객체**로
> jQuery.prototype 객체에서 extend() 메소드가 호출될 경우 **this** -> **jQeury.prototype 객체**로 바인딩 된다.

2) `for in` 문으로 prop 인자의 모든 프로퍼티를 obj 인자로 복사하는 코드다. obj 객체에 prop 객체의 프로퍼티가 추가된다.

** 정리해보자면**
> extend()메소드는 단어의 뜻 그대로 **객체의 기능을 추가**하는 것이다.
> 또한 주의할점은 obj 인자 하나만으로 호출될 경우다.
> ==(js는 java와 다르게 파라미터를 유동적으로 보낼수 있다는점 상기!!!!!)==
> 만약 obj 인자 하나만으로 호출됬다면, 이메소드를 호출한 객체의 this가 obj가 된다.
> 결국, 메소드를 호출한 객체(this)에다 obj 인자로 넘긴 객체를 복사하는 결과가 된다..


#### 8.1.5 jQuery 소스코드의 기본 구성 요소
jQuery 소스코드는 크게 세 부분으로 구성된다.
- jQuery 함수객체
- jQuery.prototype 객체
- jQuery 객체 인스턴스

jQuery() 함수의 가장 기본적인 열할은 new 연산자로 **jQuery 객체를 생성**하는 것이고,
이렇게 생성된 **jQuery 객체**는 프로토타입 체이닝으로 **jQuery.prototype 객체**에 포함된 **프로토타입 메소드**를 호출할 수 있다.

이때 jQuery 함수 객체는 자신이 메소드를 포함하고 있는데, 이러한 jQuery 함수 객체의 메소드는 각각 생성된 jQuery 인스턴스 객체에 특화되지 않고 범용적으로 사용되는 jQuery 코어 메소드로 구성된다.

---

### 8.2 jQuery의 id 셀렉터 동작 분석

제일 궁금했던 아래와 같은 클래스 아이디 연산자 컨트롤을 어떠헤 구현했는가?? 에대한 내용을 알아보자

```javascript
<div id="myDiv" >Hello</div>
<script>alert($(#myDiv).text());</script>
```
#### 8.2.1 $("#myDiv") 살펴보기

- `$("#myDiv")` 는 곧 `jQuery("#myDiv")`
결국 jQuery 함수의 첫번째 인자 a 에는 문자열`"#myDiv"`가 전달되고, 두번째 인자 c는 아무런 인자값이 전달되지 않았으므로 `undefind`값이 설정된다.

> 왜 인자를 a,c 로 표현했는지? 
> 앞에서 살펴봤던 jQuery 함수객체의 선언부 소스코드를 다시한번 보자

```javascript
function jQuery(a,c) {

	////////////////////////////// 1. //////////////////////////////////////
	// 인자 a가 함수이고, jQuery.fn.ready가 참이면?
	if ( a && a.constructor == Function && jQuery.fn.ready )
		return jQuery(document).ready(a);
	/** 
	 * 함수를 호출할 때 첫번째 인자 a는 문자열 "#myDiv"이므로 생성자를 확인하는 a.constructor 값은 'String'이 된다.
	 * a.constructor이 Function인 경우는 a 인자가 함수일 때다. 결국 a는 함수가 아니므로 if문 이하는 실행되지 않는다.
     */
	////////////////////////////// 1. //////////////////////////////////////

	////////////////////////////// 2. //////////////////////////////////////
	// Make sure that a selection was provided
	a = a || jQuery.context || document;
	/**
	 * 인자 a 의 디폴트값을 저장하는 코드. a는 문자열이므로 || 연산자 오른쪽 부분은 실행되지 않고 그대로 "#myDiv" 값을 가진다
	 */
	////////////////////////////// 2. //////////////////////////////////////

	////////////////////////////// 3. //////////////////////////////////////
	// 인자 a가 jquery 프로퍼티를 가지고 있는 객체라면?
	if ( a.jquery )
		return $( jQuery.merge( a, [] ) );
	/**
	 * a는 "#myDiv" 문자열이므로 jquery 프로퍼티를 가지는 객체가 아니다 따라서 if 문 이하는 실행되지 않는다.
	 * 왜 jquery 프로퍼티를 가지는지 확인할까? 소스코드 라인 62 참조!!
	 */
	////////////////////////////// 3. //////////////////////////////////////

	////////////////////////////// 4. //////////////////////////////////////
	// 인자 c가 jquery 프로퍼티를 가지고 있는 객체라면?
	if ( c && c.jquery )
		return $( c ).find(a);
	/**
	 * c의 값은 현재 undefind 이므로 거짓!( "#myDiv" 예제의 경우다 )
	 */
	////////////////////////////// 4. //////////////////////////////////////

	////////////////////////////// 5. //////////////////////////////////////
	// this 값을 살펴서, 현재 jQuery()가 함수 형태로 호출됐는지를 체크한다.
	if ( window == this )
		return new jQuery(a,c);
   /**
    * this 값을 살펴서 jQuery()가 어떤 형태로 호출됐는지를 체크한다.
    * this가 전역객체 window로 바인딩되는 경우는 jQuery()를 함수 형태로 호출하는 경우다.
    * $("#myDiv") 는 함수 호출 형태이므로 this는 window에 바인딩된다.
    * 따라서 if 문은 참이 되고, new 연산자와 함께 생성자 함수 형태로 다시 호출된다.
    * 생성자 함수 형태로 호출돼도 1~4번 까지는 앞의 실행결과와 같은 반면 5. 에서 jQuery 가 생성자 함수로 호출될 경우
    * this는 새로 생성되는 빈 객체에 바인딩되므로 window가 아니고 if문 이하는 실행되지 않느다.
	*/
	////////////////////////////// 5. //////////////////////////////////////


	////////////////////////////// 6. //////////////////////////////////////
	// Handle HTML strings
	var m = /^[^<]*(<.+>)[^>]*$/.exec(a);
	if ( m ) a = jQuery.clean( [ m[1] ] );
	/**
	 * js 에서 슬래쉬(/)는 정규표현식 리터럴을 만드는 기호다.
	 * 이 정규표현식은 다음과 같다.
	 * 1. 빈 문자열이나 '<' 문자를 제외한 문자나 문자열로 시작하고,
	 * 2. 중간에 태그(<>) 형태의 문자나 문자열이 있으며,
	 * 3. 빈 문자열이나 '>'문자를 제외한 문자나 문자열로 끝난다.
	 *    '<li>'  , 'abc<a>def' 는 위의 정규표현식을 만족한다.
	 * '#myDiv' 는 위 정규표현식에 일치하지 않으므로 exec()메소드의 실행결과는 null값이 된다. 즉 if문 이하는 실행되지 않는다.
	 */
	////////////////////////////// 6. //////////////////////////////////////

	////////////////////////////// 7. //////////////////////////////////////
	// Watch for when an array is passed in
	this.get( a.constructor == Array || a.length && !a.nodeType && a[0] != undefined && a[0].nodeType ?
		// Assume that it is an array of DOM Elements
		jQuery.merge( a, [] ) :

		// Find the matching elements and save them for later
		jQuery.find( a, c ) );
	/**
	 * this.get() 메소드를 호출하는 코드다.
	 * 삼항 연산자로 ? 연산의 조건 부분이 true 이면 jQuery.merge()메소드가 호출되고, false면 jQuery.find()메소드가 호출된다.
	 * a.constructor == Array
	 * 	a는 '#myDiv' 문자열이므로 a.constructor 값은 'String'이다. 따라서 false
	 * a.length
	 * 	a는 문자열이므로 length 프로퍼티가 있고, 이는 문자열 길이를 의미한다.'#myDiv'는 6 즉 이값은 true
	 * !a.nodeType
	 * 	a는 문자열이므로 nodeType 프로퍼티가 없으므로 undefined 값을 가지므로 true
	 * a[0] != undefined
	 * 	a는 문자열이므로 배열의 인덱스 값을 이용해서 접근할 수 있다. 
	 * 	따라서 a[0]은 문자열의 첫 번째 문자를 의미하므로 예제에서는 '#'가된다. 즉 여기서는 true
	 * a[0].nodeType
	 * 	예제에서 a[0]인 '#'는 문자열을 나타낼뿐 nodeType 프로퍼티를 가지는 DOM 객체가 아니므로 이 표현식은 false
	 * 이를 종합해보면 앞의 조건 전체는 false가 되므로 jQuery.find(a.c)문이 실행되게 된다.
	 */
	////////////////////////////// 7. //////////////////////////////////////

  // See if an extra function was provided
	var fn = arguments[ arguments.length - 1 ];
	
	// If so, execute it in context
	if ( fn && fn.constructor == Function )
		this.each(fn);
}
```


##### 8.2.1.1 jQuery.find(a.c) 살펴보기
jQuery.find()는 jQuery 함수 객체 내에 포함된 메소드로서, jQuery의 셀렉터 기능을 처리하는 중요한 함수다.

```javascript
find: function( t, context ) {
		// Make sure that the context is a DOM Element
		if ( context && context.nodeType == undefined )

			context = null;
..... 이하 생략
```

예제에서는 `jQuery.find('#myDiv')`형태로 호출하므로 다음 `find()` 메소드의 인자 t에는 문자열 '#myDiv'이 전달되고, 아무 인자도 전달되지 않은 context 매개변수에는 undefined 값이 할당된다.

---



### 8.3 jQuery 이벤트 핸들러 분석
jQuery는 브라우저에서 제공하는 다양한 이벤트를 처리하는 이벤트 핸들러를 제공한다.
jQuery로 이벤트 핸들러를 만들어보고, 직접 소스를 보면서 동작 원리를 분석해보자.

#### 8.3.1 jQuery 이벤트 처리 예제 


```javascript
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<meta charset="UTF-8">
	<title>문서 제목</title>
	<script src="jquery-1.0.js"></script>
</head>
<body>
	<div id="clickDiv">Click Here</div>
	<script>
	$('#clickDiv').click(function(){
		alert('Mouse Click');
	})
	</script>
</body>
</html>
```

#### 8.3.2 click() 메소드 정의
우선 .click()메소드가 jQuery에서 어떻게 정의되어 있는지를 살펴보자.

```javascript
new function(){		//1.
/**
 * 인자가 없는 생성자 함수를 호출할 경우에 괄호를 생략할 수 있다.
 */
	//2. 
	var e = ("blur,focus,load,resize,scroll,unload,click,dblclick," +
		"mousedown,mouseup,mousemove,mouseover,mouseout,change,reset,select," + 
		"submit,keydown,keypress,keyup,error").split(",");
	/**
	 * 각 문자열을 split()메소드로 ','로 구분해서 배열을 만들고, 이것을 변수 e에 저장한다. ( clikc 도 여기에 포함 )
	 */


	for ( var i = 0; i < e.length; i++ ) new function(){		//3.
		var o = e[i];
		/**
		 * 2 에서 구한 배열 e를 for문을 돌려서 1과 유사한 new function(){}문을 수행한다.
		 * 즉 각배열별로 매번 new function(){}함수로 둘러쌓인 코드를 수행한다.
		 */

		// Handle event binding
		jQuery.fn[o] = function(f){		//4.
			return f ? this.bind(o, f) : this.trigger(o);
		};
		/**
		 * click을 포함해서 2에서 지정한 blur, focus, load 등과 같은 이벤트 처리 메서드가 jQuery.prototype 객체에 추가된다.
		 * click을 예로 들면 다음과 같다
		 * function(f){
		 * 	return f ? this.bind(click,f) : this.trigger(click);
		 * }
		 */
	... 생략
	};
... 생략
};
```


#### 8.3.3 $('#clickDiv').click() 호출 코드 분석

```javascript
function(f){
	return f ? this.bind(click,f) : this.trigger(click);
}
```
`$('#clickDiv').click(...)` 메소드가 호출되면, 앞 절에서 알아본 것처럼 click()메소드가 호출된다.
이때 인자 `f` 로는 click() 메소드를 호출할 때 인자로 넘긴 함수 표현식 `function(){alert('Mouse Click');}`이 그대로 전달된다.
즉 삼항 연산자의 조건문이 true가 되어 내부적으로는 `this.bind(click,f)`메소드가 다시 호출된다.

==이때 click()메소드에서 사용된 `this`는 무엇일까?==
이에 대한 답은 click()메소드를 누가 호출했는지에 달려있는데 `click()`메소드를 호출하는 주체는 `#('#clickDiv')의 실행결과인 jQuery 객체`이므로 메소드 호출패턴으로 객체가 this로 바인딩된다. 

#### 8.3.4 $('#clickDiv').bind() 메소드 분석

##### 8.3.4.1 bind() 메소드 정의
`bind()`메소드 또한 jQuery 객체 인스턴스에 정의되어 있지 않다.
짐작할 수 있듯이 bind()메소드는 jQuery.prototype 객체에 정의되어 있는 프로토타입 메소드일 것이고, 결국 프로토타입 체이닝으로 호출될 것이다.
하지만 jQuery 소스코드를 얼핏 봐서는 bind()메소드가 어디에 정의되어 있는지 찾기 쉽지 않은데. 그 이유는 여러 단계의 코드를 거쳐 이 메소드가 동적으로 생성되기 때문이다.


##### 8.3.4.2 $('#clickDiv').bind() 호출

```javascript
////////////// 1. //////////////
// click 메소드 실행
$('#clickDiv').click(function(){
	alert('Mouse Click');
});
////////////// 1. //////////////

////////////// 2. //////////////
// click() 메소드 정의
function click(f){
	return f ? this.bind('click', f) : this.trigger('click');
}
////////////// 2. //////////////

////////////// 3. //////////////
// bind() 메소드 정의 
jQuery.fn["bind"] = function(){
	return this.each(n, arguments);
}
////////////// 3. //////////////

////////////// 4. //////////////
// bind() 메소드의 매개변수 n으로 전달된 익명함수
function( type, fn ){
	if(fn.constructor == String)
		fn = new Function("e", (!fn.indexOf(".") ? "$(this)" : "return " ) + fn);
	jQuery.event.add( this, type, fn );
}
////////////// 4. //////////////

```

**2.**
> `1.` 에서 .click()메소드를 호출할 때 매개변수 f로 익명 함수가 전달됐으므로 `this.bind('click',f)`메소드가 호출된다.
> 또한 this는 `$('#clickDiv')`에 해당하는 jQuery 객체로 바인딩된다 
> 따라서 이 코드는 `$('#clickDiv').bind('click',f)`가 호출되는 것과 같다

**3.**
> `2.` 에서 `$('#clickDiv').bind('click',f)` 메소드가 호출될 경우 `.bind()` 메소드가 호출되고 결국 다음 코드가 수행된다.
> ```javascript
>  this.each(n, arguments);
> ```
> this는 메소드 호출 패턴으로 `$('#clickDiv')`객체에 바인딩된다.
> `each()`메소드의 첫 번째 인자 `n`에는 4번 영역에 해당하는 익명함수가 할당된다.
> 두번째 인자인 arguments 에는 bind()메소드를 호출할때 전달한 인자가 배열 형태로 저장된 유사배열 객체다
> `bind('click',f)` 와 같이 bind()메소드를 호출했으므로,
>  arguments 객체 값은 `arguments[0] = 'click'` `arguments[1] = f`와 같다

##### 8.3.4.4 jQuery.event.add(this, type, fn)메소드 호출 분석
jQuery.event 객체는 jQuery에서 실제로 이벤트 핸들러를 처리하는 역할을 담당한다. jQuery.event 객체에는 이벤트 핸들러를 등록하고, 삭제하는 `add()`, `remove()메소드`, 특정 이벤트가 발생할 때 이에 해당하는 이벤트 핸들러를 수행하는 `handle() 메소드`등이 정의되어있다.
> 참고로 jQuery 1.0 에서는 Dean Edward라는 개발자가 작성한 이벤트 핸들러 라이브러리를 그대로 차용하고 있다.


jQuery 에서는 이렇게 브라우저가 호출할 DOM 이벤트 핸들러를 모두 같은 `jQuery.event.handle()`메소드로 설정한다.
==결국 브라우저는 jQuery에서 등록한 이벤트(click, focus등)가 발생하면 jQuery.event.hanle() 메소드만을 호출한다.==

jQuery는 브라우저가 제공하는 이벤트 핸들러 메커니즘을 jQuery 기반으로 다시 재구성하는 과정이라고 생각하면 된다.

#### 8.3.5 Click 이벤트 핸들러 실행 과정

`<div id='clickDiv'>` 태그가 실제 클릭됐을때, 브라우저는 이 태그에 해당하는 DOM 객체의 onclick 프로퍼티에 등록된 이벤트 핸들러 함수를 호출한다.
이에 반해 jQuery에서는 onclick,onmousedown 등과 같은 DOM 이벤트 핸들러를 `jQuery.event.handle() 메소드`로 변경하고, 이 메소드로 하여금 사용자가 등록한 실제 이벤트 핸들러를 처리하고 있다.

```javascript
handle: function(event) {
	if ( typeof jQuery == "undefined" ) return;

	event = event || jQuery.event.fix( window.event );

	// If no correct event was found, fail
	if ( !event ) return;

	var returnValue = true;

	var c = this.events[event.type];

	for ( var j in c ) {
		if ( c[j].apply( this, [event] ) === false ) {
			event.preventDefault();
			event.stopPropagation();
			returnValue = false;
		}
	}
	
	return returnValue;
},
```

DOM 객체의 onclick 프로퍼티 `jQuery.event.handle()` 메소드를 호출했으므로 클릭이 발생할 경우 위의 `handle() 메소드`가호출된다.
`handle()메소드`는 event 인자를 받는 함순데, 여기에는 발생한 이벤트에 따라 다양한 정보를 가지고 있다.
가령 click이나 mousemove 과 같은 이벤트 타입, 이벤트가 발생한 노드 같은 기본정보 뿐만 아니라, click 같은 마우스 이벤트의 경우
마우스가 클릭된 자표 정보, 마우스의 어떤 버튼이 눌렸는지 등의 정보를 포함하고 있다.

> 또한 여기서 this는 `이벤트가 발생한 DOM 객체`가 된다.

#### 8.3.6 jQuery 이벤트 핸들러 특징
jQuery의 이벤트 핸들러 처리는 onclick 프로퍼티 같은 DOM 이벤트 핸들러를 직접 사용하지 않고, 
DOM 객체 내에 자체 `이벤트 관련 프로퍼티(events 객체)`를 생성하고 각 이벤트 타입별로 여러개의 이벤트 핸들러를 동시에 등록할 수 있다.
때문에 jQuery에서는 여러 개의 이벤트 핸들러가 동시에 수행될 수 있는 장점이 있다.

```javascript
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<meta charset="UTF-8">
	<title>문서 제목</title>
	<script src="jquery-1.0.js"></script>
</head>
<body>
	<div id="clickDiv" onclick="alert('Mouse Click1')">Click Here</div>
	<script>
	$('#clickDiv').click(function(){
		alert('Mouse Click2');
	})
	$('#clickDiv').click(function(){
		alert('Mouse Click3');
	})
	
	</script>
</body>
</html>
```

직접 콘솔창에 `$('#clickDiv')`객체를 찍어보면 events.click 객체의 프로퍼티로 등록했음을 볼 수 있다.







