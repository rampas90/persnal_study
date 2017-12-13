
/* *************************************** 4-1 예제 ******************************************* */

// add() 함수 선언문
function add(x, y) {
    return x + y;
}

console.log(add(3, 4)); // 7


/* *************************************** 4-2 예제 ******************************************* */


// add() 함수 표현식
var add = function (x, y) {
    return x + y;
};

var plus = add;

console.log(add(3,4)); // 7
console.log(plus(5,6)); // 11



/* *************************************** 4-3 예제 ******************************************* */


var add = function sum(x, y) {
    return x + y;
};

console.log(add(3,4)); // 7
console.log(sum(3,4)); // Uncaught ReferenceError: sum is not defined 에러 발생


/* *************************************** 4-4 예제 ******************************************* */


var factorialVar = function factorial(n) {
    if(n <= 1) {
        return 1;
    }
    return n * factorial(n-1);
};

console.log(factorialVar(3));  // 6
console.log(factorial(3)); // Uncaught ReferenceError: factorial is not defined 에러 발생


/* *************************************** 4-5 예제 ******************************************* */


var add = new Function('x', 'y', 'return x + y');
console.log(add(3, 4)); // 7



/* *************************************** 4-6 예제 ******************************************* */


console.log(add(2,3)); // 5

// 함수 선언문 형태로 add() 함수 정의
function add(x, y) {
    return x + y;
}

console.log(add(3, 4)); // 7


/* *************************************** 4-7 예제 ******************************************* */


add(2,3); // uncaught type error

// 함수 표현식 형태로 add() 함수 정의
var add = function (x, y) {
    return x + y;
};

add(3, 4);



/* *************************************** 4-8 예제 ******************************************* */


// 함수 선언 방식으로 add()함수 정의
function add(x, y) {
    return x+y;
}

// add() 함수 객체에 result, status 프로퍼티 추가
add.result = add(3, 2);
add.status = 'OK';

console.log(add.result); // 5
console.log(add.status); // 'OK'



/* *************************************** 4-9 예제 ******************************************* */


// 변수에 함수 할당
var foo = 100;
var bar = function () { return 100; };
console.log(bar()); // 100

// 프로퍼티에 함수 할당
var obj = {};
obj.baz = function () {return 200; }
console.log(obj.baz()); // 200




/* *************************************** 4-10 예제 ******************************************* */


// 함수 표현식으로 foo() 함수 생성
var foo = function(func) {
    func(); // 인자로 받은 func() 함수 호출
};

// foo() 함수 실행
foo(function() {
    console.log('Function can be used as the argument.');
});




/* *************************************** 4-11 예제 ******************************************* */


// 함수를 리턴하는 foo() 함수 정의
var foo = function() {
    return function () {
        console.log('this function is the return value.')
    };
};

var bar = foo();
bar();


/* *************************************** 4-12 예제 ******************************************* */


function add(x, y) {
    return x + y;
}

console.dir(add);




/* *************************************** 4-13 예제 ******************************************* */



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




/* *************************************** 4-15 예제 ******************************************* */



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



/* *************************************** 4-16 예제 ******************************************* */


(function (name) {
    console.log('This is the immediate function --> ' + name);
})('foo');



/* *************************************** 4-18 예제 ******************************************* */



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



/* *************************************** 4-19 예제 ******************************************* */

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


/* *************************************** 4-20 예제 ******************************************* */



// self() 함수
var self = function () {
    console.log('a');
    return function () {
        console.log('b');
    }
}
self = self();  // a
self();  // b



/* *************************************** 4-21 예제 ******************************************* */


function func(arg1, arg2) {
    console.log(arg1, arg2);
}

func();   // undefined undefined
func(1);  // 1 undefined
func(1,2);  // 1 2
func(1,2,3); // 1 2



/* *************************************** 4-23 예제 ******************************************* */


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



/* *************************************** 4-24 예제 ******************************************* */



var foo = "I'm foo"; // 전역 변수 선언

console.log(foo); // I’m foo
console.log(window.foo); // I’m foo



/* *************************************** 4-25 예제 ******************************************* */



var test = 'This is test';
console.log(window.test);

// sayFoo() 함수
var sayFoo = function () {
    console.log(this.test); // sayFoo() 함수 호출 시 this는 전역 객체에 바인딩된다.
};

sayFoo(); // this.test 출력


/* *************************************** 4-27 예제 ******************************************* */


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




/* *************************************** 4-28 예제 ******************************************* */


// Person 생성자 함수
var Person = function (name) {
    this.name = name;
};

// foo 객체 생성
var foo = new Person('foo');
console.log(foo.name); // foo




/* *************************************** 4-29 예제 ******************************************* */


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



/* *************************************** 4-30 예제 ******************************************* */



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



/* *************************************** 4-37 예제 ******************************************* */



// Person 생성자 함수
function Person(name) {
    this.name = name;
}

// foo 객체 생성
var foo = new Person('foo');

console.dir(Person);
console.dir(foo);




/* *************************************** 4-38 예제 ******************************************* */



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


/* *************************************** 4-39 예제 ******************************************* */



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



/* *************************************** 4-40 예제 ******************************************* */


String.prototype.testMethod = function () {
    console.log('This is the String.prototype.testMethod()');
};

var str = "this is test";
str.testMethod();

console.dir(String.prototype);




/* *************************************** 4-41 예제 ******************************************* */

function Person(name){
	this.name=name;
}

var foo = new Person('foo');

//foo.sayHello();

Person.prototype.sayHello = function(){
	console.log('Hello');
}

foo.sayHello();

/* *************************************** 4-42 예제 ******************************************* */



// Person() 생성자 함수
function Person(name) {
    this.name = name;
}

// getName() 프로토타입 메서드
Person.prototype.getName = function () {
    return this.name;
};

// foo 객체 생성
var foo = new Person('foo');

console.log(foo.getName()); // foo

//Person.prototype 객체에 name 프로퍼티 동적 추가
Person.prototype.name = 'person';

console.log(Person.prototype.getName()); // person




/* *************************************** 4-43 예제 ******************************************* */


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


/* *************************************** 4-44 예제 ******************************************* */




// Person() 생성자 함수
function Person(name) {
    this.name = name;
}

Person.prototype.country = 'Korea';

var foo = new Person('foo');
var bar = new Person('bar');
console.log(foo.country);
console.log(bar.country);
foo.country = 'USA';

console.log(foo.country);
console.log(bar.country);
