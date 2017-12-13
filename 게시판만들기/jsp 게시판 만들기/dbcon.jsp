<%

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	String DB_URL = "jdbc:mysql://localhost:3306/jsp_board?useDunicode=true&characterEncoding=utf8";
	String DB_USER = "root";
	String DB_PASSWORD= "apmsetup";

	Class.forName("com.mysql.jdbc.Driver");
	conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD); 

%>