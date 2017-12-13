public class NumberObject 
{
	public static void main(String[] args) 
	{
		NumberObject no=new NumberObject();
		//no.parseLong("r1024");
		//no.parseLong("1024");
		no.printOtherBase(1024);
	}

	public long parseLong(String data){
		long longdata=-1;
		
		try{
			
			longdata=Long.parseLong(data);
			System.out.println(longdata);

		} catch(NumberFormatException ne){
			System.out.println(data+" is not a number");
		}
		catch(Exception e){

		}
		return longdata;
		
	}

	public void printOtherBase(long value){

		System.out.println("Original:"+value);
		System.out.println("Binary:"+Long.toBinaryString(value));
		System.out.println("Hex:"+Long.toHexString(value));
		System.out.println("Octal:"+Long.toOctalString(value));
	}
}
