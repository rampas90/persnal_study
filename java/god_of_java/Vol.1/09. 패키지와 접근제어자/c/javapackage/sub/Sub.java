package c.javapackage.sub;

public class Sub
{
	public Sub()
	{

	}

	public void subClassMethod()
	{

	}

	public final static String CLASS_NAME = "Sub";

	public static void subClassStaticMethod()
	{
		System.out.println("subClassStaticMethod() is called.");
	}

	public void publicMethod() {
		// 누구나 접근가능
	}

	protected void protectedMethod() {
		// 같은 패키지 내에 있거나 상속받은 경우에만 접근 가능
	}

	void packagePrivateMethod() {
		// 같은 패키지 내에 있을 때만 접근가능
	}

	private void privateMethod() {
		// 해당 클래스 내에서만 접근 가능
	}
}