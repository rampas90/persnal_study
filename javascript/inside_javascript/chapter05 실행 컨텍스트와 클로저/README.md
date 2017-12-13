## 5. 실행 컨텍스트와 클로저

살펴볼 내용
1. 실행컨텍스트의개면
2. 활성객체와 변수객체
3. 스코프체인
4. 클로저

### 5.1 실행 컨텍스트 개념

ECMAScript 에서는 실행 컨텍스트를 **"실행 가능한 코드를 형상화하고 구분하는 추상적인 개념"**으로 기술한다.
이를 **콜스택**과 연관하여 정의하면 **"실행 가능한 자바스크립트 코드 블록이 실행되는 환경"**이라고 할 수 있고,
이 컨텍스트 안에 실행에 필요한 여러가지 정보를 담고 있다.
여기서 말하는 실행 가능한 코드 블록은 대부분의 경우 함수가 된다.

ECMAScript 에서는 실행 컨텍스트가 형성되는 경우를 세가지로 규정하고 있는데,
- 전역코드
- `eval()`함수로 실행되는 코드
- 함수 안의 코드를 실행할 경우

대부분의 프로그래머는 함수로 실행 컨텍스트를 만든다.
그리고 이 코드 블록 안에 변수 및 객체, 실행 가능한 코드가 들어있다.
이 코드가 실행되면 실행컨텍스트가 생성되고, 실행 컨텍스트는 스택 안에 하나씩 차곡차곡 쌓이고,
제일 위에 위치하는 실행 컨텍스트가 현재 실행되고 있는 컨텍스트다.
ECMAScript 는 실행 컨텍스트의 생성을 다음처럼 설명한다.

> "현재 실행되는 컨텍스트에서 이 컨텍스트와 관련 없는 실행 코드가 실행되면, 새로운 컨텍스트가 생성되어 스택에 들어가고 제어권이 그 컨텍스트로 이동한다."


```javascript
console.log("This is global context");

function ExContext1() {
	console.log("This is ExContext1");
};

function ExContext2() {
	ExContext1();
	console.log("This is ExContext2");
};

ExContext2();
```

> 출력결과

```
This is global context
This is ExContext1
This is ExContext2
```


### 5.2 실행 컨텍스트 생성 과정

알아볼내용
- 활성 객체와 변수 객체
- 스코프 체인


```javascript
function execute(param1, param2) {
	var a = 1, b = 2;
	function func() {
		return a+b;
	}
	return param1+param2+func();
}

execute(3, 4);
```

#### 5.2.1 활성 객체 생성

실행 컨텍스트가 생성되면 자바스크립트 엔진은 해당 컨텍스트에서 실행에 필요한 여러가지 정보를 담을 객체를 생성하는데,
이를 활성 객체라고 한다.
이 객체에 앞으로 매개변수나 사용자가 정의한 변수 및 객체를 저장하고, 새로 만들어진 컨텍스트로 접근 가능하게 되어있다.

#### 5.2.2 arguments 객체 생성

다음 단계에서는 arguments 객체를 생성한다.
앞서 만들어진 활성 객체는 arguments 프로퍼티로 이 arguments 객체를 참조한다.


#### 5.2.3. 스코프 정보 생성

현재 컨텍스트의 유효범위를 나타내는 스코프 정보를 생성한다.
이 스코프 정보는 현재 실행 중인 실행 컨텍스트 안에서 연결리스트와 유사한 형식으로 만들어진다
현재 컨텍스트에서 특정 변수에 접근해야 할 경우, 이 리스트를 활용한다.

이 리스트를 스코프 체인이라고 하는데 `[[scope]]`프로퍼티로 참조된다.

더 자세한 사항은 이후에 알아보고 
여기서는 현재 생성된 활성 객체가 스코프 체인의 제일 앞에 추가되며, 예제의 `excute()`함수의 인자나 지역변수 등에 접근할 수 있다는 것만 알고 넘어가자

#### 5.2.4 변수 생성

현재 실행 컨텍스트 내부에서 사용되는 지역 변수의 생성이 이루어진다.
변수 객체 안엣 호출된 함수 인자는 각각의 프로퍼티가 만들어지고 그 값이 할당된다.

여기서 주의할 점은 이 과정에서는 변수나 내부 함수를 단지 메모리에 생성하고, 초기화는 변수나 함수에 해당하는 표현식이 실행되기 전가지는 이루어지지 않는다는 점이다.
표현식의 실행은 변수 객체 생성이 다 이루어진 후 시작된다.

#### 5.2.5 this 바인딩

마지막 단게에서는 this 키워드를 사용하는 값이 할당된다.
여기서 this가 참조하는 객체가 없으면 전역 객체를 참조한다.

#### 5.2.6 코드실행

이렇게 하나의 실행 컨텍스트가 생성되고, 변수 객체가 만들어진 후에, 코드에 있는 여러가지 표현식 실행이 이루어진다.

이렇게 실행되면서 변수의 초기화 및 연산, 또 다른 함수 실행 등이 이루어진다.

예제에서 undefined가 할당된 변수 a와 b에도 이 과정에서 1,2의 값이 할당된다.

---


### 5.3 스코프 체인

- 자바스크립트 코드를 이해하려면 스코프 체인의 이해는 필수적이다.
- 이를 알아야, 변수에 대한 인식 메커니즘을 알 수 있고, 현재 사용되는 변수가 어디에서 선언된 변수인지 정확히 알 수 있기 때문이다.
- 프로토타입 체인과 거의 비슷한 메커니즘 이므로 참고하자

자바스크립트도 다른 언어와 마찬가지로 스코프, 즉 유효범위가 있다.

단 다른 언어처럼 `{}` 로 묶여있는 범위 안에서 선언된 변수는 `{}`가 끝나는 순간 사라지고 밖에서는 접근할 수 가 없지만
자바스크립트에서는 함수내의 `{}` 이를테면 `for(){}`, `if{}`와 같은 구문은 유효범위가 없다.
오직 함수만이 유효범위의 한 단위가 된다.

> 이 유효 범위를 나타내는 스코프가 [[scope]]프로퍼티로 각 함수 객체 내에서 연결 리스트 형식으로 관리되는데,
> 이를 스코프 체인이라고 한다.

스코프 체인은 각 실행 컨텍스트의 변수 객체가 구성 요소인 리스트와 같다.

**각각의 함수는 [[scope]]프로퍼티로 자신이 생성된 실행 컨텍스트의 스코프 체인을 참조한다.**
함수가 실행되는 순간 실행 컨텍스트가 만들어지고, **이 실행 컨텍스트는 실행된 함수의 [[scope]]프로퍼티를 기반으로 새로운 스코프 체인을 만든다.**

#### 5.3.1 전역 실행 컨텍스트의 스코프 체인

```javascript
var var1 = 1;
var var2 = 2;
console.log(var1); //출력값 1
console.log(var2); //출력값 2
```

> 위 예제는 전역코드이다. 함수선언및 호출이 없고, 실행 가능한 코드들만 나열되어 있다.

이 코드를 실행하면, 먼저 전역 실행 컨텍스트가 생성되고, 변수 객체가 만들어진다.
이 변수 객체의 스코프 체인은 어떻게 될까?

현재 전역 실행 컨텍스트 단 하나만 실행되고 있어 참조할 상위 컨텍스트가 없다.
자신이 최상위에 위치하는 변수 객체인 것이다.
따라서, 이 변수 객체의 스코프 체인은 자기 자신만을 가진다.
다시 말해 변수 객체의 [[scope]]는 변수객체 자신을 가리킨다.


#### 5.3.2 함수를 호출한 경우 생성되는 실행 컨텍스트의 스코프 체인

```javascript
var var1 = 1;
var var2 = 2;
function func() {
    var var1 = 10;
    var var2 = 20;
    console.log(var1); // 10
    console.log(var2); // 20
}
func();
console.log(var1);  // 1
console.log(var2);  // 2
```

위 예제를 실행하면 전역 실행 컨텍스트가 생성되고, func() 함수 객체가 만들어진다.

> 이 함수 객체의 [[scope]]는 어떻게 될까?

함수 객체가 생성될 때, 그 함수 객체의 [[scope]]는 현재 실행되는 컨텍스트의 변수 객체에 있는 [[scope]를 그대로 가진다.
따라서, func 함수 객체의 [[scope]]는 전역 변수 객체가 된다.

정리해보자
- 각 함수 객체는 [[scope]]프로퍼티로 현재 컨텍스트의 스코프 체인을 참조한다.
- 한 함수가 실행되면 새로운 실행 컨텍스트가 만들어지는데, 이 새로운 실행 컨텍스트는 잣니이 사용할 스코프 체인을 다음과 같은 방법으로 만든다.
- 현재 실행되는 함수 객체의[[scope]]프로퍼티를 복사하고, 새롭게 생성된 변수 객체를 해당 체인의 제일 앞에 추가한다.
- 요약하면 스코프 체인은 다음과 같이 표현할 수 있다.

> 스코프 체인 = 현재 실행 컨텍스트의 변수객체 + 상위 컨텍스트의 스코프 체인


```javascript
var value = "value1";

function printValue() {
    return value;
}
function printFunc(func) {
    var value = "value2";
    console.log(func());
}

printFunc(printValue);
```

> 위 예제의 결과를 예측해보자. 

각 함수 객체가 처음 생성될 때 [[scope]]는 전역 객체의 [[scope]]를 참조한다.
따라서 각 함수가 실행될 때 생성되는 실행 컨텍스트의 스코프 체인은 전역 객체와 그 앞에 새롭게 만들어진 변수 객체가 추가된다.

> 지금까지 실행 컨텍스트가 만들어지면서 스코프 체인이 어떻게 형성되는지 살펴보았다.

이렇게 만들어진 스코프체인으로 식별자 인식이 이루어진다.
식별자 인식은 스코프체인의 첫번째 변수 객체부터 시작한다.
식별자와 대응되는 이름을 가진 프로퍼티가 있는지를 확인한다.

첫번째 객체에 대응되는 프로퍼티를 발견하지 못하면, 다음 객체로 이동하여 찾는다.
이런식으로 프로퍼티를 찾을 때 까지 계속된다.

---

### 5.4 클로저

#### 5.4.1 클로저의 개념

> 우선 예제를 통해 살펴보자

```javascript
function outerFunc(){
	var x=10;
    var innerFunc = function(){console.log(x);}
    
    return innerFunc;
}
var inner = outerFunc();
inner(); //10
```

`innerFunc()`은 `outerFunc()`의 실행이 끝난 후 실행된다.
그렇다면 outerFunc 실행 컨텍스트가 생성되는 것인데, innerFunc()의 스코프 체인은 outerFunc 변수객체를 여전히 참조할 수 있을까?

outerFunc 실행 컨텍스트는 사라졌지만, outerFunc 변수 객체는 여전히 남아있고, innerFunc의 스코프 체인으로 참조되고있다.

> 이것이 바로 자바스크립트에서 구현한 클로저라는 개념이다.

되돌아보는 의미에서 다시한번 정리를 해보자면

- 자바스크립트의 함수는 일급 객체로 취급된다.
- 이는 함수를 다른함수의 인자로 넘길 수도 있고, return으로 함수를 통째로 반환받을 수도 있음을 의미한다.

여기서 최종반환되는 함수가 외부 함수의 지역변수에 접근하고 있다는 것이 중요하다.
이 지역변수에 접근하려면, 함수가 종료되어 외부 함수의 컨텍스트가 반환되더라도 변수 객체는 반환되는 내부 함수의 스코프 체인에 그대로 남아있어야만 접근할 수 있다. 이것이 바로 `클로저`다 

조금 쉽게 풀어 말하면 
> #### 이미 생명 주기가 끝난 외부 함수의 변수를 참조하는 함수를 클로저라고 한다.

위의 에제에서는 outerFunc에서 선언된 `x`를 참조하는 `innerFunc`가 클로저가 된다.
그리고 클로저로 참조되는 외부 변수 즉, outerFunc의 `x`와 같은 변수를 `자유변수`라고 한다.

> 다른 예제를 살펴보자

```javascript
function outerFunc(arg1, arg2){
	var local=8;
	function innerFunc(innerArg){
		console.log((arg1+arg2)/(innerArg + local));
	}

	return innerFunc;
}

var exam1 = outerFunc(2,4);
exam1(2)
```


#### 5.4.2 클로저의 활용

- 클로저의 개념을 이해했다면, 이제 이 클로저를 어떻게 활용할 것인지 고민해야 한다.
- 클로저는 성능적인 면과 자원적인 면에서 약간 손해를 볼 수 있으므로 무차별적으로 사용해서는 안된다.
- 클로저를 잘 활용하려면 경험이 가장 중요하게 작용한다.

##### 5.4.2.1 특정함수에 사용자가 정의한 객체의 메서드 연결하기

```javascript
	function HelloFunc(funcc){
		this.greeting = "hello";
	}

	HelloFunc.prototype.call = function(func){
		func ? func(this.greeting) : this.func(this.greeting);
	}

	var userFunc = function(greeting){
		console.log(greeting);
	}

	var objHello = new HelloFunc();
	objHello.func = userFunc;
	objHello.call();
```
> `Hellofunc()`는 greeting만을 인자로 넣어 사용자가 인자로 넘긴 함수를 실행시킨다.
> 그래서 사용자가 정의한 함수도 한 개의 인자를 받는 함수를 정의할 수 밖에 없다.
> 여기서 사용자가 원하는 인자를 더넣어서 HelloFunc()를 이용하여 호출하려면 어떻게 해야 할까?

```javascript
function saySomething(obj, methodName, name) {
	return (function(greeting) {
		return obj[methodName](greeting, name);
	});
}

function newObj(obj, name) {
	obj.func = saySomething(this, "who", (name || "everyone"));

	return obj;
}

newObj.prototype.who = function(greeting, name) {
	console.log(greeting + " "  +  (name || "everyone") );
}

var obj1 = new newObj(objHello, "zzoon");
obj1.call();

```

> 앞 에제는 정해진 형식의 함수를 콜백해주는 라이브러리가 있을 경우,
>  그 정해진 형식과는 다른 형식의 사용자 정의 함수를 호출할 때 유용하게 사용된다.

> 예를 들어 브라우저에서는 onclick, onmouseover와 같은 프로퍼티에 해당 이벤트 핸들러를 사용자가 정의해 놓을 수가 있는데,
> 이 이벤트 핸들러의 형식은 `function(event){}`이다. 이를 통해 브라우저는 발생한 이벤트를 event인자로 사용자에게 넘겨주는 방식이다.
> 여기에 event 외의 원하는 인자를 더 추가한 이벤트 핸들러를 사용하고 싶을 때, 앞과 같은 방식으로 클로저를 적절히 활용해 줄 수 있다.


##### 5.4.2.2. 함수의 캡슐화

다음과 같은 함수를 작성한다고 가정해보자

`"i am XXX. I live in XXX. i'am XX years old" 라는 문장을 출력하는데, XX 부분은 사용자에게 인자로 입력받아 값을 출력하는 함수`

가장 먼저 생각해볼수 있는것은 위의 문장 템플릿을 전역 변수에 저장하고, 사용자의 입력을 받은 후,
이 전역 변수에 접근하여 완성된 문장을 출력하는 방식으로 함수를 작성하는 것이다.

> 이 방식으로 구현한 코드느느 다음과 같다.

```javascript
var buffAr = [
	'I am ',
	'',
	'. I live in ',
	'',
	'. I\'am ',
	'',
	' years old.',
];

function getCompletedStr(name, city, age){
	buffAr[1] = name;
	buffAr[3] = city;
	buffAr[5] = age;

	return buffAr.join('');
}

var str = getCompletedStr('zzoon', 'seoul', 16);
console.log(str);
```

위 에제코드는 정상적으로 작동하지만, 단점이 있다.
바로 `buffAr`이라는 매열은 전역 변수로서, 외부에 노출되어 있다는 점이다.

이는 곧 다른 함수에서 이 배열에 쉽게 접근하여 값을 바꿀 수도 있고, 실수로 같은 이름의 변수를 만들어 버그가 생길수도 있다.
특히 다른코드와의 통합 혹은 이 코드를 라이브러리로 만들려고 할 때, 문제를 발생시킬 가능성이 있다.

> 이 경우 클로저를 활용하여 `buffAr`을 추가적인 스코프에 넣고 사용하게 되면, 이 문제를 해결할 수 있다

```javascript
var getCompletedStr = (function(){
    var buffAr = [
        'I am ',
        '',
        '. I live in ',
        '',
        '. I\'am ',
        '',
        ' years old.',
    ];

    return (function(name, city, age) {
        buffAr[1] = name;
        buffAr[3] = city;
        buffAr[5] = age;

        return buffAr.join('');
    });
})();

var str = getCompletedStr('zzoon', 'seoul', 16);
console.log(str);
```

위 코드에서 가장먼저 주의해서 봐야할 점은 변수 `getCompletedStr`에 익명의 함수를 즉시 실행시켜 반환되는 함수르르 할당하는 것이다.
이 반환되는 함수가 클로저가 되고, 이 클로저는 자유변수 buffAr을 스코프체인에서 참조할 수 있다.


##### 5.4.2.3 setTimeout()에 지정되는 함수의 사용자 정의

setTimeout 함수는 웹 브라우저에서 제공하는 함수인데, 첫번째 인자로 넘겨지는 함수 실행의 스케쥴링을 할 수 있다.
두번재 인자인 밀리 초 단위 숫자만큼의 시간 간격으로 해당 함수를 호출한다.

자신의 코드를 호출하고 싶다면 첫 번째 인자로 해당 함수 객체의 참조를 넘겨주면 되지만, 이것으로는 실제 실행될 때 함수에 인자를 줄 수 없다.

그렇다면 자신이 정의한 함수에 인자를 넣어줄 수 있게 하려면 어떻게 해야 할까? 이역시 클로저로 해결할 수 있다.

```javascript
function callLater(obj, a, b) {
    return (function(){
        obj["sum"] = a + b;
		console.log(obj["sum"]);
    });
}

var sumObj = {
	sum : 0
}
 
var func = callLater(sumObj, 1, 2);
setTimeout(func, 500);
```

사용자가 정의한 함수 callLater를 setTimeout 함수로 호출하려면, 변수 func에 함수를 반환받아 setTimeout()함수의 첫번째 인자로 넣어주면 된다.
반환받는 함수는 당연히 클로저고, 사용자가 원하는 인자에 접근할 수 있다.


#### 5.4.3 클로저를 활용할 때 주의 사항

지금까지 클로저의 개념과 활용을 알아봤는데, 앞서 언급한대로 클로저는 자바스크립트의 강력한 기능이지만, 너무 남발하여 사용하면 안된다.
클로저에서 사용자가 쉽게 간과할 수 있는 사항을 알아보자

##### 5.4.3.1. 클로저의 프로퍼티값이 쓰기 가능하므로 그값이 여러 번 호출로 항상 변할 수 있음에 유의해야 한다.

```javascript
function outerFunc(argNum){
	var num = argNum;
	return function(x){
		num += x;
		console.log('num: '+num);
	}
}

var exam=outerFunc(40);

exam(5);
exam(-10);
```

> 자유변수 num의 값이 계속해서 변화하는걸 볼수 있다.


##### 5.4.3.2. 하나의 클로저가 여러 함수 객체의 스코프 체인에 들어가 있는 경우도 있다.

```javscript
function func(){
	var x =1;
	return{
		func1 : function(){console.log(++x);},
		func2 : function(){console.log(-x);}
	};
};

var exam = func();
exam.func1();
exam.func2();
```

> 반환되는 객체에는 두 개의 함수가 정의되어 있는데, 두 함수 모두 자유 변수 `x`를 참조한다.
> 그리고 각각의 함수가 호출될 때마다 x 값이 변화하므로 유의해야 한다.


##### 5.4.3.3 루프 안에서 클로저를 활용할 때는 주의하자

```javascript
function countSeconds(howMany) {
  for (var i = 1; i <= howMany; i++) {
    setTimeout(function () {
      console.log(i);
    }, i * 1000);
  }
};
countSeconds(3);
```

> 위 예제는 1,2,3 을 1초 간격으로 출력하는 의도로 만든 예제다.
> 하지만 결과는 4가 연속 3번 1초 간격으로 출력된다.

setTimeout 함수의 ㅇ니자로 들어가는 함수는 자유변수 `i`를 참조한다.
하지만 이 함수가 실행되는 시점은 countSecondes() 함수의 실행이 종료된 이후이고, i값은 이미 4가 된 상태이다.
그러므로 setTimeout()로 실행되는 함수는 모두 4를 출력하게 된다

> 그럼 이제 원하는 결과를 얻기 위해 코드를 수정해 보자


```javascript
function countSeconds(howMany) {
	for (var i = 1; i <= howMany; i++) {
		(function (currentI){
			setTimeout(function(){
				console.log(currentI);
			}, currentI * 1000);
		}(i));
	}
};
countSeconds(3);
```

> 즉시 실행 함수를 실행시켜 루프 `i`값을 currentI에 복사해서 setTimeout()에 들어갈 함수에서 사용하면, 원하는 결과를 얻을 수 있다.





