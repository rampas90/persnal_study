## 7. 함수형 프로그래밍

- 함수형 프로그래밍은 프로그래밍의 여러 가지 패러다임 중 하나이다.
- 함수형 프로그래밍은 오랫동안 학문적으로 연구되었고
- 함수형 프로그래밍 언어 역시 역사가 깊다.
- 자바스크립트에서 사용되는 함수형 프로그래밍 방식의 여러가지 기본적인 함수를 알아보자


### 7.1 함수형 프로그래밍의 개념

함수형프로그래밍은 함수의 조합으로 작업을 수행함을 의미한다.
중요한 것은 이 작업이 이루어지는 동안 작업에 필요한 데이터와 상태는 변하지 않는다는 점이다.
변할 수 있는 건 오로지 함수뿐이다.

이 함수가 바로 연산의 대상이 된다. (기존 프로그래밍 방식과 다른만큼 이해하기 어려울수도 있다)

우선 쉬운 예를 들어보자. 이 예는 **함수형 프로그래밍을 표현하는 슈도코드**다.

> 아래처럼 특정 문자열을 암호화하는 함수가 여러 개 있다고 하자.

```javascript
f1 = encrypt1;
f2 = encrypt2;
f3 = encrypt3;
```
> 여기서 f1,f2,f3은 입력값이 정해지지 않고, 서로 다른 암호화 알고리즘만 있다.

```javascript
pure_value = 'zzoon';
encrypted_value = get_encrypted(x);
```

pure_value는 암호화할 문자열이고, encrypted_value는 암호화된 문자열이다.
get_encrypted()는 암호화 함수를 받아서 입력받은 함수로 pure_value를 암호화한 후 반환한다.
즉 다음과 같이 처리할 수 있다.

```javascript
encrypted_value = get_encrypted(f1);
encrypted_value = get_encrypted(f2);
encrypted_value = get_encrypted(f3);
```

여기서 pure_value는 작업에 필요한 데이터고 작업이 수행되는 동안 변하지 않는다.
get_encrypted()가 작업하는 동안 변할 수 있는것은 오로지 입력으로 들어오는 함수뿐이다.

f1,f2,f3은 외부(예제에서는 zzoon이라는 변수)에 아무런 영향을 미치지 않는 함수라고 할 수 있다.
이를 **순수 함수**라고 한다.

get_encrypted함수는 인자로서 f1,f2,f3함수를 받는다.
그리고 이 예에서는 결과값이 encrypted_value라는 값이지만 결과값을 또 다른 형태의 함수로서 반환할 수도 있다.
이렇게 함수를 또 하나의 값으로 간주하여 함수의 인자 혹은 반환값으로 사용할 수 있는 함수를 **고계함수**라고 한다.

즉 이 예제에서 프로그래머는 입력으로 넣을 암호화 함수를 새롭게 만드는 방식으로 암호화 방법을 개선할 수 있다.
이와 같이 내부 데이터 및 상태는 그대로 둔 채 제어할 함수를 변경 및 조합함으로써 원하는 결과를 얻어내는 것이 함수형 프로그래밍의 중요한 특성이다.
이 특성은 높은 수준의 모듈화가 가능하다는 점에서 큰 장점이 된다.

앞서 설명한 순수함수의 조건을 충족하는 함수 구현으로 모듈 집약적인 프로그래밍이 가능하다
간단한 모듈의 적절한 재구성과 조합으로 프로그래밍할 수 있다.


---

### 7.2 자바스크립트에서의 함수형 프로그래밍

자바스크립트에서도 함수형 프로그래밍이 가능하다. 그 이유는 자바스크립트가 다음을 지원하기 때문이다.
- 일급 객체로서의 함수
- 클로저

> 이를 쉽게 이해하려면 앞에서 언급한 암호화 예를 자바스크립트로 구현해 보자.

```javascript
var f1 = function(input){
	var result;
	/* 암호화 작업 수행 */
	result = 1;
	return result;
}

var f2 = function(input){
	var result;
	/* 암호화 작업 수행 */
	result = 2;
	return result;
}

var f3 = function(input){
	var result;
	/* 암호화 작업 수행 */
	result = 3;
	return result;
}

var get_encrypted = function(func){
	var str = 'zzoon';

	return function(){
		return func.call(null, str);
	}
}

var encrypted_value = get_encrypted(f1)();
console.log(encrypted_value);

var encrypted_value = get_encrypted(f2)();
console.log(encrypted_value);

var encrypted_value = get_encrypted(f3)();
console.log(encrypted_value);
```

이처럼 자바스크립트에서 앞서 예로 든 함수형 프로그래밍 슈도코드를 구현할 수 있다.
이것이 가능한 이유는 앞서 언급한 대로 함수가 일급 객체로 취급되기 때문이다.
그래서 함수의 인자로 함수를 넘기고, 결과로 함수를 반환할 수도 있다.
게다가 변수 str 값이 영향을 받지 않게 하려고 클로저를 사용했다.

get_encrypted()함수에서 반환하는 익명함수가 클로저다
이 클로저에서 접근하는 변수 str은 외부에서 접근할 수 없으므로 클로저로 함수형 프로그래밍의 개념을 정확히 구현해낼 수 있다.



#### 7.2.1 배열의 각 원소 총합 구하기

배열의 각 원소 합을 구하는 프로그램을 작성해 보자( 시그마 )

```javascript
function sum(arr){
	var len = arr.length;
	var i = 0, sum =0;

	for (; i<len;i++){
		sum += arr[i];
	}
	return sum;
}

var arr = [1, 2, 3, 4, 5];
console.log(sum(arr));
```

> 이번에는 배열의 각원소를 모두 곱한 값을 구해보자


```javascript
function multiply(arr){
	var len = arr.length;
	var i = 0, result =1;

	for (; i<len;i++){
		result *= arr[i];
	}
	return result;
}

var arr = [1, 2, 3, 4];
console.log(multiply(arr));
```

> 위 두개의 에제는 명령형 프로그래밍 방식으로 작성된 코드다
> 문제 하나하나를 각각의 함수를 구현하여 문제를 풀고있다.
> 즉 다른 방식의 연산을 원한다면 새로운 함수를 다시 구현해야 한다.
> 하지만 함수형 프로그래밍을 이용하면 이러한 수고를 덜 수 있다


```javascript
function reduce(func, arr, memo) {
	var len = arr.length,
		i = 0,
		accum = memo;

	for (; i < len; i++) {
		accum = func(accum, arr[i]);
	}

	return accum;
}

var arr = [ 1, 2, 3, 4 ];

var sum = function(x, y) {
	return x+y;
};

var multiply = function(x, y) {
	return x*y;
};

console.log(reduce(sum, arr, 0));
console.log(reduce(multiply, arr, 1));
```


#### 7.2.2 팩토리얼

먼저 명령형 프로그래밍 방식의 소스를 보자

```javascript
function fact(num) {
	var val = 1;
	for (var i = 2; i <= num; i++) 
		val = val * i;
	return val;
}

console.log(fact(100));
```

> 또한 다음과 같이 재귀 호출을 이용할 수도 있다.

```javascript
function fact(num) {
	if (num == 0) return 1;
	else return num* fact(num-1);
}

console.log(fact(100));
```

> 확인해보면 위 두 코드 모두 성공적인 결과가 나온다.
> 하지만 이런 종류의 함수 구현은 항상 성능을 고려하게 된다. 이를 함수형 프로그래밍으로 성능을 고려하여 구현해보자

그전에 먼저 팩토리얼 특성을 살펴보자
처음 10!을 실행한 후 20!을 실행한다고 가정해보자
20!을 실행할 때는 앞에서 실행한 10!을 중복하여 계산한다.
이렇게 중복되는 값, 즉 앞서 연산한 결과를 캐시에 저장하여 사용할 수 있는 함수를 작성한다면 성능향상에 도움이 된다.


```javascript
var fact = function(){
	var cache = {'0' : 1};
	var func = function(n){
		var result = 0;

		if (typeof(cache[n]) === 'number'){
			result = cache[n];
		}
		else{
			result = cache[n] = n * func(n-1);
		}

		return result;
	}
	return func;
}();

console.log(fact(10));
console.log(fact(20));
```

fact는 cache에 접근할 수 있는 클로저를 반환 받는다. 클로저로 숨겨지는 cache에는 팩토리얼을 연산한 값을 저장하고 있다.
연산을 수행하는 과정에서 캐시에 저장된 값이 있으면 곧바로 그 값을 반환하는 방식이다.
이렇게 하면 한번 연산된 값을 캐시에 저장하고 있으므로, 중복된 연산을 피하여 보다 나은 성능의 함수를 구현할 수 있다.


> 메모이제이션 패턴 p.209~212 참조

#### 7.2.3 피보나치 수열

마지막으로 피보나치 수열을 구현해보자 
이 절에서는 명령형 프로그래밍 방식을 생략하고 곧바로 함수형 프로그래밍을 알아보자 (메모이제이션 기법)

```javascript
var fibo = function(){
	var cache = {'0':0,'1':1};
   
   var func = function(n){
   	if(typeof(cache[n]) === 'number'){
      	result = cache[n];
      }
      else{
      	result = cache[n] = func(n-1) + func(n-2);
      }
      return result;
   }
   return func;

}();

console.log(fibo(10));
```


---

### 7.3 자바스크립트에서의 함수형 프로그래밍을 활용한 주요 함수

#### 7.3.1 함수 적용

앞서 배운 `Function.prototype.apply` 함수로 함수 호출을 수행할 수 있음을 배웠다
그런데 왜 이름이 `apply`일까?
함수 적용(Applying function)은 함수형 프로그래밍에서 사용되는 용어다.
함수형 프로그래밍에서는 특정 데이터를 여러 가지 함수를 적용시키는 방식으로 작업을 수행한다.

여기서 함수는 단순히 입력을 넣고 출력을 받는 기능을 수행하는 것뿐만 아니라, 인자 혹은 반환 값으로 전달된 함수를
특정 데이터에 적용시키는 개념으로 이해해야 한다.
그래서 자바스크립트에서도 함수를 호출하는 역할을 하는 메소드를 apply라고 이름 짓게 된 것이다.

> 따라서 `func.apply(Obj.Args)`와 같은 함수 호출을 'func 함수를 Obj 객체와 Args 인자 배열에 적용시킨다'라고 표현할 수 있다.

#### 7.3.2 커링

커링이란 특정 함수에서 인자의 일부를 넣어 고정시키고, 나머지를 인자로 받는 새로운 함수를 만드는 것을 의미한다.

```javascript
function Calculate(a, b, c) {
	return a*b+c;
}

function Curry(func) {
	var args = Array.prototype.slice.call(arguments, 1);

	return function() {
		return func.apply(null, args.concat(Array.prototype.slice.call(arguments)));
	}
}

var new_func1 = Curry(Calculate, 1);
console.log(new_func1(2,3)); // 5
var new_func2 = Curry(Calculate, 1, 3);
console.log(new_func2(3)); // 6
```

`calculate()`함수는 인자 세개를 받아 연산을 수행하고 결과값을 반환한다.
여기서 `curry()`함수로 첫 번째 인자를 1로 고정시킨 새로운 함수 new_func1()과 첫 번째, 두 번째 인자를 1과 3으로 
고정시킨 new_func2()함수를 새롭게 만들 수 있다.

여기서 핵심적인 역할을 하는 curry()함수의 역할은 간단하다.
curry()함수로 넘어온 인자를 args에 담아놓고, 새로운 함수 호출로 넘어온 인자와 합쳐서 함수를 적용한다.

이러한 커링은 함수형 프로그래밍 언어에서 기본적으로 지원하는데, 자바스크립트에서는 기본으로 제공하지는 않는다.
그러나 사용자는 다음과 같이 Function.prototype에 커링 함수를 정의하여 사용할 수있다.

```javascript
Function.prototype.curry = function(){
	var fn = this, args = Array.prototype.slice.call(arguments);
   return function(){
   	return fn.apply(this, args.concat(Array.prototype.slice.call(arguments)));
   };
}
```


#### 7.3.3 bind

`bind`함수는 4장에서 언급됬었다. 이 함수 역시 함수형 프로그래밍 방식을 사용한 대표적인 함수이다.
아래 코드는 bind()함수의 가장 기본적인 기능만을 구현한 코드이다

```javascript
Function.prototype.bind = function (thisArg){
	var fn = this,
   slice = Array.prototype.slice,
   args = slice.call(argments, 1);
   return function(){
   	return fn.apply(thisArg, args.concat(slice.call(arguments)));
   };
}
```

> 이 앞절에서 살펴본 curry() 함수와 유사한 면에서 보듯이 `bind()`함수는 커링 기법을 활용한 함수이다.

커링과 같이 상요자가 고정시키고자 하는 인자를 bind()함수를 호출할 때 인자로 넘겨주고 반환받은 함수를 호출하면서
나머지 가변 인자를 넣어줄 수 있다.

앞에서 소개한 curry()함수와 다른 점은 함수를 호출할 때 this에 바인딩시킬 객체를 사용자가 넣어줄 수 있다는 점이다.
curry() 함수가 자바스크립트 엔진에 내장되지 않은 것도 이 bind()함수로 충분히 커버가 가능하기 때문일 것이다

```javascript
var print_all = function(arg) {
	for (var i in this){
   	console.log(i + " : " + this[i]);
   }
	for (var i in arguments){
   	console.log(i + " : " + arguments[i]);
   }
}

var myobj = { name : "zzoon" };

var myfunc = print_all.bind(myobj);
myfunc(); // name : zzoon

var myfunc1 = print_all.bind(myobj, "iamhjoo", "others");
myfunc1("insidejs");
```

> myfunc()함수는 myobj객체를 this에 바인딩시켜 print_all()함수를 실행하는 새로운 함수이다.
> 한발 더 나아가서 my-func1()을 실행하면 인자도 bind()함수에 모두 넘겨진다.

> 이와 같이 특정 함수에 원하는 객체를 바인딩시켜 새로운 함수를 사용할 때 bind()함수가 사용된다.



#### 7.3.4 래퍼

래퍼(wrapper)란 쉽게 말하면 특정 함수를 자신의 함수로 덮어쓰는 것을 말한다.
객체지향 프로그래밍에서 **다형성**의 특성을 살리려면 **오버라이드**를 지원하는데, 이와 유사한 개념으로 생각하자

```javascript
function wrap(object, method, wrapper){
	var fn = object[method];
	return object[method] = function(){
		//return wrapper.apply(this, [ fn.bind(this) ].concat(
		return wrapper.apply(this, [ fn ].concat(
		Array.prototype.slice.call(arguments)));
	};
}

Function.prototype.original = function(value) {
	this.value = value;
	console.log("value : " + this.value);
}

var mywrap = wrap(Function.prototype, "original", function(orig_func, value) {
	this.value= 20;
	orig_func(value);
	console.log("wrapper value : " + this.value);
});

var obj = new mywrap("zzoon");
```

Function.prototype에 original이라는 함수가 있고, 이는 인자로 넘어온 값을 value에 할당하고 출력하는 기능을 한다.
이를 사용자가 덮어쓰기 위해 wrap 함수를 호출하였다.

세번째 인자로 넘긴 자신의 익명 함수를 `Function.prototype.original`에 덮어쓰려는 것이다.

> 래퍼는 기존에 제공되는 함수에서 사용자가 원한느 로직을 추가하고 싶다거나, 기존에 있는 버그를 피해가고자 할 때 많이 사용된다.
> 특히, 특정 플랫폼에서 버그를 발생시키는 함수가 있을 경우 이를 컨트롤할 수 있으므로 상당히 용이하다

#### 7.3.5 반복함수


##### 7.3.5.1 each

PHP의 foreach 와 유사한 구문이라고 생각하면 된다.
대부분의 자바스크립트 라이브러리에 기본적으로 구현되어 있는 함수이다.
jQuery 1.0의 each()함수를 알아보자

```javascript
function each( obj, fn, args ) {
	if ( obj.length == undefined )
		for ( var i in obj )
			fn.apply( obj[i], args || [i, obj[i]] );
	else
		for ( var i = 0; i < obj.length; i++ )
			fn.apply( obj[i], args || [i, obj[i]] );
	return obj;
};
  
each([1,2,3], function(idx, num) {
	console.log(idx + ": " + num);
});

var zzoon = {
	name : "zzoon",
	age : 30,
	sex : "Male"
};

each(zzoon, function(idx, value) {
	console.log(idx + ": " + value);
});
```


##### 7.3.5.3 reduce

reduce()는 배열의 각 요소를 하나씩 꺼내서 사용자의 함수를 적용시킨 뒤, 그 값을 계속해서 누적시키는 함수다.

```javascript

  Array.prototype.reduce = function(callback) { 
	// this 가 null 인지, 배열인지 체크 
	// callback이 함수인지 체크 
  
	var obj = this;
	var value, accumulated_value = 0;
 
	for ( var i = 0; i < obj.length; i++ ) {
		value = obj[i];
		//console.log("exe");
		accumulated_value = callback.call(null, accumulated_value, value); 
	}
	 
    return accumulated_value;
  };  

var arr = [1,2,3];
var accumulated_val = arr.reduce(function(a, b) {
	return a + b*b;
});

console.log(accumulated_val);
```

배열의 각 요소를 순차적으로 제곱한 값을 더해 누적된 값을 반환받는 예제다.
각 요소를 사용자가 원하는 특정 연산으로 누적된 값을 반환받고자 할 때 유용하게 사용된다.
