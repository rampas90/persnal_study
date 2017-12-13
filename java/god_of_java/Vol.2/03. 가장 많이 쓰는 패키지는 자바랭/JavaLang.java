public class JavaLang
{
	public static void main(String[] args) 
	{
		JavaLang sample = new JavaLang();

		//sample.numberTypeCheck();
		//sample.numberTypeCheck2();
		//sample.numberMinMaxCheck();
		sample.systemPropertiesCheck();
	}

	public void numberTypeCheck() {
		String value1 = "3";
		String value2 = "5";

		// 3과 5라는 String값을 parseByte() 메소드를 사용하여 byte로 변환
		byte byte1 = Byte.parseByte(value1);
		byte byte2 = Byte.parseByte(value2);

		System.out.println(byte1 + byte2);

		Integer refInt1 = Integer.valueOf(value1);
		Integer refInt2 = Integer.valueOf(value2);
		System.out.println(refInt1 + refInt2 + "7");
	}

	public void numberTypeCheck2(){
		Integer refInt1;
		refInt1=100;
		System.out.println(refInt1.doubleValue());
	}

	public void numberMinMaxCheck() {
		long startTime = System.currentTimeMillis();
		long startNanoTime = System.nanoTime();

		System.out.println("Byte min=" + Byte.MIN_VALUE + " max=" + Byte.MAX_VALUE);
		System.out.println("Short min=" + Short.MIN_VALUE + " max=" + Short.MAX_VALUE);
		System.out.println("Integer min=" + Integer.MIN_VALUE + " max=" + Integer.MAX_VALUE);
		System.out.println("Long min=" + Long.MIN_VALUE + " max=" + Long.MAX_VALUE);
		System.out.println("Float min=" + Float.MIN_VALUE + " max=" + Float.MAX_VALUE);
		System.out.println("Double min=" + Double.MIN_VALUE + " max=" + Double.MAX_VALUE);
		System.out.println("Character min=" + (int) Character.MIN_VALUE + " max=" + (int) Character.MAX_VALUE);

	}

	public void systemPropertiesCheck() {
		System.out.println("java.version=" + System.getProperty("java.version"));
		System.out.println("JAVA_HOME=" + System.getenv("JAVA_HOME"));
	}

}