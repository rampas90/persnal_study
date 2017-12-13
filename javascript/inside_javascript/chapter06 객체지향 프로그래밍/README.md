## 6. 객체지향 프로그래밍

다음에 나오는 객체지향 언어의 특성을 자바스크립트로 구현하는 방법을 알아보자

1. 클래스, 생성자, 메소드
2. 상속
3. 캡슐화

- `C++`이나 `Java` 같은 클래스 기반의 언어와 달리 프로토타입 기반의 언어인 자바스크립트는 
객체의 자료구조, 메소드등을 동적으로 바꿀 수 있다.

- 정확성, 안정성, 예측성 등의 언어는 클래스 기반의 언어가 좀다 나은 결과를 보인다는 단점이 있고,
- 반면 프로토타입 기반인 자바스크립트는 동적으로 자유롭게 객체의 구조와 동작 방식을 바꿀 수 있다는 장점이 있다.


### 6.1 클래서, 생성자, 메소드

C++이나 Java와 같은 경우 class 라는 키워드를 제공하여 클래스를 만들 수 있다.
클래스와 같은 이름의 메소드로 생성자를 구현해 낸다.

하지만 자바스크립트에는 이러한 개념이 없다.
자바스크립트는 거의 모든 것이 객체이고, 특히 함수 객체로 많은 것을 구현해 낸다.
클래스, 생성자, 메소드도 모두 함수로 구현이 가능하다.

```javascript
function Person(arg){
	this.name=arg;
	this.getName=function(){
		return this.name;
	}
	this.setName=function(value){
		this.name=value;
	}
}

var me = new Person("zzoon");
console.log(me.getName());

me.setName("iamhjoo");
console.log(me.getName());
```

> 위 코드를 차근 차근살펴보자

아래 형태는 기존 객체지향 프로그래밍 언어에서 한 클래스의 인스턴스를 생성하는 코드와 매우 유사하다.
여기서 함수 `Person`이 **클래스**이자 **생성자**의 역할을 한다.

```javascript
var me = new Person("zzoon");
```

자바스크립트에서 클래스 기반의 객체지향 프로그래밍은 기본적인 형태가 이와 같다.
클래스 및 생성자의 역할을 하는 함수가 있고, 사용자는 new키워드로 인스턴스를 생성하여 사용할 수 있다.
예제에서 생성된 `me`는 Person의 인스턴스로서 `name`변수가 있고, `getName()`과 `setName()`함수가 있다.

> 하지만 이 예제는 문제가 많다.
> 정확히는 이 예제의 Person함수의 구현히 바람직하지 못하다
> 이 Person을 생성자로 하여 여러개의 객체를 생성한다고 가정해보자

```javascript
var me = new Person("me");
var you = new Person("you");
var him = new Person("him");
```

위와 같이 객체를 생성하여 사용하면 겉으로는 별 문제 없이 작동하지만,
각각의 객체는 자기 영역에서 공통적으로 사용할 수 있는 setName(), getName()함수를 따로 생성하고 있다.
이는 불필요하게 중복되는 영역을 메모리에 올려놓고 사용함을 의미하고 자원낭비를 가져온다.

> 따라서 앞의 문제를 해결하려면 다른 방식의 접근이 필요한데, 여기서 활용할 수 있는 자바스크립트의 특성이 
> 함수 객체의 프로토타입이다.

```javascript
function Person(arg) {
	this.name = arg;
}

Person.prototype.getName = function() {
	return this.name;
}

Person.prototype.setName = function(value) {
	this.name = value;
}

var me = new Person("me");
var you = new Person("you");
console.log(me.getName());
console.log(you.getName());
```

> 위처럼 Person 함수 객체의 prototype프로퍼티에 getName()과 setName()함수를 정의했다.
> 이 Person으로 객체를 생성한다면 각 객체는 각자 따로 함수 객체를 생성할 필요 없이 프로토타입 체인으로 접근할 수 있다.

> 이와 같이 자바스크립트에서 클래스 안의 메소드를 정의할 때는 프로토타입 객체에 정의한 후,
> new 로 생성한 객체에서 접근할 수 있게 하는 것이 좋다.

> 더글라스 크락포드는 다음과 같은 함수를 제시 하면서 메소드를 정의하는 방법을 소개한다.

```javascript
Function.prototype.method = functioin(name,func){
	if(!this.prototype[name]){
    	this.prototype[name] = func;
    }
}
```

> 위의 함수를 이용한다면 다음과 같은 형태가 된다

```javascript
Function.prototype.method=function(name,func){
	this.prototype[name] = func;
}

function Person(arg){
	this.name = arg;
}

Person.method("setName", function(value){
	this.name = value;
});

Person.method("getName", function(){
	return this.name
});

var me = new Person("me");
var you = new Person("you");
console.log(me.getName());
console.log(you.getName());
```

### 6.2 상속

- 자바스크립트는 클래스를 기반으로 하는 전통적인 상속을 지원하지는 않는다.
- 하지만 객체 프로토타입 체인을 이용하여 상속을 구현해낼 수 있다.

이러한 상속의 구현방식은 크게 두 가지로 구분할 수 있다.

- 클래스 기반 전통적인 상속방식을 흉내 내는 것
- 클래스 개념 없이 객체의 프로토타입으로 상속을 구현하는 방식이다.
	- 이를 프로토타입을 이용한 상속이라고 한다.

#### 6.2.1 프로토타입을 이용한 상속

> 아래코드는 더글라스 크락포드가 자바스크립트 객체를 상속하는 방법으로 오래전에 소개한 코드다.
> 조금 과장해서 말하면 이 세줄의 코드를 이해하면 자바스크립트에서의 프로토타입 기반의 상속을 다 배운것이나 다름없다.


```javascript
function create_object(o){
	function F(){}
    F.prototype = o;
    return new F();
}
```

create_object() 함수는 인자로 들어온 객체를 부모로 하는 자식객체를 생성하여 반환한다.
순서를 살펴보자면
1. 새로운 빈 함수 객체 F를 만들고
2. F.prototype 프로퍼티에 인자로 들어온 객체를 참조한다.
3. 함수 객체 F를 생성자로 하는 새로운 객체를 만들어 반환한다.
4. 반환된 객체는 부모객체의 프로퍼티에 접근할 수 있고, 자신만의 프로퍼티를 만들 수도 있다.

> 이와 같이 프로토타입의 특성을 활용하여 상속을 구현하는 것이 프로토타입 기반의 상속이다.

> 참고로 위의 create_object() 함수는 ECMAScript 5에서 Object.create() 함수로 제공되므로 따로 구현할 필요없다.

그럼 create_object() 함수를 이용하여 상속을 구현해보자

```javascript
var person = {
	name : "zzoon",
	getName : function() {
		return this.name;
	},
	setName : function(arg) {
		this.name = arg;
	}
};

function create_object(o) {
	function F() {};
	F.prototype = o;
	return new F();
}

var student = create_object(person);

student.setName("me");
console.log(student.getName());
```

> 앞서 말했듯이 "반환된 객체는 부모객체의 프로퍼티에도 접근할 수 있지만 자신만의 프로퍼티를 만들 수도 있다"고 했다.

```javascript
student.setAge = function(age){}
student.getAge = function(){}
```

단순하게 앞과 같이 그 기능을 더 확장시킬수 도 있다.
하지만 이렇게 구현하면 코드가 지저분해지기 십상이다.

보다 깔끔한 방법을 생각해보자..

자바스크립트에서는 범용적으로 `extend()` 라는 이름의 함수로 객체에 자신이 원하는 객체 혹은 함수를 추가시킨다.

> 자세한 내용은 p.177~178 참조 ( jQuery.extend 분석 내용 )


```javascript
var person = {
	name : "zzoon",
	getName : function() {
		return this.name;
	},
	setName : function(arg) {
		this.name = arg;
	}
};

function create_object(o) {
	function F() {};
	F.prototype = o;
	return new F();
}

function extend(obj,prop) {
	if ( !prop ) { prop = obj; obj = this; }
	for ( var i in prop ) obj[i] = prop[i];
	return obj;
};


var student = create_object(person);

student.setName("me");
console.log(student.getName());

var added = {
	setAge : function(age) {
		this.age = age;
	},
	getAge : function() {
		return this.age;
	}
};

extend(student, added);

student.setAge(25);
console.log(student.getAge());
```

> 위 코드에서는 얕은 복사를 사용하는 extend()함수를 사용하여 student 객체를 확장시켰다.


#### 6.2.2 클래스 기반의 상속

**원리는 프로토타입을 이용한 상속**과 원리는 거의 같다. 
다만 프로토타입형 상속은 객체리터럴로 생성된 객체의 상속을 소개했지만, 여기서는 클래스의 역할을 하는 함수로 상속을 구현한다.

```javascript
function Person(arg){
	this.name = arg;
}

Person.prototype.setName = function(value){
	this.name = value;
};

Person.prototype.getName = function(){
	return this.name;
}

function Student(arg){
	Person.apply(this,arguments);
}

var you = new Person("iamgjoo");
Student.prototype = you;

var me = new Student("zzoon");
me.setName("zzoon");
console.log(me.getName());
```

> 현재는 자식 클래스의 객체가 부모 클래스의 객체를 프로토타입체인으로 직접 접근한다.
> 하지만 부모 클래스의 인스턴스와 자식 클래스의 인스턴스는 서로 독립적일 필요가 있다..

두 클래스의 프로토타입 사이에 중개자를 하나 만들어 보자

```javascript
function Person(arg){
	this.name = arg;
}

Function.prototype.method = function(name, func){
	this.prototype[name] = func;
}

Person.prototype.setName = function(value){
	this.name = value;
};

Person.prototype.getName = function(){
	return this.name;
}

function Student(arg){
	Person.apply(this,arguments);
}

function F(){};
F.prototype = Person.prototype;
Student.prototype = new F();
Student.prototype.constructor = Student;
Student.super = Person.prototype;

var me = new Student();
me.setName("zzoon");
console.log(me.getName());
```

--- 


### 6.3 캡슐화

- 관련된 여러가지 정보를 하나의 틀 안에 담는 것을 의미한다.
- 이를 응용하면 멤버 변수와 메소드가 서로 관련된 정보가 되고 클래스가 이것을 담는 하나의 큰 틀이라고 할 수 있다.
- 여기서 중요한 것은 정보의 공개 여부이다.

자바스크립트에서는 Java의 public, private 같은 접근지정좌 관련 예약어를 지원하지 않는다.
그렇다고 해서 자바스크립트에서 정보 은닉이 불가능한 것은 아니다.

```javascript
var Person = function(arg) {
	var name = arg ? arg : "zzoon" ;

	this.getName = function() {
		return name;
	}
	this.setName = function(arg) {
		name = arg;
	}
}; 

var me = new Person();
console.log(me.getName());
me.setName("iamhjoo");
console.log(me.getName());
console.log(me.name); // undefined
```

> this객체의 프로퍼티로 선언하면 외부에서 new 키워드로 생상한 객체로 접근 할 수 있다. 

하지만  var로 선언된 멤버들은 외부에서 접근히 불가능하다.
그리고 public 메소드가 클로저 역할을 하면서 private 멤버인 name 에 접근할 수 있다.


```javascript
 var Person = function(arg) { 
	var name = arg ? arg : "zzoon" ;
	
	return {
		getName : function() {
			return name;
		},
		setName : function(arg) {
			name = arg;
		}
	};
 }
 
 var me = Person(); /* or var me = new Person(); */
 console.log(me.getName());
```

> Pseson( 함수를 호출하여 객체를 반환한다.)
> 이 객체에 Person 함수의 private멤버에 접근할 수 있는 메소드들이 담겨있다.

사용자는 반환받는 객체로 메소드를 호출할 수 있고,
private 멤버에 접근할 수 있다.

> 이렇게 메소드가 담겨있는 객체를 반환하는 함수는 여러 유명 자바스크립트 라이브러리에서 쉽게 볼 수 있는 구조이다.

이 코드를 개선해보자

```javascript
 var Person = function(arg) { 
	var name = arg ? arg : "zzoon" ;

	var Func = function() {}
	Func.prototype = {
		getName : function() {
			return name;
		},
		setName : function(arg) {
			name = arg;
		}
	};

	return Func;
 }();


var me = new Person();
console.log(me.getName());

```



---


### 6.4 객체지향 프로그래밍 응용 예제

#### 6.4.1 클래스의 기능을 가진 subClass 함수 

**프로토타입을 이용한 상속**과 **클래스 기반의 상속**에서 소개한 내용을 바탕으로 기존 클래스와 같은 기능을 하는 자바스크립트 함수를 만들어 보자.

여기서는 다음 세가지를 활용해서 구현한다.
- 함수의 프로토타입 체인
- extend 함수
- 인스턴스를 생성할 때 생성자 호출(여기서는 생성자를 _int 함수로 정한다.)


##### 6.4.1.1 subClass 함수 구조

subClass는 상속받을 클래스에 넣을 변수 및 메소드가 담긴 객체를 인자로 받아 부모 함수를 상속 받는 자식 클래스를 만든다.
여기서 부모 함수는 subClass() 함수를 호출할 때 this 객체를 의미한다.

```javascript
var SuperClass = subClass(obj);
var SubClass = SuperClass.subClass(obj);
```

> 이처럼 SuperClass를 상속받는 subClass를 만들고자 할때, SuperClass.subClass()의 형식으로 호출하게 구현한다.
> 참고로 최상위 클래스인 Superclass는 자바스크립트의 Function을 상속 받게 한다.

함수 subClass의 구조는 다음과 같이 구성된다.

```javascript
function subClass(obj){
	/* 1) 자식 클래스 (함수객체) 생성  */
    /* 2) 생성자호출  */
    /* 3) 프로토타입 체인을 활용한 상속 구현  */
    /* 4) obj를 통해 들어온 변수 및 메소드를 자식 클래스에 추가  */
    /* 5) 자식 함수 객체 반환  */
}
```


##### 6.4.1.2 자식 클래스 생성 및 상속


```javascript
function subClass(obj){
	//생략

    var parent = this;
    var F = function(){};

    var child = function(){
    };

    F.prototype = parent.prototype;
    child.prototype = new F();
    child.prototype.constructor = child;
    child.parent = parent.prototype;
    child.parent_constructor = parent;

    // 생략

    return child;
}
```


자식 클래스는 child 라는 이름의 함수 객체를 생성함으로써 만들어졌다.
부모 클래스를 가리키는 parent는 this를 그대로 참조한다.

##### 6.4.1.3 자식 클래스 확장

이제 사용자가 인자로 넣은 객체를 자식 클래스에 넣어 자식 클래스를 확장할 차례다.

```javascript
for(var i in obj){
	if(obj.hasOwnProperty(i)){
    	child.prototype[i] = obj[i];
    }
}
```

프로토타입을 이용한 상속에서 살펴본 extend()함수의 역할을 하는 코드를 추가했다
여기서는 간단히 얕은 복사로 객체의 프로퍼티를 복사하는 방식을 택했다


##### 6.4.1.4 생성자 호출

클래스의 인스턴스가 생성될 때, 클래스 내에 정의된 생성자가 호출돼야 한다.
이를 자식 클래스 안에 구현하자.

```javascript
var child = function(){
	if(parent.hasOwnProperty("_init")){
    	parent._init.apply(this, arguments);
    }
    if(child.prototype.hasOwnProperty("_init")){
    	child.prototype._init.apply(this, arguments);
    }
};
```

> 위 코드에서 한 가지를 더 고려해야 한다.
> 앞 코드는 단순히 부모와 자식이 한쌍을 이루었을 때만 제대로 동작한다. 
> 자식을 또 다른 함수가 다시 상속 받았을 때는 어떻게 될 것인가?


```javascript
var Superclass = subClass();
var SubClass = SuperClass.subClass();
var Sub_SubClass = SubClass.subClass();

var instance = new Sub_SubClass();
```

이 코드에서 instance를 생성할 때, 그 상위 클래스의 상위 클래스인 SuperClass의 생성자가 호출이 되지 않는다.
따라서 부모 클래스의 생성자를 호출하는 코드는 재귀적으로 구현할 필요가 있다.


> 보다 자세한 사항은 p.190 (6.5 객체지향 프로그래밍 응용 참고)

`subClass`함수의 전체코드는 다음과 같다.

```javascript
function subClass(obj) {
	var parent = this === window ? Function : this; // Node.js의 경우에는 global를 사용한다.
	var F = function() {};

	var child = function() {
		var _parent = child.parent;
		
		if (_parent && _parent !== Function) {
		     _parent.apply(this, arguments);
		}
		
		if (child.prototype._init) {
			child.prototype._init.apply(this, arguments);
		}
	};

	F.prototype = parent.prototype;
	child.prototype = new F();
	child.prototype.constructor = child;
	child.parent = parent;
	child.subClass = arguments.callee;

	for (var i in obj) {
		if (obj.hasOwnProperty(i)) {
			child.prototype[i] = obj[i];
		}
	}

	return child;
}
```


> 이제 subClass 함수로 상속 에제를 만들어 보자


```javascript
function subClass(obj) {
	var parent = this === global ? Function : this;
	var F = function() {};

	var child = function() {
		var _parent = child.parent;
		
		if (_parent && _parent !== Function) {
			_parent();
		}
		
		if (child.prototype._init) {
			child.prototype._init.apply(this, arguments);
		}
	};

	F.prototype = parent.prototype;
	child.prototype = new F();
	child.prototype.constructor = child;
	child.parent = parent;
	child.subClass = arguments.callee;

	for (var i in obj) {
		if (obj.hasOwnProperty(i)) {
			child.prototype[i] = obj[i];
		}
	}

	return child;
}

var person_obj = {
	_init : function() {
		console.log("person init");
	},
	getName : function() {
		return this._name;
	},
	setName : function(name) {
		this._name = name;
	}
};

var student_obj = {
	_init : function() {
		console.log("student init");
	},
	getName : function() {
		return "Student Name: " + this._name;
	}
};

var Person = subClass(person_obj);
var person = new Person();
person.setName("zzoon");
console.log(person.getName());

var Student = Person.subClass(student_obj);
var student = new Student();
student.setName("iamhjoo");
console.log(student.getName());
console.log(Person.toString());
```

> 위 에제에서 다음의 내용을 다시 한번 살펴보자
> - 생성자 함수가 호출되는가?
> - 부모의 메소드가 자식인스턴스에서 호출되는가?
> - 자식클래스가 확장 가능한가?
> - 최상위 클래스인 Person은 Function을 상속받는가?


ㄴ