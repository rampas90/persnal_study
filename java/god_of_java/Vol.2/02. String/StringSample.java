public class StringSample
{
	public static void main(String[] args) 
	{
		StringSample sample=new StringSample();
		sample.constructors();
	}


	public void constructors(){
		try{
			//"�ѱ�"�̶�� ���� ���� String��ü str����
			String str="�ѱ�";	
			
			// str�� byte�迭�� ����
			byte[] array1=str.getBytes();	

			for(byte data:array1){
				System.out.print(data + " ");
			}

			System.out.println();

			// byte�迭(array1)�� �Ű������� ���� String��ü ������ ���
			String str1=new String(array1);
			System.out.println(str1);

		} catch(Exception e){
			e.printStackTrace();
		}
	}
}