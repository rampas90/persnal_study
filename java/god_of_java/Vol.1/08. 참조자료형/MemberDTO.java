public class MemberDTO {
	public String name;
	//public static String name;
	public String phone;
	public String email;

	/**
	 * 아무 정보도 모를때
	 */
	public MemberDTO() {
	}

	/**
	 * 이름만 알때
	 * 
	 * @param name
	 */
	public MemberDTO(String name) {
		this.name = name;
	}

	/**
	 * 이름과 전화번호만 알 때
	 * 
	 * @param name
	 * @param phone
	 */
	public MemberDTO(String name, String phone) {
		this.name = name;
		this.phone = phone;
	}

	/**
	 * 모든 정보를 알고 있을 
	 * 
	 * @param name
	 * @param phone
	 * @param email
	 */
	public MemberDTO(String name, String phone, String email) {
		this.name = name;
		this.phone = phone;
		this.email = email;
	}

	public static void main(String[] args) {
		MemberDTO.staticMethod();
	}

	public static void staticMethod() {
		System.out.println("This is a static method.");
		//System.out.println(name);// name은 인스턴스 변수이므로 컴파일에러가 난다.
	}

}
