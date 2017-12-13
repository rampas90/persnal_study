
/* *************************************** 5-1 예제 ******************************************* */

console.log("This is global context");

function ExContext1() {
	console.log("This is ExContext1");
};

function ExContext2() {
	ExContext1();
	console.log("This is ExContext2");
};

ExContext2();



/* *************************************** 5-2 예제 ******************************************* */

function execute(param1, param2) {
	var a = 1, b = 2;
	function func() {
		return a+b;
	}
	return param1+param2+func();
}

execute(3, 4);





/* *************************************** 5-4 예제 ******************************************* */


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


/* *************************************** 5-5 예제 ******************************************* */


var value = "value1";

function printFunc(func) {
    var value = "value2";
	
	function printValue() {
		return value;
	}
	
    console.log(printValue());
}

printFunc();




/* *************************************** 5-7 예제 ******************************************* */

function outerFunc(){
	var x=10;
    var innerFunc = function(){console.log(x);}
    
    return innerFunc;
}
var inner = outerFunc();
inner(); //10



/* *************************************** 5-9 예제 ******************************************* */


function HelloFunc(func) {
	this.greeting = "hello";
}

HelloFunc.prototype.call = function(func) {
	func ? func(this.greeting) : this.func(this.greeting);
}	

var userFunc = function(greeting) {
	console.log(greeting);
}

var objHello = new HelloFunc();
objHello.func = userFunc;
objHello.call();



/* *************************************** 5-10 예제 ******************************************* */



function HelloFunc(func) {
	this.greeting = "hello";
}

HelloFunc.prototype.call = function(func) {
	func ? func(this.greeting) : this.func(this.greeting);
}	

var userFunc = function(greeting) {
	console.log(greeting);
}

var objHello = new HelloFunc();
objHello.func = userFunc;
objHello.call();

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





/* *************************************** 5-11 예제 ******************************************* */


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




/* *************************************** 5-12 예제 ******************************************* */



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






/* *************************************** 5-13 예제 ******************************************* */



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





/* *************************************** 5-14 예제 ******************************************* */


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


/* *************************************** 5-15 예제 ******************************************* */



function func(){
   var x = 1;
   return {
      func1 : function(){ console.log(++x); },
      func2 : function(){ console.log(-x); }
   };
};

var exam = func();
exam.func1();
exam.func2();




/* *************************************** 5-16 예제 ******************************************* */


function countSeconds(howMany) {
  for (var i = 1; i <= howMany; i++) {
    setTimeout(function () {
      console.log(i);
    }, i * 1000);
  }
};
countSeconds(3);
