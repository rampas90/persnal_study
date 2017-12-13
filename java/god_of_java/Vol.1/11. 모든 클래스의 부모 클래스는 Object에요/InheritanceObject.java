public class InheritanceObject {
	public static void main(String[] args) {
		InheritanceObject obj = new InheritanceObject();
		obj.toStringMethod(obj);


		//obj.toStringMethod(sample);
		//obj.toStringMethod2();
		//obj.equalMethod();
	}

	public void toStringMethod(InheritanceObject obj) {
		System.out.println(obj);
		System.out.println(obj.toString());
		System.out.println("plus " + obj);
	}

	public String toString() {
		return "InheritanceObject class";
	}




}


