public class PrimitiveTypes
{
	public void checkByte()
	{
		byte byteMin = -128l
		byte byteMax = 127;
		System.out.println("byteMin="byteMin);
		System.out.println("byteMax="byteMax);
		byteMin--;
		byteMax++;
		System.out.println("byteMin--="byteMin);
		System.out.println("byteMax++="byteMax);
	}
	
	public static void main(String[] args)
	{
		PrimitiveTypes types=new PrimitiveTypes();
		types.checkByte();
	}
	
	public void checkOtherTypes()
	{
		short shortMax=32767;
		int intMax=2147483647;
		long longMax=9223372036854775807l;
		//  맨 마지막은 소문자L 이다. long 타입을 명시하기 위해 사용
	}
}
