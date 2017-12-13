public class StringSample2
{
	public static void main(String[] args) 
	{
		StringSample2 sample=new StringSample2();
		sample.constructors();
	}

	public void printByteArray(byte[] array){
		for(byte data:array){
			System.out.print(data+" ");
		 }
		 System.out.println();    
	}



	public void constructors(){
		try{
			
			String str="ÇÑ±Û";

			byte[] array1=str.getBytes();
			printByteArray(array1);
			String str1=new String(array1);
			System.out.println(str1);

			byte[] array2=str.getBytes();
			printByteArray(array2);
			String str2=new String(array2, "EUC-KR");
			System.out.println(str2);

			byte[] array3=str.getBytes("UTF-8");
			printByteArray(array3);
			String str3=new String(array3, "UTF-8");
			System.out.println(str3);

		} catch(Exception e){
			e.printStackTrace();
		}
	}
}