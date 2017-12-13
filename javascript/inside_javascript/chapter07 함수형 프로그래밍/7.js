
/* *************************************** 7-1 예제 ******************************************* */

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


/* *************************************** 7-2-1 예제 ******************************************* */


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



/* *************************************** 7-2-2 예제 ******************************************* */



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




/* *************************************** 7-2-4 예제 ******************************************* */


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





/* *************************************** 7-3-1 예제 ******************************************* */


function fact(num) {
	var val = 1;
	for (var i = 2; i <= num; i++) 
		val = val * i;
	return val;
}

console.log(fact(100));




/* *************************************** 7-3-2 예제 ******************************************* */



function fact(num) {
	if (num == 0) return 1;
	else return num* fact(num-1);
}

console.log(fact(100));




/* *************************************** 7-3-3 예제 ******************************************* */



var fact = function() {
	var cache = {'0' : 1};
	var func = function(n) {
		var result = 0;

		if (typeof(cache[n]) === 'number') {
			result = cache[n];
		} else {
			result = cache[n] = n * func(n-1);
		}

		return result;
	}

	return func;
}();

console.log(fact(10));
console.log(fact(20));




/* *************************************** 7-4 예제 ******************************************* */


var fibo = function() {
	var cache = {'0' : 0, '1' : 1};

	var func = function(n) {
		if (typeof(cache[n]) === 'number') {
			result = cache[n];
		} else {
			result = cache[n] = func(n-1) + func(n-2);
		}

		return result;
	}
	
	return func;
}();

console.log(fibo(10));





/* *************************************** 7-7 예제 ******************************************* */


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





/* *************************************** 7-9 예제 ******************************************* */



var print_all = function(arg) {
	for (var i in this) console.log(i + " : " + this[i]);
	for (var i in arguments) console.log(i + " : " + arguments[i]);
}

var myobj = { name : "zzoon" };

var myfunc = print_all.bind(myobj);
myfunc(); // name : zzoon

var myfunc1 = print_all.bind(myobj, "iamhjoo", "others");
myfunc1("insidejs");


 


/* *************************************** 7-10 예제 ******************************************* */



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



/* *************************************** 7-11 예제 ******************************************* */



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



/* *************************************** 7-13 예제 ******************************************* */



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









