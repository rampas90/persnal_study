## 3. 자바스크립트 데이터 타입과 연산자

#### 자바스크립트의 데이터 타입은 다음과 같이 나뉜다

- 기본타입
	1) 숫자
	2) 문자열
	3) 불린값
	4) undefined
	5) null

- 참조타입
	1) 객체
	- 배열
	- 함수
	- 정규표현식

```javascript
// 숫자 타입
var intNum = 10;
var floatNum = 0.1;


// 문자열 타입
var singleQuoteStr = 'single quote strign';
var doubleQuoteStr = "double quote string";
var singleChar = 'a';


// 불린 타입
var boolVar = true;


// undefined 타입
var emptyVar;


// null 타입
var nullVar = null;

console.log(
	typeof inNum,
	typeof floatNum,
	typeof singleQuoteStr,
	typeof doubleQuoteStr,
	typeof boolVar,
	typeof nullVar,
	typeof emptyVar
);
```

> 결과값

`number number string string boolean obejct nullVar emptyVar`


1. 숫자

- 하나의 숫자형만 존재한다. 
- 모든 숫자를 64비트 부동 소수점 형태로 저장하기 때문이다.
- 정수형이 따로 없고, 모든 숫자를 실수로 처리하므로 나눗셈 연산을 할 때는 주의해야 한다.

> 예) 다음 계산을 C언어에서 할경우 5/2 는 소수부분을 버린 2가 출력된다 반면 `js`에서는 2.5가 출력

```javascript
var num = 5/2;

console.log(num);			   //출력값 2.5
console.log(Math.floor(num));  //출력값 2
```

2. 문자열
- 문자열은 작은따옴표나 큰 따옴표로 생성한다.
- `java`의 char 타입과 같이 문자 하나만을 별도로 나타내는 데이터 타입은 존재하지 않는다.
- 문자 배열처럼 인덱스를 이용해서 접근할 수 있다. 
- 한번생성된 문자열은 읽기만 가능하지 수정은 불가능하다.

3. null과 undefined
- 두 타입 모두 '값이 비어있음'을 나타낸다.
- 자바스크립트 환경 내에서 기본적으로 값이 할당되지 않는 변수는 `undefined`타입이며, 변수자체의 값 또한 `undefined`이다.

> **undefined는 타입이자, 값을 나타낸다**

- null 타입 변수의 경우는 개발자가 명시적으로 값이 비어있음을 나타내는 데 사용한다.
- null타입 변수인지를 확인할 때 typeof 연산자를 사용하면 안되고, 일치연산자(==)를 사용해서 변수의 값을 직접 확인해야 한다.


---

##### 자바스크립트 참조 타입(객체 타입)

- 기본타입을 제외한 모든 값은 객체다. 따라서 배열, 함수, 정규표현식 등도 모두 결국 자바스크립트 객체로 표현된다.

- 객체는 단순히 '이름(key):값(value)' 형태의 프로퍼티들을 저장하는 컨테이너
- 컴퓨터 과학 분야에서 `해시`라는 자료구조와 상당히 유사하다.
- 기본타입은 하나의 값만을 가지는 데 비해, 참조타입인 객체는 여러 개의 프로퍼티들을 포함할 수 있으며, 이러한 객체의 프로퍼티는 기본 타입의 값을 포함하거나, 다른 객체를 가리 킬 수도 있다
- 객체의 프로퍼티는 기본 타입의 값을 포함하거나, 다른 객체를 가리킬 수도 있다.
- 객체의 프로퍼티는  함수로 포함할 수 있으며, 자바스크립트에서는 이러한 프로퍼티를 **메서드**라고 부른다.


##### 객체생성
- 자바는 클래스르를 정의하고, 클래스의 인스턴스를 생성하는 과정에서 객체가 만들어지지만, 자바스크립트에서는 클래스라는 개념이 없고, 객체 리터럴이나 생성자 함수 등 별도의 생성방식이 존재한다.

- 객체를 생성하는 방법은 크게 세가지가 있다.
	1. 기본제공 Object()객체 생성자 함수를 이용하는 방법
	2. 객체 리터럴을 이용하는 방법
	3. 생성자 함수를 이용하는 방법

1)  Object() 생성자 함수 이용방법

```javascript
// Object()를 이용해서 foo 빈 객체 생성
var foo = new Object();

// foo 객체 프로퍼티 생성
foo.name = 'foo';
foo.age = 30;
foo.gender = 'male';

console.log(typeof foo);	//출력값 object
console.log(foo);			   //출력값 { name:'foo', age:30, gender:'male'}
```

2) 객체 리터럴 방식 이용
- 객체를 생성하는 표기법을 의미한다.
- 중괄호{}를 이용해서 객체를 생성한다. {}안에 아무것도 적지 않은 경우는 빈객체가 생성
- 중괄호{}안에  "프로퍼티 이름:프로퍼티값" 형태로 표기하면, 해당 프로퍼티가 추가된 객체를 생성
- 프로퍼티 이름은 문자열이나 숫자가 올 수 있고 프로퍼티값은 어떤 표현식이든 가능하다.
- 이 값이 함수일 경우 이러한 프로퍼티를 메서드라고 부른다.

```javascript
//객체 리터럴 방식으로 foo 객체 생성
var foo = {
	name:'foo',
	age:30,
	gender:'male'
};

console.log(typeof foo);	//출력값 object
console.log(foo);			   //출력값 { name:'foo', age:30, gender:'male'}
```

3) 생성자 함수 이용
- `js`에서는 함수를 통해서도 객체를 생성할 수 있다.
- 이 내용은 4장 참조(다음장)

---

##### 객체 프로퍼티 읽기/쓰기/갱신
- 객체는 새로운 값을 가진 프로퍼티를 생성하고, 생성된 프로퍼티에 접근해서 해당 값을 읽거나 또는 원하는 값으로 프로퍼티의 값을 갱신할 수 있다.

객체의 프로퍼티에 접근하는 방법은 다음과 같이 두가지이다.
	- 대괄호[] 표기법
	- 마침표 . 표기법

```javascript
// 객체 리터럴 방식을 통한 foo 객체 생성
var foo = {
    name : 'foo',
    major : 'computer science'
};

// 객체 프로퍼티 읽기
console.log(foo.name); // foo
console.log(foo['name']); // foo
console.log(foo.nickname); // undefined

// 객체 프로퍼티 갱신
foo.major = 'electronics engineering';
console.log(foo.major); //electronics engineering
console.log(foo['major']); //electronics engineering

// 객체 프로퍼티 동적 생성
/*
	객체의 프로퍼티에 값을 할당할 때, 프로퍼티가 이미 있을경우는 값이 갱신되고, 없을경우는 프로퍼티가 동적으로 생성된 후 값이 할당된다.
*/
foo.age = 30;
console.log(foo.age); // 30

// 대괄호 표기법만을 사용해야 할 경우
/*
	접근하려는 프로퍼티가 표현식이거나 예약어일 경우 대괄호를 이용해서 접근해야 한다.
    아래 full-name에서 "-"는 연산자이므로 
*/
foo['full-name'] = 'foo bar';
console.log(foo['full-name']); // foo bar
console.log(foo.full-name); // NaN
console.log(foo.full); // undefined
console.log(name); // undefined
```

> Nan(Not a Number)값
> 수치 연산을 해서 정상적인 값을 얻지 못할 때 출력되는 값
> 가령 `1-'hello'` 라는 연산의 결과는 NaN이다 



##### for in 문과 객체 프로퍼티 출력

> `for in` 문을 사용하면 객체에 포함된 모든 프로퍼티에 대해 루프를 수행할 수 있다

```javascript
// 객체 리터럴을 통한 foo 객체 생성
var foo = {
    name: 'foo',
    age: 30,
    major: 'computer science'
};

// for in문을 이용한 객체 프로퍼티 출력
var prop;
for (prop in foo) {
    console.log(prop, foo[prop]);
}
```

---
##### 객체 프로퍼티 삭제
> 객체의 프로퍼티를 delete 연산자를 이용해 즉시 삭제할 수 있다.
> 주의점은 객체의 프로퍼티를 삭제할 뿐, 객체 자체를 삭제하지는 못한다는 점이다.



##### 참조타입의 특성
- 기본타입을 제외한 모든값은 객체로, 배열이나 함수 또한 객체로 취급된다. 또한 이런 타입을 참조타입이라 부른다 (java와 크게 다르지 않은 형태)
- 이것은 객체의 모든 연산이 실제 값이 아닌 참조값으로 처리되기 때문이다.


```javasciprt
// 객체를 리터럴 방식으로 생성
//여기서 objA 변수는 객체 자체를 저장하고 있는 것이 아니라 생성된 객체를 가리키는 참조값을 저장하고 있다.
var objA = {
    val : 40
};
// objB에도 objA와 같은 객체의 참조값이 저장된다.
var objB = objA;

console.log(objA.val);  // 40
console.log(objB.val);  // 40

objB.val = 50;
console.log(objA.val);  // 50
console.log(objB.val);  
```
> objA 객체는 참조 변수 objA가 가리키고 있는 객체


동등연산자(==)를 사용하여 두 객체를 비교할 때도 객체의 프로퍼티값이 아닌 참조값을 비교한다는것에 주의

```javascript
var a = 100;
var b = 100;

var objA = { value: 100 };
var objB = { value: 100 };
var objC = objB;

console.log(a == b);  // true
console.log(objA == objB); // false
console.log(objB == objC); // true
```
> objA와 objB는 같은형태의 프로퍼티값을 가지고 있지만 동등연산자 로 출력해보면 false가 출력된다
> 이는 기본타입의 경우는 값 자체를 비교해서 일치 여부를 판단하지만, 객체와 같은 참조 타입의 경우는
> 참조값이 같아야 true가 되기 때문이다.

##### 참조에 의한 함수 호출 방식

> 기본 타입과 참조 타입의 경우는 함수 호출 방식도 다르다. 
> 기본 타입의 경우는 값에 의한 호출(call byy value)방식으로 동작한다.
> 즉, 함수를 호출할 때 인자로 기본 타입의 값을 넘길 경우, 호출된 함수의 매개변수로 **복사된 값**이 전달된다.
> 때문에 함수내부에서 매개변수를 이용해 값을 변경해도, 실제로 호출된 변수의 값이 변경되지는 않는다

이에반해
> 참조 타입의 경우 함수를 호출할 때 참조에 의한 호출(call by reference)방식으로 동작한다.
> 즉, 함수 호출시 객체를 전달할 경우, 객체의 프로퍼티값이 함수의 매개변수로 복사되지 않고, 인자로 넘긴 객체의 참조값이 그대로 함수 내부로 전달된다.
> 때문에 함수 내부에서 참조값을 이용해서 인자로 넘긴 실제 객체의 값을 변경할 수 있는것이다.

```javascript
var a = 100;
var objA = { value: 100 };

function changeArg(num, obj) {
    num = 200;
    obj.value = 200;

    console.log(num);
    console.log(obj);
}

changeArg(a, objA);

console.log(a);
console.log(objA);
```
	결과값
	> 200
	> {value:200}
	> 100
	> {value:200}


##### 프로토타입
> 자바크스립트의 **모든 객체는 자신의 부모 역할을 하는 객체와 연결되어 있다** 이것은 마치 객체지향의 상속 개념과 같이 부모 객체의 프로퍼티를 마치 자신의 것처럼 쓸 수 있는 것 같은 특징이 있다.
> 자바스크립트에서는 이러한 부모 객체를 **프로토타입 객체(짧게는 프로토타입)** 이라 부른다


```javascript
var foo = {
    name: 'foo',
    age: 30
};

console.log(foo.toString());

console.dir(foo);
```
> 모든 객체의 프로토타입은 자바스크립트의 룰에 따라 객체를 생성할 때 결정된다. (자세한 내용은 4.5장참조)
> 객체 리터럴 방식으로 생성된 객체의 경우 `Object.prototype`객체가 프로토 타입 객체가 된다는 것만 기억하자
> 객체를 생성할 때 결정된 프로토타입 객체는 임의의 다른 객체로 변경하는 것도 가능하다.
> 즉, 부모 객체를 동적으로 바꿀 수도 있는 것이다. 
> 자바스크립트에서는 이러한 특징을 활용해서 객체 상속 등의 기능을 구현한다.(자세한 사항은 6장 참조)

##### 배열
- 배열은 자바스크립트 객체의 특별한 형태다.
- 즉, C나 자바의 배열과 같은 기능을 하는 객체지만, 이들과는 다르게 굳이 크기를 지정하지 않아도 되며,
- 어떤 위치에 어느 타입의 데이터를 저장하더라도 에러가 발생하지 않는다.

#####배열리터럴
> **배열리터럴**은 자바스크립트에서 새로운 배열을 만드는 데 사용하는 표기법이다.
> 객체 리터럴이 {} 중괄호 표기법이었다면, 배열 리터럴은 []대괄호를 사용한다.

```javascript
// 배열 리터럴을 통한 5개 원소를 가진 배열 생성
var colorArr = ['orange', 'yellow', 'blue', 'green', 'red'];
console.log(colorArr[0]); // orange
console.log(colorArr[1]); // yellow
console.log(colorArr[4]); // red
```
> 당연히 위와같이 인덱스로 접근가능하다~

##### 배열의 요소생성
- 객체가 동적으로 프로퍼티를 추가할 수 있듯이, 배열도 동적으로 배열 원소를 추가할 수 있디.
- 값을 순차적으로 넣을 필요 없이 아무 인덱스 위치에나 동적으로 추가할 수 있다.

```javascript
// 빈 배열
var emptyArr = [];
console.log(emptyArr[0]); // undefined

// 배열 요소 동적 생성
emptyArr[0] = 100;
emptyArr[3] = 'eight'
emptyArr[7] = true;
console.log(emptyArr); // [100, undefined × 2, "eight", undefined × 3, true]
console.log(emptyArr.length); // 8
```
> 출력값을 확인해보면 배열의 크기를 현재 인덱스중 가장 큰 값을 기준으로 정함을 알 수 있다.
> 또한 값이 할당되지 않는 인덱스요소는 `undefined`값을 기본으로 가진다.

##### 배열의 length 프로퍼티
> `length`프로퍼티는 배열의 원소 개수를 나타내지만, 실제로 배열에 존재하는 원소 개수와 일치하는 것은 아니다.
> 하지만 실제 메모리는 length 크기처럼 할당되지는 않는다 `ex) arr[100]` 에 값이 3개밖에 없을경우
> `undefined` 부분은 실제로 메모리가 할당되지 않는다는 말이다.


```javascript
var arr = [0, 1, 2];
console.log(arr.length); // 3

arr.length = 5;
console.log(arr); // [0, 1, 2]

arr.length = 2;
console.log(arr); // [0, 1]
console.log(arr[2]); // undefined
```

> 위소스를 보면 명시적으로 배열의 `length`값을 변경 가능하고 또한 생성된 인덱스보다 작을경우 그에 해당하는 값이 삭제되는걸 알 수 있다.


##### 배열 표준 메서드와 length 프로퍼티
- `js`는 배열에서 사용 가능한 다양한 표준 메서드를 제공한다.
- 이러한 배열 메소드는 `length 프로퍼티`를 기반으로 동작하고 있다.
- 예를 들면 `push()`메소드 (인수로 넘어온 항목을 배열의 끝에 추가하는 표준 배열 메소드다.)

##### 배열과 객체
> `js`에서는 배열 역시 객체다. 하지만 배열은 일반객체와는 약간 차이가 있다.

- 객체는 length 프로퍼티가 존재하지 않는다.
- 객체는 배열이 아니므로 `push()`와 같은 **표준 배열 메소드**를 사용할 수 없다
- 객체 리터럴 방식으로 생성한 객체의 경우 `Object.prototype` 객체가 프로토타입이고
- 배열의 경우 `Array.prototype` 객체가 부모객체인 프로토타입이 된다.

> 배열과객체의 프로토타입 순서도는 아래와 같다

##### Obejct.prototype  <- Array.prototype <- 배열

> 즉 배열에서는 Object.prototype의 표준메소드들도 사용할 수 있다는 뜻

```javascript
var emptyArray = []; // 배열 리터럴을 통한 빈 배열 생성
var emptyObj = {}; // 객체 리터럴을 통한 빈 객체 생성

console.dir(emptyArray.__proto__); // 배열의 프로토타입(Array.prototype) 출력
console.dir(emptyObj.__proto__); // 객체의 프로토타입(Object.prototype) 출력
```
> 크롬브라우져 개발자도구로 확인해보자

```javascript
// 배열 생성
var arr = ['zero', 'one', 'two'];
console.log(arr.length); // 3

// 프로퍼티 동적 추가
arr.color = 'blue';
arr.name = 'number_array';
console.log(arr.length); // 3

// 배열 원소 추가
arr[3] = 'red';
console.log(arr.length); // 4

// 배열 객체 출력
console.dir(arr);
```

> 배열의 length 프로퍼티는 배열 원소의 가장 큰 인덱스가 변했을 경우만 변경된다.
> 아래는 위 코드의 출력 결과다.
> 보기와 같이 배열도 객체처럼 "key:value" 형태가 가능함을 알수있고 `length` 로  알수 있는 값은 인덱스값만을 뽑아낼수 있다는걸 알 수 있다.

```javascript
0: "zero"
1: "one"
2: "two"
3: "red"
color: "blue"
length: 4
name: "number_array"
```

##### 배열의 프로퍼티 열거

- 배열역시 객체 처럼 `for in`문으로 프로퍼티를 열거할 수 있지만 불필요한 프로퍼티가 출력될 수 있으므로 되도록  `for` 문을 사용하는 것이 좋다.

```javascript

// 배열 생성
var arr = ['zero', 'one', 'two'];

// 프로퍼티 동적 추가
arr.color = 'blue';
arr.name = 'number_array';

// 배열 원소 추가
arr[3] = 'red';

for (var prop in arr) {
    console.log(prop, arr[prop]);
}

for (var i=0; i<arr.length; i++) {
    console.log(i, arr[i]);
}
```

> 아래는 결과 값이다. 보기와 같이 `for in`문으로 돌릴경우 color와 name 프로퍼티까지 출력된걸 알 수 있다.

```
0 zero
1 one
2 two
3 red
color blue
name number_array
----------------------
0 "zero"
1 "one"
2 "two"
3 "red"
```

##### 배열요소삭제
- 배열도 객체이므로, 배열오소나 프로퍼티를 삭제하는 데 delecte 연산자를 사용할 수 있다.

```javascript
var arr = ['zero', 'one', 'two', 'three'];
delete arr[2];
console.log(arr); // ["zero", "one", undefined × 1 , "three"]
console.log(arr.length); // 4
```

> 주의할점은 위와 같이 arr[2] 배열의 요소를 삭제할 경우 undefined가 할당되지만 lengh값은 변하지 않는 것을 확인할 수 있다.
> 즉, delete 연산자는 해당 요소의 값을 undefined로 설정할  뿐 원소 자체를 삭제하지는 않는다.
> 때문에 보통 배열에서 요소들을 완전히 삭제할 경우 자바스크립트에서는 `splice()`배열 메소드를 사용한다.

```javascript
splice(start,deleteCount,item...)
```
	- start - 배열에서 시작 위치
	- deleteCount - start에서 지정한 시작 위치에서 삭제할 요소의 수
	- item - 삭제할 위치에 추가할 요소

> 아래는 사용예제다


```javascript
var arr = ['zero', 'one', 'two', 'three'];

arr.splice(2, 1);
console.log(arr); // ["zero", "one", "three"]
console.log(arr.length); // 3
```


##### Array()생성자 함수

- 배열은 일반적으로 배열 리터럴로 생성하지만, 배열 리터럴도 결국 자바스크립트 기본 제공 `Array() 생성자함수`로 배열을 생성하는 과정을 단순화시킨 것이다.
- 생성자 함수로 배열과 같은 객체를 생성할 때는 반드시 new 연산자를 같이 써야 한다는 것을 기억하자.

> Array() 생성자 함수 호출시 인자 개수에 따라 동작이 다르므로 아래 코드를 참고하자


```javascript
var foo = new Array(3);
console.log(foo); // [undefined, undefined, undefined]
console.log(foo.length); // 3

var bar = new Array(1, 2, 3);
console.log(bar); // [1, 2, 3]
console.log(bar.length); // 3
```


##### 유사 배열 객체
- `length`프로퍼티를 가진 객체를 `유사 배열 객체(array-like object)`라고 부른다.
- 아래 예제에서 객체 obj는 length 프로퍼티가 있으므로 유사 배열 객체다.
- 유사배열 객체의 가장 큰 특징은 객체임에도 불구하고, 자바스크립트의 표준 배열 메소드를 사용하는 게 가능하다는 것이다.

```javascript
var arr = ['bar'];
var obj = {
    name : 'foo',
    length : 1
};

arr.push('baz');
console.log(arr);  // ['bar', 'baz']

obj.push('baz');  // Uncaught TypeError: Object #<Object> has no method 'push'
```
> 위 코드를 살펴보면, 배열 arr은 push()표준 배열 메소드를 활용해서 원소를 추가하는 것이 가능한 반면에,
> 유사 배열 객체 obj는 당연히 배열이 아니므로 바로 push()메소드를 호출할 경우 에러가 발생한다.

하지만 유사배열객체의 경우 추후 배울 `apply()`메소드를 사용하면 객체지만 표준 배열 베소드를 활용하는 것이 가능하다.
이 부분에 대한 더욱 자세한 내용은 **4.4.2.4 call과 apply메소드를 이용한 명시적인 this바인딩**에서 살펴볼 것이니
지금은 '유사 배열 객체도 배열 메소드를 사용하는 것이 가능하다'정도만 숙지하도록 하자.

> 참고로 `arguments 객체`나 `jQuery 객체`가 바로 유사 배열 객체 형태로 되어있다

```javascript
var arr = ['bar'];
var obj = { name : 'foo', length : 1};

arr.push('baz');
console.log(arr); // [‘bar’, ‘baz’]

Array.prototype.push.apply(obj, ['baz']);
console.log(obj); // { '1': 'baz', name: 'foo', length: 2 }
```


#### 연산자

- `+`연산자는 두 연산자가 모두 숫자일 경우에만 더하기 연산이 수행되고, 나머지는 문자열 연결 연산이 이뤄진다.

##### typeonf 연산자
- `typeof` 연산자는 피연산자의 타입을 문자열 형태로 리턴한다. 
- 여기서 `null` 과 배열이 `object`라는점. 함수는 `function`이라는 점에 유의해야 한다.

##### 동등(==)연산자와 일치(===)연산자
- 자바스크립트에서는 두 값이 동일한지를 확인하는 데, 두 연산자를 모두 사용할 수 있다.
- 두 연산자의 차이는 `==`연산자는 비교하려는 피연산자의 타입이 다를 경우에 타입 변환을 거친 다음 비교한다.
- `===`연산자는 피연산자의 타입이 다를 경우에 타입을 변경하지 않고 비교한다.

```javascript
console.log(1 == '1'); // true
console.log(1 === '1'); // false
```


##### !! 연산자

- `!!`의 역할은 피연산자를 불린 값으로 변화하는 것이다.

```javascript
console.log(!!0); // false
console.log(!!1); // true
console.log(!!'string'); // true
console.log(!!'');
console.log(!!true);
console.log(!!false);
console.log(!!null);
console.log(!!undefined); // false
console.log(!!{}); // true
console.log(!![1,2,3]); // true
```



