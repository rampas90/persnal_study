public class StringSample4
{
	public static void main(String[] args) 
	{
		StringSample4 sample=new StringSample4();
		//sample.constructors();
		//System.out.println(sample.nullCheck(null));
		//sample.equalCheck();
		sample.compareToCheck();
	}

	public void compareToCheck(){
		String text="a";
		String text2="b";
		String text3="c";

		System.out.println(text2.compareTo(text));
		System.out.println(text2.compareTo(text3));
		System.out.println(text.compareTo(text3));
	}

	public void equalCheck(){
		String text="Check value";
		String text2="Check value";
		if(text==text2){
			System.out.println("text==text2 result is same");
		}
		else{
			System.out.println("text==text2 result is different");
		}
		if(text.equals("Check value")){
			System.out.println("text.equals(text2) result is same");
		}

		String text3="check value";
		if(text.equalsIgnoreCase(text3)){
			System.out.println("text.equalsIgnoreCase(text3) result is same");
		}
	}

	public void comparecheck(){
		String text="You must know String class.";
		System.out.println("text.length()="+text.length());
		System.out.println("text.isEmpty()="+text.isEmpty());
	}

	

	

	
}