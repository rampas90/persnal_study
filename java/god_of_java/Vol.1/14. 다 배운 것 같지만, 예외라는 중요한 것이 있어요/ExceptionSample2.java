
public class ExceptionSample2 {

	public static void main(String[] args) {
		ExceptionSample2 sample = new ExceptionSample2();
		sample.multiCatch();
	}

	public void arrayOutOfBounds() {
		int[] intArray = null;
		try{
			//intArray=new int[5];
			System.out.println(intArray[5]);
		}catch(Exception e){
			System.out.println(intArray.length);
		}
		System.out.println("this code shoul run.");
	}

	public void multiCatch() {
		int[] intArray = new int[5];
		try {
			System.out.println(intArray[5]);
		} /*catch (NullPointerException e) {
			System.out.println("NullPointerException occured");
		}*/  catch (Exception e) {
			System.out.println(intArray.length);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("ArrayIndexOutOfBoundsException occured");
		}
	}
}
