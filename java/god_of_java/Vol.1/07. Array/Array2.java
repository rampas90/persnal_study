public class Array2 {
	public static void main(String[] args) {
		Array2 sample = new Array2();
		// sample.init();
		// sample.primitiveTypes();
		sample.referenceTypes2();
		// sample.otherInit();
		// sample.twoDimensionArray();
		// sample.printArrayLength();

	}

	public void referenceTypes2() {
		String[] strings = new String[2];
		Array[] array = new Array[2];
		strings[0]="Please visit www.GodOfJava.com.";
		array[0]=new Array();

		System.out.println("strings[0]="+strings[0]);
		System.out.println("strings[1]="+strings[1]);
		System.out.println("array[0]="+array[0]);
		System.out.println("array[1]="+array[1]);

		System.out.println("strings" + strings);
		System.out.println("array" + array);

	}

	

}
