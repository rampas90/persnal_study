## 4. 함수와 프로토타입 체이닝

>  자바스크립트에서 가장 중요한 개념 1순위는 단연 `함수`다. (`C`의 포인터...)

살펴볼 주요 내용
- 함수 생성
- 함수 객체
- 다양한 함수 형태
- 함수 호출과 this
- 프로토타입과 프로토타입 체이닝

### 4.1 함수 정의

> 자바스크립트에서 함수를 생성하는 방법은 3가지가 있다.
> 각각의 방식에 따라 함수 동작이 미묘하게 차이가 난다.

- 함수 선언문(function statement)
- 함수 표현식(function expression)
- Function() 생성자 함수

#### 4.1.1 함수 리터럴
- 자바스크립트에서는 함수도 일반 객체처럼 값으로 취급된다.
- 때문에, 함수리터럴을 이용해 함수를 생성할 수 있다.
- 함수 선언문이나 함수 표현식 방법 모두 이런 함수 리터럴 방식으로 함수를 생성한다.

```javascript
function 함수명(x,y){
	return x+y;
}
```

function 키워드
- 자바스크립트 함수 리터럴은 function 키워드로 시작한다.

함수명
- 함수 몸체의 내부 코드에서 자신을 재귀적으로 호출하거나 또는 자바스크립트 디버거가 해당 함수를 구분하는 식별자로 사용
- 함수명은 선택사항이다. (함수명이 없는 함수를 익명 함수라 한다.)

매개변수리스트
- 매개변수 타입을 기술하지 않는다.

#### 4.1.2 함수 선언문 방식으로 함수 생성하기

- 함수 선언문 방식으로 정의된 함수의 경우는 **반드시 함수명이 정의되어 있어야 한다.**

```javascript
//add() 함수선언문

function add(x,y){
	return x+y;
}
console.log(add(3,4));			// 출력값 7
```
#### 4.1.3 함수 표현식 방식으로 함수 생성하기
- 자바스크립트에서는 함수도 하나의 값처럼 취급된다(이러한 특징이 있으므로 자바스크립트의 함수는 일급객체라고 한다.)
- 따라서, 함수도 숫자나 문자열처럼 변수에 할당하는 것이 가능하다.

> 이런 방식으로 함수 리터럴로 하나의 함수를 만들고, 
> 여기서 생성된 함수를 변수에 할당하여 함수를 생성하는 것을 **함수표현식**이라한다.

함수표현식은 함수 선언문 문법과 거의 유사하지만 유일한 차이점은 
###### **함수 이름이 선택사항이며, 보통 사용하지 않는다는 것이다.**

```javascript
// add() 함수 표현식
var add = function(x,y){
	return x+y;
}
var plus = add;

console.log(add(3,4));		// 출력값 7
console.log(plus(3,4));		// 출력값 11
```

> `add`변수는 함수 리터럴로 생성한 함수를 참조하는 변수이지, **함수이름**이 아니라는것에 주의 하자
> `add`와 같이 함수가 할당된 변수를 **함수변수**라 한다.

> `add`는 함수의 참조값을 가지므로 또 다른 변수 `plus`에도 그 값을 그대로 할당할 수가 있다.

> 이것이 바로 **익명 함수를 이용한 함수표현식(익명 함수 표현식)**이다.

> 참고로 함수 이름이 포함된 함수 표현식을 **기명 함수 표현식**이라 한다.

```javascript
var add = function sum(x, y) {
    return x + y;
};

console.log(add(3,4)); // 7
console.log(sum(3,4)); // Uncaught ReferenceError: sum is not defined 에러 발생
```
> 위 코드를 보면 `sum()`함수를 정의하고, 이 함수를 `add`함수 변수에 할당했다.
> 주의할점은 `add()`함수 호출은 결과값이 성공적으로 리턴되는 반면,
> `sum()`함수 호출의 경우 에러가 발생한다는 점이다. 
> 이것은 **함수 표현식에서 사용된 함수 이름이 외부 코드에서 접근 불가능하기 때문이다.**

실제로 함수 표현식에서 사용된 함수 이름은 정의된 함수 내부에서 해당 함수를 재귀적으로 호출하거나, 디버거 등에서 함수를 구분할 때 사용된다.
따라서 함수 이름으로 사용된 `sum`으로 함수 외부에서 해당 함수를 호출할때 `sum()`함수가 정의되어 있지 않다는 에러가 발생한다.

> `함수 표현식`에서는 함수 이름이 선택사항이지만, 이러한 함수 이름을 이용하면 함수 코드 내부에서 함수이름으로 함수의 재귀적인 호출 처리가 가능하다.

무슨말인지 당최 설명만 보고는 알수가 없으니 아래 예제 코드를 통해 살펴보자

```javascript
var factorialVar = function factorial(n) {
    if(n <= 1) {
        return 1;
    }
    return n * factorial(n-1);
};

console.log(factorialVar(3));  // 6
console.log(factorial(3)); // Uncaught ReferenceError: factorial is not defined 에러 발생
```

위 코드는 함수 표현식 방식으로 팩토리얽밧을 재귀적으로 구현한 함수다.......-

- 함수 외부에서는 함수 변수 `factorialVar`로 함수를 호출했으며, 함수 내부에서 이뤄지는 재귀 호출은 `factorial()`함수 이름으로 처리한다는 것을 알 수 있다.
- 함수명 `factorial()`으로 함수 외부에서 해당 함수를 호출하지 못해 에러가 발생한다.

	function statement와 function expression에서의 세미콜론
    **일반적으로 자바스크립트 코드를 작성할 때 함수 선언문 방식으로 선언된 함수의 경우는 함수 끝에 세미콜론(;)을 붙이지 않지만,
    함수 표현식 방식의 경우는 세미콜론(;)을 붙이는 것을 권장한다.**
    자바스크립트는 `C`와 같이 세미콜론 사용을 강제하지는 않는다(인터프리터가 자동으로 세미콜론을 삽입함)
    

> **결론은 함수표현식 방식에서는 세미콜론을 반드시 사용하자** (자세한 사항은 p.77 참조)


#### 4.1.4 Function()생성자 함수를 통한 함수 생성하기

- 자바스크립트의 함수도 `Functioin()`이라는 기본 내장 생성자 함수로부터 생성된 `객체`라고 볼 수 있다.
- 앞에서 살펴본 함수선언문이아 함수 표현식 방식도 `Function()`생성자 함수가 아닌 함수 리터럴 방식으로 함수를 생성하지만,
- 결국엔 이 또한 내부적으로는 `Function()`생성자 함수로 함수가 생성된다고 볼 수 있다.

```javascript
var add = new Function('x', 'y', 'return x + y');
console.log(add(3, 4)); // 7
```
> 이와 같은 방식은 실제로 많이 사용하지 않으므로 상식선에서만 알아두자..


#### 4.1.5 함수 호이스팅

처음 언급했던 함수의 3가지 생성방법에 대해 동작방식이 약간씩 다르다고 했었는데, 그중의 한가ㅏ 바로 `함수 호이스팅(Function Hoistion)` 이다.

> 더글러스 크락포드의 저서 [더글라스 크락포드의 자바스크립트 핵심 가이드]에서 함수표현식만을 사용할 것을 권하고 있다.
> 그 이유 중의 하나가 바로 함수 호이스팅 때문이다.

```javascript
console.log(add(2,3)); // 5

// 함수 선언문 형태로 add() 함수 정의
function add(x, y) {
    return x + y;
}

console.log(add(3, 4)); // 7
```

> 위 코드를 첫번째 줄을 보면 `add()`함수가 정의되지 않았음에도 정상적으로 호출된것을 알 수 있다.

이것은 함수가 자신이 위치한 코드에 상관없이 **함수 선언문 형태로 정의한 함수의 유효범위는 코드의 맨 처음부터 시작한다**는 것을 확인할 수 있다. 이것을 **함수 호이스팅**이라고 부른다.

더클라스크락포드는 이러한 함수 호이스팅은 함수를 사용하기 전에 반드시 선언해야 한다는 규칙을 무시하므로,
코드의 구조를 엉성하게 만들 수도 있다고 지적하며, 함수 표현식 사용을 권장하고 있다.

혹시 모르니 아래 `함수 표현식` 방식으로 인한 함수 호이스팅 코드를 실행해보자

```javascript
add(2,3); // uncaught type error

// 함수 표현식 형태로 add() 함수 정의
var add = function (x, y) {
    return x + y;
};

add(3, 4);
```

---

### 4.2) 함수 객체 : 함수도 객체다


#### 4.2.1 자바스크립트에서는 함수도 객체다

- 자바스크립트에서는 `함수도 객체`다. 즉 함수의 기본 기능인 코드 실행뿐만 아니라, 함수 자체가 일반 객체처럼 프로퍼티들을 가질수 있다는 것이다.

예제를 통해 살펴보자

```javascript
// 함수 선언 방식으로 add()함수 정의
function add(x, y) {
    return x+y;
}

// add() 함수 객체에 result, status 프로퍼티 추가
add.result = add(3, 2);
add.status = 'OK';

console.log(add.result); // 5
console.log(add.status); // 'OK'
```
> `add()`함수를 생성할 때 함수 코드는 함수 객체의 `[[Code]]` 내부 프로퍼티`에 자동으로 저장된다.
> 즉 아래와 같다.

`[[Code]]`  --------> return x+y;
`result`  --------> 5;
`status`  --------> 'OK';


#### 4.2.2 자바스크립트에서 함수는 값으로 취급된다.

> **함수도 일반 객체처럼 취급될 수 있다**는 말은 다음과 같은 동작이 가능하다는 뜻이다.

- 리터럴에 의해 생성
- 변수나 배열의 요소, 객체의 프로퍼티 등에 할당 가능
- 함수의 인자로 전달 가능
- 함수의 리턴값으로 리턴가능
- 동적으로 프로퍼티를 생성 및 할당 가능

> 이와 같은 특징이 있으므로 자바스크립트에서는 함수를 `일급~FirstClass~ 객체`라고 부른다.

여기서 `일급객체`라는 말은 컴퓨터 프로그래밍 언어 분야에서 쓰이는 용어로서, 앞에서 나열한 기능이 모두 가능한 객체를 일급 객체라고 부른다.


##### 4.2.2.1 변수나 프로퍼티의 값으로 할당

함수는 숫자나 문자열처럼 변수나 프로퍼티의 값으로 할당될 수 있다. 예제를 통해 살펴보자

```javascript
// 변수에 함수 할당
var foo = 100;
var bar = function () { return 100; };
console.log(bar()); // 100

// 프로퍼티에 함수 할당
var obj = {};
obj.baz = function () {return 200; }
console.log(obj.baz()); // 200
```

##### 4.2.2.2 함수 인자로 전달

함수는 다른 함수의 인자로도 전달이 가능하다. 다음 예제를 살펴보자. `foo()`함수 표현식 방법으로 생성한 함수로서, 인자로 받은 func 함수를 내부에서 함수 호출 연산자()를 붙여 호출하는 기능을 한다.


```javascript
// 함수 표현식으로 foo() 함수 생성
var foo = function(func) {
    func(); // 인자로 받은 func() 함수 호출
};

// foo() 함수 실행
foo(function() {
    console.log('Function can be used as the argument.');
});
// 출력결과   Function can be used as the argument.
```


> `foo()`함수를 호출할 때, 함수 리터럴 방식으로 생성한 `익명함수`를 func인자로 넘겼다.
> 따라서 `foo()`함수 내부에서는 func 매개변수로 인자에 넘겨진 `함수`를 호출할 수 있다.
> 출력결과를 보면 알 수 있듯이 인자로 넘긴 익명 함수가 `foo()`함수 내부에서 제대로 호출된 것을 알 수 있다.


##### 4.2.2.3 리턴값으로 활용

함수는 다른함수의 리턴값으로도 활용할 수 있다. 다음 에제에서 `foo()`함수는 `console.log()`를 이용해 출력하는 간단한
익명 함수를 리턴하는 역할을 한다.
이것이 가능한 이유 또한 함수 자체가 값으로 취급되기 때문이다.


```javascript
// 함수를 리턴하는 foo() 함수 정의
var foo = function() {
    return function () {
        console.log('this function is the return value.')
    };
};
// foo() 함수가 호출되면, 리턴값으로 전달되는 함수가 bar변수에 저장된다.
var bar = foo();
//()함수 호출 연산자를 이용해 bar()로 리턴된 함수를 실행하는 것이 가능하다.
bar();
```


#### 4.2.3 함수 객체의 기본 프로퍼티

계속 강조하듯이 자바스크립트에서는 함수 역시 객체다(그만큼 중요한 내용이므로 반복숙달 하자)
이것은 함수 역시 일반적인 객체의 기능에 추가로 호출됐을 때 정의된 코드를 실행하는 기능을 가지고 있다는 것이다.
또한, 일반객체와는 다르게 추가로 **함수 객체만의 표준프로퍼티**가 정의되어 있다.
실제 함수가 어떤 객체 형태로 되어있는지름 코드를 통해 직접 확인해보자.

```javascript
function add(x, y) {
    return x + y;
}

console.dir(add);
```

위 코드를 브라우저 개발자도로 살펴보면 아래와 같다.

```console
function add(x,y)
    arguments: null
    caller: null
    length: 2
    name: "add"
    prototype: add
    __proto__: ()
        apply: apply()
        arguments: (...)
        get arguments: ThrowTypeError()
        set arguments: ThrowTypeError()
        bind: bind()
        call: call()
        caller: (...)
        get caller: ThrowTypeError()
        set caller: ThrowTypeError()
        constructor: Function()
        length: 0
        name: ""
        toString: toString()
        __proto__: Object
    <function scope>

```

1) `length`와 `prototype 프로퍼티`
> A5 스크립트 명세서에는 모든 함수가 `length`와 `prototype 프로퍼티`를 가져야 한다고 기술하고 있다.
위 `add()`함수 역시 `length`와 `prototype 프로퍼티`를 가지고 있는 것을 확인할 수 있다.

> length나 prototype 이외의`name`, `aller` `guments` `_proto__` 프로퍼티는 ECMA 표준이 아니다.

2) `name`

> `name`프로퍼티는 함수의 이름을 나타내며, 익명 함수라면 이 값은 빈 문자열이 된다.

3) `caller`

> `caller 프로퍼티`는 자신을 호출한 함수를 나타낸다. 
이 예제에서는 `add()`함수를 호출하지 않았으므로, `null`값이 나왔다.

4) `arguments`

> `arguments 프로퍼티`는 함수를 호출할 때 전달된 인자값을 나타내는데, 현재는 `add()`함수가 호출된 상태가 아니므로 `null`값이 출력됬다.

5)` __proto__`

> **3.4 프로토타입** 에서 모든 자바스크립트 객체는 자신의 프로토타입을 가리키는 **[[Prototype]]라는 내부 프로퍼티**를 가진다고 설명했다.
> 즉 `[[Prototype]]`과 `__proto__`는 같은 개념이라고 생각하면 된다.
add()함수 역시 자바스크립트 객체이므로 `__proto__`프로퍼티를 가지고 있고 이를통해 자신의 부모 역할을 하는 프로토타입 객체를 가리킨다.

> ECMA 표준에서는 `add()`와 같이 함수 객체의 부모 역할을 하는 프로토타입 객체를 `Function.prototype 객체`라고 명명하고 있으며, 이것 역시 **함수 객체**라고 정의하고 있다.


##### 4.2.3.1 length 프로퍼티
함수 객체의 length 프로퍼티는 앞서 설명했듯이 ECMAScript에서 정한 모든 함수가 가져야 하는 표준 프로퍼티로서, 
함수가 정상적으로 실행될 때 기대되는 인자의 개수를 나타낸다.

```javascript
function func0() {

}

function func1(x) {
    return x;
}

function func2(x, y) {
    return x + y;
}

function func3(x, y, z) {
    return x + y + z;
}

console.log('func0.length - ' + func0.length); // func0.length - 0
console.log('func1.length - ' + func1.length); // func1.length - 1
console.log('func2.length - ' + func2.length); // func2.length - 2
console.log('func3.length - ' + func3.length); // func3.length - 3
```

> 출력값은 살펴보면 함수 객체의 length 프로퍼티는 함수를 작성할 때 정의한 인자 개수를 나타내고 있음을 알 수 있다.


##### 4.2.3.2 prototype 프로퍼티

- 모든 함수는 객체로서 `prototype 프로퍼티`를 가지고 있다. 여기서 주의할 것은 함수 객체의 `prototype 프로퍼티`는 앞서 설명한 모든 객체의 부모를 나타내는 `내부프로퍼티`인 `[[Prototype]]`과 혼동하지 말아야 한다는 것이다.
- prototype 프로퍼티는 함수가 생성될 때 만들어지며, constructor 프로퍼티 하나만 있는 객체를 가리킨다.
- prototype 프로퍼티가 가리키는 프로토타입 객체의 유일한 constructor 프로퍼티는 자신과 열결된 함수를 가리킨다.
- 즉 `js`에서는 함수를 생성할 때, 함수 자신과 연결된 프로토타입 객체를 동시에 생성하며, 이 둘은 각각 prototype과 constructor라는 프로퍼티로 서로를 참조하게 된다.

>  다음 예제를 통해 함수의 프로토타입 객체를 좀 더 살펴보자.

```javascript
// myFunction 함수 정의
function myFunction() {
    return true;
}

console.dir(myFunction.prototype);
console.dir(myFunction.prototype.constructor);
```

1) 우선 `myFunction()`라는 함수를 생성했다. 함수가 생성됨과 동시에 `myFunction()` 함수의 prototype프로퍼티에는 이 함수와 연결된 프로토타입 객체가 생성된다.

2) `myFunction.prototype`은 `myFunction()`함수의 프로토타입 객체를 의미한다.

3) `myFunction.prototype.constructor`의 값을 출력함으로써 프로토타입 객체와 매핑된 함수를 알아볼 수 있다.
결과값을 보면 myFunction() 함수를 가리키고 있다.


---

### 4.3 함수의 다양한 형태

#### 4.3.1 콜백함수
- 자바스크립트 함수표현식에서 **함수이름**은 꼭 붙이지 않아도 되는 선택사항이다.
- 즉, 함수의 이름을 지정하지 않아도 함수가 정의되며 이러한 함수가 익명함수다.
- 이러한 익명 함수의 대표적인 용도가 바로 `콜백함수`이다.

콜백함수는 코드를 통해 명시적으로 호출하는 함수가 아니라, 개발자는 단지 함수를 등록하기만 하고, 어떤 이벤트가 발생했거나 특정 시점에 도달했을때 시스템에서 호출되는 함수를 말한다.
또한, 특정 함수의 인자로 넘겨서, 코드 내부에서 호출되는 함수 또한 콜백 함수가 될 수있다.

대표적인 콜백함수의 사용 예가 자바스크립트에서의 이벤트 핸들러 처리이다.

> 다음 코드는 웹페이지가 로드됬을때 경고창을 띄워 주는 간단한 예제다.
> `window.onload`는 이벤트 헨들러로서, 웹 페이지의 로딩이 끝나는 시점에 `load`이벤트가 발생하면 실행된다.
> 예제에서는 `window.onload`이벤트 핸들러를 익명 함수로 연결했다. 따라서 익명 함수가 콜백 함수로 등록된 것이다.

```javascript
<!DOCTYPE html>
<html>
<body>
    <script>
        // 페이지 로드시 호출될 콜백 함수
        window.onload = function() {
            alert('This is the callback function.');
        };
    </script>
</body>
</html>
```


#### 4.3.2 즉시실행함수

- 함수를 정의함과 동시에 바로 실행되는 함수를 `즉시 실행 함수`라고 한다.
- 이 함수도 익명함수를 응용한 형태이다.

```javascript
(function (name) {
    console.log('This is the immediate function --> ' + name);
})('foo');
```

> `즉시실행함수`를 만드는 방법은 간단하다.

- 우선 함수 리터럴을 `()`로 둘러싼다. 이때 함수 이름이 있든 없든 상관없다.
- 그런 다음 함수가 바로 호출될 수 있게 `()`괄호 쌍을 추가한다.
- 이때 괄호 안에 값을 추가해 `즉시 실행 함수`의 인자로 넘길 수가 있다. (예제의 경우는 `('foo')`로 즉시 실행함수를 호출했으며, 이때 'foo'를 인자로 넘겼다
- 이렇게 함수가 선언되자마자 실행되게 만든 즉시 실행함수의 경우, 같은 함수를 다시 호출할 수 없다.
- 따라서 즉시실행함수의 이러한 특징을 이용한다면 **최초 한 번의 실행만을 필요로 하는 초기화 코드부분**등에 사용할 수있다.

> 즉시 실행 함수의 또 다른 용도를 알아보자.

그것은 바로 `jQuery`와 같은 자바스크립트 라이브러리나 프레임워크 소스들에서 사용된다.
`jQuery`최신 소스코드를 살펴보면 소스의 시작 부분과 끝 부분이 다음 에제와 같이 즉시 실행 함수 형태로 구성되어 있음을 확인할 수 있다.
즉  `jQeury`소스 코드 전체가 즉시 실행 함수고 감싸져 있다.

```javascript
(function(window, undefined){
......생략
})(window);
```

> 이렇게 `jQuery`에서 즉시 실행 함수를 사용하는 이유는 자바스크립트의 변수 유효범위 특성때문이다.
> 자바스크립트에서는 **함수유효범위**를 지원한다.
> 자바스크립트는변수를 선언할 경우 프로그램 전체에서 접근할 수 있는 전역 유효범위를 가지게 된다.
> 그러나 함수 내부에서 정의된 매개변수와 변수들은 함수 코드 내부에서만 유효할 뿐 함수 밖에서는 유효하지 않다.
> 이것은 달리 ㅁ라하면 함수 외부의 코드에서 함수 내부의 변수를 엑세스하는 게 불가능 하다는 것이다.
이에 대한 자세한 내용은 5장 참고

> 따라서 라이브러리 코드를 이렇게 즉시 실행 함수 내부에 정의해두게 되면, 라이브러리 내의 변수들은 함수 외부에서 접근할 수 없다.

> 따라서 이렇게 즉시실행함수 내에 라이브러리 코드를 추가하면 전역 네임스페이스를 더럽히지 않으므로, 이후 다른 자바스크립트 라이브러리들이 동시에 로드가 되더라도 라이브러리 간 변수 이름 충돌 같은 문제를 방지할 수 있다.

#### 4.3.3 내부함수

자바스크립트에서는 함수 코드 내부에서도 다시 함수 정의가 가능하다.
이렇게 함수 내부에 정의된 함수를 **내부 함수**라고 부른다.

내부 함수는 자바스크립트의 기능을 보다 강력하게 해주는 클로저를 생성하거나 부모 함수 코드에서 외부에서의 접근을 막고 독립적인 헬퍼 함수를 구현하는 용도 등으로 사용한다.

```javascript
// parent() 함수 정의
function parent() {
    var a = 100;
    var b = 200;

    // child() 내부 함수 정의
    function child() {
        var b = 300;
        console.log(a);
        console.log(b);
    }
    child();
}
parent();
child();
```

> 출력결과

```javascript
100
300
Uncaught ReferenceError: chiled is not defined
```

> 내부 함수는 자신을 둘러썬 외부 함수의 변수에 접근가능하다. 이게 가능한 이유는 자바스크립트의 `스코프체이닝`때문이다.
> 관련된 자세한 내용은 5장에서 다룬다.(다음장)

> 마지막줄에 `parent()`함수 외부에서 `child()`함수 호출을 시도하지만, 함수가 정의되어 있지 않다는 에러가 발생한다.

> 이것은 자바스크립트의 함수 스코핑 때문이다.
> 즉, 함수 내부에 선언된 변수는 함수 외부에서 접근이 불가능하다.
> 이 규칙은 내부 함수도 그대로 적용된다.
>
> 하지만 함수 외부에서도 특정함수 스코프 안에 선언된 내부 함수를 호출할 수 있다. 
> 가령, 부모 함수에서 내부 함수를 외부로 리턴하면, 부모 함수 밖에서도 내부 함수를 호출하는 것이 가능하다.

> 아래 예제는 parent()함수를 호출하고, 이 결과로 반환된 inner()함수를 호출하는 간단한 예제이다.

```javascript
function parent() {
    var a = 100;

    // child() 내부 함수
    var child = function () {
        console.log(a);
    }

    // child() 함수 반환
    return child;
}

var inner = parent();
inner();
```

> 1) 내부 함수를 함수 표현식 형식으로 정의하고, child 함수 변수에 저장했다. 그리고 parent()함수의 리턴값으로 
> 내부 함수의 참조값을 가진 child 함수 변수를 리턴했다.

> 2) parent()함수가 호출되면, inner변수에 child함수 변수 값이 리턴된다. child함수 변수는 내부 함수의 참조값이 있으므로, 결국 inner 변수도 child()내부 함수를 참조했다.

> 3) 때문에 inner변수에 함수 호출 연산자 `()`를 붙여 함수 호출 구문을 만들면, 
> parent()함수 스코프 밖에서도 내부 함수 child()가 호출된다.
> 호출하는 내부 함수에는 a 변수가 정의되어 있지 않아, 스코프 체이닝으로 부모 함수에 a 변수가 정의되어 있는지 확인하게 되고, a가 정의되어 있으면 그 값이 출력된다.


이와 같이 실행이 끝난 parent()와 같은 부모 함수 스코프의 변수를 참조하는 inner()와 같은 함수를 `클로저`라고 한다.


#### 4.3.4 함수를 리턴하는 함수

- 자바스크립트에서는 함수도 일급 객체이므로 일반 값처럼 함수 자체를 리턴할 수도 있다.
- 이러한 특징은 다양한 활용이 가능해진다.
- 함수를 호출함과 동시에 다른함수로 바꾸거나,
- 자기 자신을 재정의하는 함수를 구현할 수 있다.
- 이러한 함수 유형 또한 자바스크립트의 언어적인 유연성을 보여주는 좋은 활용예다.

> 다음 에제는 함수를 리턴하는 self 라는 함수를 정의했다

```javascript
// self() 함수
var self = function () {
    console.log('a');
    return function () {
        console.log('b');
    }
}
self = self();  // a
self();  // b
```

1. 처음 self()함수가 호출됐을 때는 `a`가 출력된다. 
그리고 다시 self 함수 변수에 self()함수 호출 리턴값으로 내보낸 함수가 저장된다.

2. 두번째로 self()함수가 호출됐을 때는 `b`가 출력된다.
즉, 1번에서 self()함수 호출 후에, self 함수 변수가 가리키는 함수가 원래 함수에서 리턴 받은 새로운 함수가 변경됐기 때문이다.


### 4.4 함수 호출과 this

> 함수의 기본적인 기능은 당연히 함수를 호출하여 코드를 실행하는 것이다.
> 하지만 자바스크립트 언어 자체가 `C/C++`같은 엄격한 문법 체크를 하지 않는 자유로운 특성의 언어이므로 함수 호출 또한 다른 언어와는 달리 자유롭다.


#### 4.4.1 arguments 객체

> `C`와 같은 엄격한 언어와 달리, 자바스크립트에서는 함수를 호출할 때 함수 형식에 맞춰 인자를 넘기지 않더라도 에러가 발생하지 않는다. 
> 백문이 불여일견 코드를 보자

```javascript
function func(arg1, arg2) {
    console.log(arg1, arg2);
}

func();   // undefined undefined
func(1);  // 1 undefined
func(1,2);  // 1 2
func(1,2,3); // 1 2
```

위 예제를 보면 알 수 있듯이 정의된 함수의 인자보다 적게 함수를 호출했을 경우에는 `undefined`값이 할당 되고
이와 반대로 정의된 인자 개수보다 많게 함수를 호출했을 경우는 에러는 발생하지 않고, 초과된 인수는 무시된다.

> 자바스크립트의 이러한 특성 때문에 함수 코드를 작성할 때, 런타임 시에 호출된 인자의 개수를 확인하고
> 이에 따라 동작을 다르게 해줘야 할 경우가 있다.
> 이를 가능케 하는 게 바로 `arguments 객체`다
> 자바스크립트에서는 함수를 호출할 때 인수들과 함께 암묵적으로 arguments객체가 함수 내부로 전달되기 때문이다.

여기서 `arguments 객체`는 함수를 호출할 때 넘긴 인자들이 배열 형태로 저장된 객체를 의미한다.
특이한 점은 이 객체는 실제 배열이 아닌 **유사 배열 객체**라는 점이다.

> 유사배열 객체는 이전장에서 살펴봤으니 arguments 객체를 살펴보자

```javascript
// add() 함수
function add(a, b) {
    // arguments 객체 출력
    console.dir(arguments);
    return a+b;
}

console.log(add(1)); // NaN
console.log(add(1,2)); // 3
console.log(add(1,2,3)); // 3
```

> 아래는 개발자도구를 통해 살펴본 값이다.
```
Arguments[1]
0: 1		// 호출 시에 넘긴 인자를 배열 형태로 저장
callee: add(a, b)
length: 1		//호출 시에 넘긴 인자 개수
Symbol(Symbol.iterator): values()
__proto__: Object
NaN
Arguments[2]
0: 1		// 호출 시에 넘긴 인자를 배열 형태로 저장
1: 2
callee: add(a, b)
length: 2		//호출 시에 넘긴 인자 개수
Symbol(Symbol.iterator): values()
__proto__: Object
3
Arguments[3]
0: 1		// 호출 시에 넘긴 인자를 배열 형태로 저장
1: 2
2: 3
callee: add(a, b)
length: 3		//호출 시에 넘긴 인자 개수
Symbol(Symbol.iterator): values()
__proto__: Object
3
```

- arguments는 객체이지 배열이 아니다. 즉, length 프로퍼티가 있으므로 배열과 유사하게 동작하지만, 배열은 아니므로 
- 배열 메소드를 사용할 경우 에러가 발생한다는 것에 주의해야 한다.
- 물론 유사배열객체에서 배열 메소드를 사용하는 방법이 있다.

> arguments 객체는 매개변수 개수가 정확하게 정해지지 않은 함수를 구현하거나, 전달된 인자의 개수에 따라 서로 다른 처리를 해줘야 하는 함수를 개발하는 데 유용하게 사용할 수 있다.


```javascript
function sum(){
	var result = 0;
    
    for(var i=0; i<arguments.length; i++){
    	result += arguments[i];
    }
    return result;
}

console.log(sum(1,2,3));		// 출력값 6
console.log(sum(1,2,3,4,5,6,7,8,9));		// 출력값 45
```

#### 4.4.2 호출 패턴과 this 바인딩

자바스크립트에서 함수를 호출할 때 기존 매개변수로 전달되는 인자값에 더해, 
`arguments 객체` 및 `this 인자`가 함수 내부로 암묵적으로 전달된다.
여기서 특히 `this 인자`는 고급 자바스크립트 개발자로 거듭나려면 확실히 이해해야 하는 핵심 개념이다.

`this`가 이해하기 어려운 이유는 자바스크립트의 여러 가지 ==**함수가 호출되는 방식(호출패턴)**==에  따라 this가
다른 ==**객체를 참조**==하기 ==**(this 바인딩)**==때문이다.
따라서 이번 절에서는 함수 호출 패턴과 해당 패턴에 따라 this가 어떤 객체에 바인딩이 되는지에 대해서 알아보자.



##### 4.4.2.1 객체의 메소드 호출할 때 this 바인딩

객체의 프로퍼티가 함수일 경우, 이 함수를 메소드라고 부른다.
이러한 메소드를 호출할 때, 메소드 내부 코드에서 사용된 this는 **++해당 메소드를 호출한 객체로 바인딩++**된다.


```javascript
// myObject 객체 생성
var myObject = {
    name: 'foo',
    sayName: function () {
        console.log(this.name);
    }
};

// ohterObject 객체 생성
var otherObject = {
    name: 'bar'
};

// otherObject.sayName() 메서드
otherObject.sayName = myObject.sayName;

// sayName() 메서드 호출
myObject.sayName();
otherObject.sayName();
```

> 출력 결과

```
foo
bar
```


##### 4.4.2.2 함수를 호출할 때 this 바인딩

자바스크립트에서는 함수를 호출하면, 해당 함수 내부 코드에서 사용된 ==**this 는 전역 객체에 바인딩**==된다.
브라우저에서 자바스크립트를 실행하는 경우 전역 객체는 ==**window 객체**==가 된다.

자바스크립트의 모든 전역 변수는 실제로는 이러한 전역 객체의 프로퍼티들이다.

```javascript
var foo = "I'm foo"; // 전역 변수 선언

console.log(foo); // I’m foo
console.log(window.foo); // I’m foo
```

> 위 코드를 보면 알 수 있듯이 전역 변수는 전역 객체(window)의 프로퍼티로도 접근할 수가 있다.
> 이제 함수를 호출할 때 this바인딩이 어떻게 되는지를 다음 예제 코드를 살펴보자.

```javascript
var test = 'This is test';
console.log(window.test);

// sayFoo() 함수
var sayFoo = function () {
    console.log(this.test); // sayFoo() 함수 호출 시 this는 전역 객체에 바인딩된다.
};

sayFoo(); // this.test 출력
```

> 출력결과

```
This is test
This is test
```

> 자바스크립트의 전역 변수는 전역 객체(window)의 프로퍼티로 접근 가능하다..는점을 상기

하지만 이러한 함수 호출에서의 this바인딩 특성은 ==**내부함수를 호출했을 경우**==에도 그대로 적용되므로,
내부 함수에서 this를 이용할 때는 주의해야 한다.

```javascript
// 전역 변수 value 정의
var value = 100;

// myObject 객체 생성
var myObject = {
    value: 1,
    func1: function () {
        this.value += 1;
        console.log('func1() called. this.value : ' + this.value);

        // func2() 내부 함수
        func2 = function () {
            this.value += 1;
            console.log('func2() called. this.value : ' + this.value);

            // func3() 내부 함수
            func3 = function () {
                this.value += 1;
                console.log('func3() called. this.value : ' + this.value);
            }

            func3();// func3() 내부 함수 호출
        }
        func2(); // func2() 내부 함수 호출
    }
};
myObject.func1(); // func1() 메서드 호출
```

> 출력결과

```
func1() called. this.value : 2
func2() called. this.value : 101
func3() called. this.value : 102
```

자바스크립트에서는 내부 함수 호출패턴을 정의해 놓지 않기 때문에 이와같은 결과가 나왔다.

> 이렇게 내부 함수가 this를 참조하는 자바스크립트의 한계를 극복하려면 ==**부모함수(위 에제의 func1()메소드)의 this**==를 내부함수가 접근 가능한 ==**다른 변수에 저장**==하는 방법이 사용된다.
> 관례상 this의 값을 저장하는 변수의 이름을 `that`이라고 짓는다. 


```javascript
// 내부 함수 this 바인딩
var value = 100;

var myObject = {
    value: 1,
    func1: function () {
        var that = this;

        this.value += 1;
        console.log('func1() called. this.value : ' + this.value);

        func2 = function () {
            that.value += 1;
            console.log('func2() called. this.value : ' + that.value);

            func3 = function () {
                that.value += 1;
                console.log('func3() called. this.value : ' + that.value);
            }
            func3();
        }
        func2();
    }
};

myObject.func1(); // func1 메서드 호출

```


> 자바스크립트에서는 이와 같은 this바인딩의 한계를 극복하려고, this 바인딩을 명시적으로 할 수 있도록 `call`과 `apply` 메소드를 제공하는데, 이는 다다음 절에서 알아보자.
> 또한 jQuery, underscore.js 등과 같은 자바스크립트 라이브러리들의 경우 bind라는 이름의 메소드를 통해,
> 사용자가 원하는 객체를 this에 바인딩할 수 있는 기능을 제공하고 있다.

##### 4.4.2.3 생성자 함수를 호출할 때 this 바인딩

3장에서 살펴봤듯이 자바스크립트 객체를 생성하는 방법은 크게 객체 리터럴 방식이나 생성자 함수를 이용하는 두가지 방법이 있다.

> 자바와 같은 객체지향 언어에서의 생성자 함수의 형식과는 다르게 그 형식이 정해져 있는 것이 아니라, 기존 함수에 new연산자를 붙여서 호출하면 해당 함수는 생성자 함수로 동작한다.

> 이는 반대로 생각하면 일반 함수에 new를 붙여 호출하면 원치 않는 생성자 함수처럼 동작할 수 있다는 뜻이다.
> 따라서 대부분의 자바스크립트 스타일 가이드에서는 특정 함수가 생성자 함수로 정의되어 있음을 알리려고 
> ++**함수 이름의 첫 문자를 대문자로 쓰기**++를 권하고 있다.

생성자 함수를 호출할 때, 생성자 함수 코드 내부에서 `this`는 바로 이전에 알아본 `메소드`와 `함수 호출방식`에서의 this바인딩과는 또 다르게 동작한다.

> 그전에 생성자 함수가 동작하는 방식을 먼저 알아보자

new 연산자로 자바스크립트 함수를 생성자로 호출하면, 다음과 같은 순서로 동작한다.

1. 빈객체 생성 및 this 바인딩
	- 생성자 함수 코드가 실행되기 전 **빈 객체**가 생성된다.
	- 바로 이 객체가 생성자 함수가 새로 생성하는 객체이며, 이 객체는 this로 바인딩된다.  따라서 이후 생성자 함수의 코드 내부에서 사용된 this는 이 빈객체를 가리킨다.
	- 하지만 여기서 생성된 객체는 엄밀히 말하면 **빈 객체는 아니다**
	- 앞서 살펴본것처럼 `js`의 모든 객체는 자신의 부모인 프로토타입 객체와 연결되어 있으며, 이를 통해 부모 객체의 프로퍼티나 메소드를 마치 자신의 것처럼 사용할 수가 있기 때문이다.
	- 이렇게 생성자 함수가 생성한 객체는 자신을 생성한 **생성자 함수의 prototype 프로퍼티**가 가리키는 객체를 **자신의 프로토타입 객체**로 설정한다.

2. this를 통한 프로퍼티 생성
	- 이후에는 함수 코드 내부에서 this를 사용해서, 앞에서 생성된 빈 객체에 동적으로 프로퍼티나 메소드를 생성할 수 있다.
    
3. 생성된 객체 리턴
	- 리턴문이 동작하는 방식은 경우에 따라 다르므로 주의해야 한다.
	- 가장 일반적인 경우로 리턴문이 없을 경우, **this로 바인딩된 새로 생성한 객체가 리턴**된다.
	- 이것은 명시적으로 this를 리턴해도 결과는 같다
	- 하지만 리턴값이 새로 생성한 객체(this)가 아닌 다른 객체를 반환하는 경우는 생성자 함수를 호출했다고 하더라도 this가 아닌 해당 객체가 리턴된다.


```javascript
// Person 생성자 함수
var Person = function (name) {
    this.name = name;
};

// foo 객체 생성
var foo = new Person('foo');
console.log(foo.name); // foo
```


> #### 객체 리터럴 방식과 생성자 함수를 통한 객체 생성 방식의 차이

각각 두가지의 차이점은 뭘까? 

> 예제를 통해 살펴보자

```javascript
//객체 리터럴 방식으로 foo 객체 생성
var foo = {
    name: 'foo',
    age: 35,
    gender: 'man'
};
console.dir(foo);

//생성자 함수
function Person(name, age, gender, position) {
    this.name = name;
    this.age = age;
    this.gender = gender;
}

// Person 생성자 함수를 이용해 bar 객체, baz 객체 생성
var bar = new Person('bar', 33, 'woman');
console.dir(bar);

var baz = new Person('baz', 25, 'woman');
console.dir(baz);
```

> `foo`객체와 같이 객체리터럴 방식으로 생성된 객체는 같은 형태의 객체를 재생성할 수 없다.

> `Person()`생성자 함수를 사용해서 객체를 생성하면, 생성자 함수를 호출할 때 다른 인자를 넘김으로써 같은 형태의 서로다른 객체 bar와 bax를 생성할 수 있다.

개발자 도구의 console.dir 값도 확인해 보자

> #### 생성자 함수를 new를 붙이지 않고 호출할 경우

`js`에서는 일반함수와 생성자 함수가 별도의 차이가 없다.
new를 붙여서 함수를 호출하면 생성자 함수로 동작하는 것이다.
때문에 객체 생성을 목적으로 작성한 생성자 함수를 new 없이 호출하거나 일반함수를 new를 붙여서 호출할 경우 코드에서
오류가 발생할 수 있다.

> 그이유는 this 바인딩 방식이 각각 다르기 때문이다.

- 일반 함수 호출은 this가 window 전역객체에 바인딩
- 생성자 함수 호출의 경우 this는 새로 생성되는 빈 객체에 바인딩 


```javascript
//생성자 함수
function Person(name, age, gender, position) {
    this.name = name;
    this.age = age;
    this.gender = gender;
}

var qux = Person('qux', 20, 'man');
console.log(qux);  // undefined

console.log(window.name); // qux
console.log(window.age); // 20
console.log(window.gender); // man
```
> 위와 같이 `js`에서는 일반 함수와 생성자 함수의 구분이 별도로 없으므로, 일반적으로 생성자 함수로 사용할 함수는 
> 첫 글자를 대문자로 표기하는 네이밍 규칙을 권장한다.
> 그러나 이러한 규칙을 사용하더라도 결국 new를 사용해서 호출하지 않을 경우 에러가 발생하므로
> 더글라스 크락포드와 같은 전문가들은 객체를 생성하는 **다음과 같은 별도의 코드 패턴**을 사용하기도 한다.


```javascript
// 앞에서 설명한 위험성을 피하려고 널리 사용되는 패턴이 있다.

function A(arg){
	if(!(this instanceof A))
		return new A(arg);
	this.value = arg ? arg :0;
}

var a = new A(100);
var b = A(10);

console.log(a.value);		//출력값 100
console.log(b.value);		//출력값 10
console.log(global.value);	//출력값 undefined
```
> `함수 A`에서는 `A`가 호출될 때, this가 `A`의 인스턴스인지를 확인하는 분기문이 추가되었다.
> rhis가 `A`의 인스턴스가 아니라면, `new`로 호출된 것이 아님을 의미하고, 이경우 `new`로 A를 호출하여 반환하게 하였다.

> 이렇게 하면 `var b = A(10);`과 같이 사용자가 사용했다고 하더라도, `전역 객체`에 접근하지않고, `새 인스턴스`가 생성되어 b에 반환될 것이다.

> 이와 같이 하면, 특정 함수 이름과 상관없이 이 패턴을 공통으로 사용하는 모듈을 작성할 수 있는 장점이 있다.

##### 4.4.2.4 call과 apply메소드를 이용한 명시적인 this바인딩

자바스크립트에서 함수호출이 발생할 때마다 각각의 상황에 따라 `this`가 정해진 객체에 자동으로 바인딩 된다는것을 확인했다
자바스크립트에서는 이러한 내부적인 `this`바인딩 이외에도 `this`를 특정객체에 **명시적으로 바인딩**시키는 방법도 제공한다.

> 이를 가능하게 하는 것이 바로 **함수 객체의 기본프로퍼티**에서 간단히 설명한 `apply()`나 `call()`메소드다.
> 다음과 같은 형식으로 `apply()` 메소드를 호출하는 것이 가능하다

```javascript
function.apply(thisArg, argArray)
```

- 첫번째 인자 thisArg는 apply()메소드를 호출한 함수 내부에서 사용한 this에 바인딩할 객체를 가리킨다.
- 두번째 인자 argArray 인자는 함수를 호출할 때 넘길 인자들의 배열을 가리킨다.

> 두번째 인자인 argArray 배열을 자신을 호출한 함수의 인자로 사용하되, 이 함수 내부에서 사용된 this는 첫 번째 인자인
> thisArg객체로 바인딩해서 함수를 호출하는 기능을 하는 것이다.


```javascript
// 생성자 함수
function Person(name, age, gender) {
    this.name = name;
    this.age = age;
    this.gender = gender;
}

// foo 빈 객체 생성
/*
	foo는 객체 리터럴 방식으로 생성한 빈 객체다.
*/
var foo = {};

// apply() 메서드 호출
/*
	apply()메소드를 사용해서, Person()함수를 호출한 코드다.
    이 코드는 결국 Person('foo',30,'man') 함수를 호출하면서, this를 foo 객체에 명시적으로 바인딩하는 것을 의미한다.
*/
Person.apply(foo, ['foo', 30, 'man']);
console.dir(foo);
```

> 출력결과

```
Object
	age: 30
    gender: "man"
    name: "foo"
    __proto__: Object
```

출력결과를 보면 foo 객체에 제대로 프로퍼티가 생성되어 있음을 확인할 수 있다.
call()메서드의 경우로 살펴보면 아래와 같다

```javascript
Person.call(foo,'foo',30,'man');
```

이런 apply()나 call()메소드는 this를 원하는 값으로 명시적으로 매핑해서 특정함수나 메서드를 호출할 수 있다는 장점이 있다.

> 이들의 대표적인 용도가 바로 arguments객체에서 설명한 arguments 객체와 같은 유사배열객체에서 배열 메서드를 사용하는 경우다

자세한 사항은 p.117 ~ 118 참조



#### 4.4.3 함수 리턴
- 자바스크립트 함수는 항상 리턴값을 반환한다.
- 특히 return 문을 사용하지 않았더라도 다음의 규칙으로 항상 리턴값을 전달하게 된다.

규칙1) 일반 함수나 메소드는 리턴값을 지정하지 않을 경우, undefined 값이 리턴된다.

규칙2) 생성자 함수에서 리턴값을 지정하지 않을 경우 생성된 객체가 리턴된다.



### 4.5 프로토타입 체이닝

#### 4.5.1 프로토타입의 두 가지 의미

- 자바스크립트는 자바같은 객체지향 프로그래밍 언어와는 다른 ++**프로토타입 기반의 객체지향 프로그래밍**++을 지원한다.
- 자바스크립트의 동작 과정을 제대로 이해하려면 프로토타입의 개념도 잘 이해하고 있어야 한다.

**프로토타입**과 **프로토타입 체이닝**의 기본개념을 알아보자

- 자바스크립트에는 `Java`와 같은 `클래스`개념이 없다.
- 대신에 객체리터럴이나 생성자함수로 객체를 생성한다.
- 이렇게 생성된 객체의 부모객체가 바로 '프로토타입'객체다.

> 함수객체의 `prototype 프로퍼티`와 객체의 숨은 프로퍼티인 `[[Prototype]]`을 구분하는것이 초급 개발자와 고급 개발자의 차이라할만큼 중요하다

이 둘의 차이점을 알려면 **자바스크립트의 객체 생성 규칙**을 알아야 한다.

> 자바스크립트에서 모든 객체는 자신을 생성한 생성자 함수의 prototype 프로퍼티가 가리키는 **프로토타입 객체**를 자신의 부모객체로 설정하는 [[Prototype]]링크로 연결한다.

```javascript
// Person 생성자 함수
function Person(name) {
    this.name = name;
}

// foo 객체 생성
var foo = new Person('foo');

console.dir(Person);
console.dir(foo);
```


#### 4.5.2 객체 리터럴 방식으로 생성된 객체의 프로토타입 체이닝

- 자바스크립트에서 객체는 자기 자신의 프로퍼티뿐만이 아니라, 자신의 부모 역할을 하는 프로토타입 객체의 프로퍼티 또한 마치 자신의 것처럼 접근하는게 가능하다.

> 이것을 가능하게 하는게 바로 `프로토타입 체이닝`이다

```javascript
var myObject = {
    name: 'foo',
    sayName: function () {
        console.log('My Name is ' + this.name);
    }
};

myObject.sayName();
console.log(myObject.hasOwnProperty('name'));
console.log(myObject.hasOwnProperty('nickName'));
myObject.sayNickName();
```

> sayNickName() 메소드는 myObject 객체의 메소드가 아니므로 당연히 에러가 나지만
> hasOwnProperty()메소드는 에러가 안난다?

이를 이해하려면 객체 리터럴 방식으로 생성한 객체와 프로토타입 체이닝의 개념을 살펴봐야 한다.

> 우선 객체리터럴로 생성한 객체는 `Object()`라는 내장 생성자 함수로 생성된 것이다.
> Object()생성자 함수도 함수 객체이므로 prototype이라는 프로퍼티 속성이 있다.

> 따라서 앞서 설명한 자바스크립트의 규칙으로 생성한 객체 리터럴 형태의 `myObject`는 `Object()`함수의 prototype 프로퍼티가 가리키는 Object.prototype 객체를 자신의 프로토타입 객체로 연결한다.

프로토타입 체이닝의 개념은
자바스크립트에서 특정객체의 프로퍼티나 메소드에 접근하려고 할 때, 해당 객체에 접근하려는 프로퍼티 또는 메소드가 없다면!!!

> `[[Prototype]]링크`를 따라 자신의 부모 역할을 하는 프로토타입 객체의 프로퍼티를 차례대로 검색하는 것을 프로토타입 체이닝이라고 말한다.

위의 예제를 치면 `hasOwnProperty()`는  `myObejct`객체에 없으므로 [[Prototype]]링크를 따라 그것의 부모 역할을 하는
Object.prototype 프로토타입 객체 내에 `hasOwnProperty()` 메소드가 있는지 검색하는 것이다.
이 메소드는 자바스크립트 표준 API로 Object.prototype 객체에 포함되어 있으므로 에러가 나지 않았던 것이다.



##### 4.5.3 생성자 함수로 생성된 객체의 프로토타입 체이닝

생성자 함수로 객체를 생성하는 경우는 객체리터럴 방식과 얀간 다른 프로토타입 체이닝이 이뤄진다.

다만 다음의 그 기본원칙은 잘 지키고 있다

> 자바스크립트에서 모든 객체는 자신을 생성한 생성자 함수의 prototype 프로퍼티가 가리키는 객체를 자신의 프로토타입 객체(부모객체)로 취급한다.

```javascript
// Person 생성자 함수
function Person(name, age, hobby) {
    this.name = name;
    this.age = age;
    this.hobby = hobby;
}

// foo 객체 생성
var foo = new Person('foo', 30, 'tennis');

// 프로토타입 체이닝
console.log(foo.hasOwnProperty('name')); // true

// Person.prototype 객체 출력
console.dir(Person.prototype);
```


#### 4.5.4 프로토타입 체이닝의 종점

자바스크립트에서 Object.prototype 객체는 **프로토타입 체이닝**의 종점이다.

이것을 달리 해석하면, 객체리터럴방식이나 생성자함수 방식에 상관없이 모든 자바스크립트 객체는 프로토타입 체이닝으로
Object.prototype 객체가 가진 프로퍼티와 메서드에 접근하고, 서로 공유가 가능하다는 것을 알 수 있다.

> 때문에 자바스크립트 표준 빌트인 객체인 `Object.prototype`에는 `hasOwnProperty()`나 `isPrototypeOf()`등과 같이
> 모든 객체가 호출 가능한 표준 메서드들이 정의되어 있다.

#### 4.5.5 기본 데이터 타입 확장

- `Object.prototype`에 정의된 메소드들은 자바스크립트의 모든 객체의 표준 메소드라고 볼 수 있다.

이와 같은 방식으로 자바스크립트의 숫자, 문자, 배열 등에서 사용되는 표준 메소드들의 경우, 이들의 프로토타입인
`Number.prototype`, `String.prototype`, `Array.prototype`등에 정의되어 있다.

자바스크립트는 Object.prototype, String.prototype 등과 같이 표준 빌트인 프로토타입 객체에도 사용자가 직접 정의한
메소드들을 추가하는 것을 허용한다.

가령 아래 예제 처럼 String.prototype 객체에 testMethod() 메소드를 추가하면 이 메소드는 일반 문자열 표준 메소드처럼,
모든 문자열에서 접근 가능하다


```javascript
String.prototype.testMethod = function () {
    console.log('This is the String.prototype.testMethod()');
};

var str = "this is test";
str.testMethod();

console.dir(String.prototype);
```



#### 4.5.6 프로토타입도 자바스크립트 객체다

함수가 생성될 때, 자신의 prototype 프로퍼티에 연결되는 프로토타입 객체는 디폴트로 constructor 프로퍼티만을 가진 객체다.
당연히 프로토타입 객체 역시 자바스크립트 객체 이므로 일반 객체처럼 동적으로 프로퍼티를 추가/삭제하는 것이 가능하다.

```javascript
// Person() 생성자 함수
function Person(name) {
    this.name = name;
}

// foo 객체 생성
var foo = new Person('foo');

/foo.sayHello();

// 프로토타입 객체에 sayHello() 메서드 정의
Person.prototype.sayHello = function () {
    console.log('Hello');
}

foo.sayHello(); // Hello
```


#### 4.5.7 프로토타입 메소드와 this 바인딩

프로토타입 객체는 메소드를 가질 수 있다.
만약 프로토타입 메소드 내부에서 this를 사용한다면 이는 어디에 바인딩 될까?

> 4.4.2.1 객체의 메소드를 호출할 때 this 바인딩에서 설명한 this 바인딩 규칙을 그대로 적용하면 된다.
> 결국, 메소드 호출 패턴에서의 this는 그 메소드를 호출한 객체에 바인딩된다는 것을 기억하다.

```javascript
function Person(name){
	this.name=name;
}

Person.prototype.getName=function(){
	return this.name;
}

var foo = new Person('foo');

console.log(foo.getName());

Person.prototype.name='person';

console.log(Person.prototype.getName());
```


#### 4.5.6 디폴트 프로토 타입은 다른 객체로 변경이 가능하다

디폴트 프로토타입 객체는 함수가 생성될 때 같이 생성되며, 함수의 prototype 프로퍼티에 연결된다.
자바스크립트에서는 이렇게 함수를 생성할 때 해당 함수와 연결되는 디폴트 포로토타입 객체르르 다른 일반 객체로 변경하는 것이 가능하다.

이러한 특징을 이용해서 객체지향의 상속을 구현한다.

> 여기서 주의할 점!
생성자 함수의 프로토타입 객체가 변경되면, 변경된 시점 이후에 생성된 객체들은 변경된 프로토타입 객체로 [[Prototype]]링크를
연결한다는 것이다.

이에 반해 생성자 함수의 프로토타입이 변경되기 이전에 생성된 객체들은 기존 프로토타입 객체로의 [[Prototype]] 링크를 그대로 유지한다.


```javascript
// Person() 생성자 함수
function Person(name) {
    this.name = name;
}
console.log(Person.prototype.constructor);

// foo 객체 생성
var foo = new Person('foo');
console.log(foo.country);

// 디폴트 프로토타입 객체 변경
Person.prototype = {
    country: 'korea'
};
console.log(Person.prototype.constructor);

// bar 객체 생성
var bar = new Person('bar');
console.log(foo.country);
console.log(bar.country);
console.log(foo.constructor);
console.log(bar.constructor);


// 출력 결과
/*
function Person(name) {
    this.name = name;
}
undefined
function Object() { [native code] }
undefined
korea
function Person(name) {
    this.name = name;
}
function Object() { [native code] }
*/
```

#### 4.5.9 객체의 프로퍼티 읽기나 메소드를 실행할 때만 프로토타입 체이닝이 동작한다.

객체의 특정 프로퍼티를 읽으려고 할때, 프로퍼티가 해당 객체에 없는 경우 프로토타입 체이닝이 발생한다.
반대로 객체에 있는 특정 프로퍼티에 값을 쓰려고 한다면 이때는 프로토타입 체이닝이 일어나지 않는다.





