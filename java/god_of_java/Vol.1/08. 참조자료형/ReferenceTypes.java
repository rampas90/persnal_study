public class ReferenceTypes {

	// 기본 생성
	public ReferenceTypes() {

	}

	// 문자열을 매개변수로 받는 생성자를 생성
	// 생성자는 아래처럼 메소드와 비슷하게 생겼지만, 리턴타입이 없고 클래스 이름으로 되어있따
	// 기본생성자 없이 아래처럼 문자열을 받는 생성자만 생성할경우는 컴파일 에러가 난다.
	public ReferenceTypes(String data) {

	}

	public static void main(String[] args) {
		// 클래스명 _ 인스턴스 변수 = new 생성자 호출
		/*
			매개 변수가 없는 기본 생성자의 경우 자동으로 생성된다.
		*/
		ReferenceTypes sample = new ReferenceTypes();
		//sample.checkMemberDTOName();
		//sample.checkMemberDTOName();
		//sample.makeStaticBlockObject();
		//sample.callPassByValue();
		//System.out.println("----------------");
		//sample.passByValue();
		sample.calculateNumbers(1,2,3,4,5);
	}

	// 

	public MemberDTO getMemberDTO() {
		MemberDTO dto = new MemberDTO();
		//
		return dto;
	}

	public void makeMemberObject() {
		MemberDTO dto1 = new MemberDTO();
		MemberDTO dto2 = new MemberDTO("Sangmin");
		MemberDTO dto3 = new MemberDTO("Sangmin", "010XXXXYYYY");
		MemberDTO dto4 = new MemberDTO("Sangmin", "010XXXXYYYY",
				"god@godofjava.com");
	}

	public void print(int data) {
	}

	public void print(String data) {
	}

	public void print(int intData, String stringData) {
	}

	public void print(String stringData, int intData) {
	}

	public int intReturn() {
		int returnInt = 0;

		return returnInt;
		// returnInt++;// Compile error
	}

	public int intReturn2() {
		int returnInt = 0;
		if (returnInt == 0) {
			return ++returnInt;
		} else {
			return --returnInt;
		}

	}

	public int[] intArrayReturn() {
		int returnArray[] = new int[10];
		return returnArray;
	}

	public String stringReturn() {
		String returnString = "Return value";
		return returnString;
	}

	public void checkMemberDTOName() {
		MemberDTO dto1 = new MemberDTO("Sangmin");
		System.out.println(dto1.name);
		MemberDTO dto2 = new MemberDTO("Sungchoon");
		System.out.println(dto1.name);
	}

	public void makeStaticBlockObject() {
		System.out.println("data="+StaticBlock.getData());

		System.out.println("Creating block1");
		StaticBlock block1 = new StaticBlock();
		System.out.println("Created block1");
		System.out.println("Creating block2");
		StaticBlock block2 = new StaticBlock();
		System.out.println("Created block2");

		System.out.println("data="+StaticBlock.getData());
	}

	public void callPassByValue() {
		int a = 10;
		String b = "b";
		MemberDTO member = new MemberDTO("myung cheol");
		passByValue(a, b, member);
		System.out.println("callPassByValue 메쏘드 결과 !!! ");
		System.out.println("a=" + a);
		System.out.println("b=" + b);
		System.out.println("member.name=" + member.name);
	}

	public void passByValue(int a, String b, MemberDTO member) {
		a = 20;
		b = "z";
		member = new MemberDTO("hyejin");
		//member.name="hyejin";
		System.out.println("passByValue 메쏘드 결과 !!! ");
		System.out.println("a=" + a);
		System.out.println("b=" + b);
		System.out.println("member.name=" + member.name);
	}

	public void calculateNumbers(int... numbers) {
		int total = 0;
		for (int number : numbers) {
			total += number;
		}
		System.out.println("Total=" + total);
	}

}
