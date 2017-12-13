
public class ExceptionSample {

	public static void main(String[] args) {
		ExceptionSample sample = new ExceptionSample();
		sample.arrayOutOfBounds();
		// sample.arrayOutOfBounds2();
		// sample.finallySample();
		// sample.multiCatch();
		// sample.throwable();

		// try {
		// sample.throwException(13);
		// sample.multiThrow();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		// try {
		// sample.throwException2(13);
		// } catch (MyException e) {
		// e.printStackTrace();
		// }

	}

	public void arrayOutOfBounds() {
		int[] intArray = new int[5];
		try {
			System.out.println(intArray[5]);
			System.out.println("This code should run.");
		} catch (Exception e) {
			System.out.println("Exception occured.");
			// System.out.println(intArray.length);
		}
		// System.out.println("This code should run.");
	}

	public void arrayOutOfBounds2() {
		int[] intArray = null;
		try {
			intArray = new int[5];
			System.out.println(intArray[5]);
			System.out.println("This code should run.");
		} catch (Exception e) {
			System.out.println(intArray.length);
		}
		// System.out.println("This code should run.");
	}

	public void finallySample() {
		int[] intArray = new int[5];
		;
		try {
			System.out.println(intArray[5]);
		} catch (Exception e) {
			System.out.println(intArray.length);
		} finally {
			System.out.println("Here is finally");
		}
		System.out.println("This code should run.");
	}

	public void multiCatch() {
		int[] intArray = new int[5];
		try {
			System.out.println(intArray[5]);
		} catch (NullPointerException e) {
			System.out.println("NullPointerException occured");
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("ArrayIndexOutOfBoundsException occured");
		} catch (Exception e) {
			System.out.println(intArray.length);
		}
	}

	public void throwable() {
		int[] intArray = new int[5];
		try {
			intArray = null;
			System.out.println(intArray[5]);
		} catch (Throwable t) {
			System.out.println(t.getMessage());
			System.out.println(t.toString());
		}
	}

	public void throwException(int number) throws Exception {
		try {
			if (number > 12) {
				throw new Exception("Number is over than 12");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void multiThrow() throws NullPointerException,
			ArrayIndexOutOfBoundsException {
		int[] intArray = new int[5];
		intArray = null;
		System.out.println(intArray[5]);
	}

	public void throwException2(int number) throws MyException {
		try {
			if (number > 12) {
				throw new Exception("Number is over than 12");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
