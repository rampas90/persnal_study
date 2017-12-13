public class StringSample
{
	public static void main(String[] args) 
	{
		StringSample sample=new StringSample();
		sample.constructors();
	}


	public void constructors(){
		try{
			//"한글"이라는 값을 갖는 String객체 str생성
			String str="한글";	
			
			// str을 byte배열로 만듬
			byte[] array1=str.getBytes();	

			for(byte data:array1){
				System.out.print(data + " ");
			}

			System.out.println();

			// byte배열(array1)을 매개변수로 갖는 String객체 생성후 출력
			String str1=new String(array1);
			System.out.println(str1);

		} catch(Exception e){
			e.printStackTrace();
		}
	}
}