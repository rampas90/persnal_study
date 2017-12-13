/* ****************************************** 3-1 예제 ********************************************** */


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



/* ****************************************** 3-5 예제 ********************************************** */



// Object()를 이용해서 foo 빈 객체 생성
var foo = new Object();

// foo 객체 프로퍼티 생성
foo.name = 'foo';
foo.age = 30;
foo.gender = 'male';

console.log(typeof foo);	//출력값 object
console.log(foo);			   //출력값 { name:'foo', age:30, gender:'male'}



/* ****************************************** 3-6 예제 ********************************************** */


//객체 리터럴 방식으로 foo 객체 생성
var foo = {
	name:'foo',
	age:30,
	gender:'male'
};

console.log(typeof foo);	//출력값 object
console.log(foo);			   //출력값 { name:'foo', age:30, gender:'male'}


/* ****************************************** 3-7 예제 ********************************************** */



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
foo.age = 30;
console.log(foo.age); // 30

// 대괄호 표기법만을 사용해야 할 경우
foo['full-name'] = 'foo bar';
console.log(foo['full-name']); // foo bar
console.log(foo.full-name); // NaN
console.log(foo.full); // undefined
console.log(name); // undefined



/* ****************************************** 3-8 예제 ********************************************** */



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



/* ****************************************** 3-9 예제 ********************************************** */

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




/* ****************************************** 3-10 예제 ********************************************** */



var a = 100;
var b = 100;

var objA = { value: 100 };
var objB = { value: 100 };
var objC = objB;

console.log(a == b);  // true
console.log(objA == objB); // false
console.log(objB == objC); // true



/* ****************************************** 3-11 예제 ********************************************** */


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



/* ****************************************** 3-12 예제 ********************************************** */


var foo = {
    name: 'foo',
    age: 30
};

console.log(foo.toString());

console.dir(foo);



/* ****************************************** 3-13 예제 ********************************************** */



// 배열 리터럴을 통한 5개 원소를 가진 배열 생성
var colorArr = ['orange', 'yellow', 'blue', 'green', 'red'];
console.log(colorArr[0]); // orange
console.log(colorArr[1]); // yellow
console.log(colorArr[4]); // red



/* ****************************************** 3-14 예제 ********************************************** */


// 빈 배열
var emptyArr = [];
console.log(emptyArr[0]); // undefined

// 배열 요소 동적 생성
emptyArr[0] = 100;
emptyArr[3] = 'eight'
emptyArr[7] = true;
console.log(emptyArr); // [100, undefined × 2, "eight", undefined × 3, true]
console.log(emptyArr.length); // 8



/* ****************************************** 3-16 예제 ********************************************** */



var arr = [0, 1, 2];
console.log(arr.length); // 3

arr.length = 5;
console.log(arr); // [0, 1, 2]

arr.length = 2;
console.log(arr); // [0, 1]
console.log(arr[2]); // undefined



/* ****************************************** 3-19 예제 ********************************************** */



var emptyArray = []; // 배열 리터럴을 통한 빈 배열 생성
var emptyObj = {}; // 객체 리터럴을 통한 빈 객체 생성

console.dir(emptyArray.__proto__); // 배열의 프로토타입(Array.prototype) 출력
console.dir(emptyObj.__proto__); // 객체의 프로토타입(Object.prototype) 출력



/* ****************************************** 3-20 예제 ********************************************** */



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




/* ****************************************** 3-21 예제 ********************************************** */



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




/* ****************************************** 3-22 예제 ********************************************** */


var arr = ['zero', 'one', 'two', 'three'];
delete arr[2];
console.log(arr); // ["zero", "one", undefined × 1 , "three"]
console.log(arr.length); // 4



/* ****************************************** 3-23 예제 ********************************************** */


var arr = ['zero', 'one', 'two', 'three'];

arr.splice(2, 1);
console.log(arr); // ["zero", "one", "three"]
console.log(arr.length); // 3


/* ****************************************** 3-24 예제 ********************************************** */


var foo = new Array(3);
console.log(foo); // [undefined, undefined, undefined]
console.log(foo.length); // 3

var bar = new Array(1, 2, 3);
console.log(bar); // [1, 2, 3]
console.log(bar.length); // 3


/* ****************************************** 3-25 예제 ********************************************** */


var arr = ['bar'];
var obj = {
    name : 'foo',
    length : 1
};

arr.push('baz');
console.log(arr);  // ['bar', 'baz']

obj.push('baz');  // Uncaught TypeError: Object #<Object> has no method 'push'


/* ****************************************** 3-26 예제 ********************************************** */


var arr = ['bar'];
var obj = { name : 'foo', length : 1};

arr.push('baz');
console.log(arr); // [‘bar’, ‘baz’]

Array.prototype.push.apply(obj, ['baz']);
console.log(obj); // { '1': 'baz', name: 'foo', length: 2 }


/* ****************************************** 3-29 예제 ********************************************** */


console.log(1 == '1'); // true
console.log(1 === '1'); // false







/* ****************************************** 3-30 예제 ********************************************** */


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


/* ****************************************** 3-31 예제 ********************************************** */





/* ****************************************** 3-32 예제 ********************************************** */





/* ****************************************** 3-33 예제 ********************************************** */
