
public class WildcardSample {

	public static void main(String[] args) {
		WildcardSample sample = new WildcardSample();
//		sample.callWildcardMethod();
//		sample.callBoundedWildcardMethod();
		sample.callGenericMethod();
	}

	public void callWildcardMethod() {
		WildcardGeneric<String> wildcard = new WildcardGeneric<String>();
		wildcard.setWildcard("A");
		wildcardMethod(wildcard);
	}

	public void wildcardMethod(WildcardGeneric<?> c) {
		Object value=c.getWildcard();
		System.out.println(value);
	}
	public void callWildcardMethod2() {
		WildcardGeneric<?> wildcard=new WildcardGeneric<String>();
//		wildcard.setWildcard("A");//Compile Error
		wildcardMethod(wildcard);
	}
	
	public void callBoundedWildcardMethod() {
		WildcardGeneric<Car> wildcard=new WildcardGeneric<Car>();
		wildcard.setWildcard(new Car("BMW"));
		
//		WildcardGeneric<Bus> wildcard=new WildcardGeneric<Bus>();
//		wildcard.setWildcard(new Bus("Bongo"));

		
		wildcardMethod(wildcard);
	}	
	public void boundedWildcardMethod(WildcardGeneric<? extends Car> c) {
		Car value=c.getWildcard();
		System.out.println(value);
	}

	public <T> void genericMethod(WildcardGeneric<T> c, T addValue) {
		c.setWildcard(addValue);
		T value=c.getWildcard();
		System.out.println(value);
	}
	public void callGenericMethod() {
		WildcardGeneric<String> wildcard=new WildcardGeneric<String>();
		genericMethod(wildcard,"Data");
	}


}
