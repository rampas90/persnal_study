package my.db;

import javax.naming.*;
import javax.sql.*;
import java.sql.*;

public class ConnectionControl {
	static DataSource ds;
	static{
		try{
			InitialContext ctx = new InitialContext();
			Context envCtx = (Context)ctx.lookup("java:/comp/env");
			ds = (DataSource)envCtx.lookup("java:comp/env/jdbc/struts");
			System.out.println("data lookup 성공!");
		}catch(NamingException e){
			System.out.println("Lookup 실패!");
		}
	} //static block ------------

	public static Connection getConnection()
		throws SQLException{
		return ds.getConnection();
	} //getConnection() ------------------
} //////////////////////////////////
