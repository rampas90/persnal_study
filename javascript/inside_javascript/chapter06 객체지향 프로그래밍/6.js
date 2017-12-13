
/* *************************************** 6-1 예제 ******************************************* */

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



/* *************************************** 6-2 예제 ******************************************* */


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





/* *************************************** 6-4 예제 ******************************************* */


function create_object(o) {
	function F() {}
	F.prototype = o;
	return new F();
}



/* *************************************** 6-5 예제 ******************************************* */


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



/* *************************************** 6-6 예제 ******************************************* */



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



/* *************************************** 6-9 예제 ******************************************* */


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




/* *************************************** 6-10 예제 ******************************************* */


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
 




/* *************************************** 6-12 예제 ******************************************* */


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
 



/* *************************************** 6-13 예제 ******************************************* */


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




/* *************************************** 6-14 예제 ******************************************* */



/* *************************************** 6-15 예제 ******************************************* */






