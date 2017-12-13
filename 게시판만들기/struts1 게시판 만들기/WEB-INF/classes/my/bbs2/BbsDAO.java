package my.bbs2;
// Data Access Object : 업무처리 로직(비지니스 로직)
// 을 가지는 클래스... DB에 insert, delete, update, select 등의 업무처리 로직

import java.sql.*;

import javax.naming.*;
import javax.sql.*;
import java.util.*;
import java.io.*;

import javax.servlet.ServletContext;
import javax.servlet.http.*;

import my.bbs2.BbsDTO;
import my.bbs2.ReplyDTO;
import my.bbs2.controller.form.BbsEditForm;

import com.oreilly.*;
import com.oreilly.servlet.*;
import com.oreilly.servlet.multipart.*;

public class BbsDAO {
	static DataSource ds;
			Connection con;
			 
			
	// #1. 글쓰기 관련 -writeOk 관련(bbs_writeOk.jsp)
	// 1_1. 글 그룹번호(refer)최대값 가져오기.
	PreparedStatement ps1, ps1_1;
	ResultSet rs1, rs1_1;
	
	
	// #2. 글목록 관련 -listAll 관련(bbs_list.jsp)
	PreparedStatement ps2;
	ResultSet rs2;
	
	// #2_1. 총 게시물 수 - getTotalGulCount관련
	PreparedStatement ps2_1;
	ResultSet rs2_1;
	
	// #3. 글내용 보기 관련 --viewContent(bbs_content.jsp)
	PreparedStatement ps3, ps3_1;
	ResultSet rs3;
	
	// #4. 글 삭제 관련 --deleteOk
	PreparedStatement ps4, ps4_1;
	ResultSet rs4;
	
	// #5. 5_1 글 편집 성공 여부 관련 --- editOk
	PreparedStatement ps5, ps5_1;
	ResultSet rs5;
	
	// #6, 6_1, 6_2 답글 쓰기 관련 --- rewriteOk
	PreparedStatement ps6, ps6_1, ps6_2;
	ResultSet rs6;
	
	// #7 꼬리말 달기 관련 ---replySave
	PreparedStatement ps7;
	
	
	 
		
	public BbsDAO(){
		
	}
	
	static{

		try{
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			ds = (DataSource)envCtx.lookup("jdbc/struts");
			System.out.println("DataSource 룩업 성공!");
					
		}catch (NamingException e){

			e.printStackTrace();
		}
	}
	/** 
		1. 글쓰기 -- writeOk()
	*/
	public int writeOk(MultipartRequest mr)
		throws SQLException{
		
		String writeOk_sql = "insert into jsp_board (writer, email, homepage, pwd, subject, content, writedate, readnum, filename, filesize, refer, lev, sunbun) values("+
				"?,?,?,?,?,?," +
				"now(),?,?,?,?,?,?)";
		
		try{
			
			con = ds.getConnection();
			ps1 = con.prepareStatement(writeOk_sql);
			
			String writer = mr.getParameter("writer");
			String email = mr.getParameter("email");
			String homepage = mr.getParameter("hpmepage");
			String pwd = mr.getParameter("pwd");
			String subject = mr.getParameter("subject");
			String content = mr.getParameter("content");
			
			int readnum = 0;
			
			String filename = mr.getFilesystemName("filename");
			
			String originFilename = mr.getOriginalFileName("filename");
			
			System.out.println("filename : "+filename+" originFilename : "+originFilename);
			
			
			long filesize = 0;
			
			if(filename == null){
				
				 filename ="";
			}else{
				filename = filename.trim();
				File f = mr.getFile("filename");
				
				if(f!=null)
					filesize = f.length();
				else
					filesize =0;
			}
			
      // 답변형 게시판에서 사용할 필드
		// 답변형 추가 - 글 그룹번호 증가 로직
			int referMax = getMaxRefer();
				
			int refer = referMax+1;
			
			int lev = 0;
			int sunbun = 0;

			
			ps1.setString(1, writer);
			ps1.setString(2,email);
			ps1.setString(3,homepage);
			ps1.setString(4,pwd);
			ps1.setString(5,subject);
			ps1.setString(6,content);
			
			ps1.setInt(7,readnum);
			ps1.setString(8,filename);
			ps1.setLong(9,filesize);
			ps1.setInt(10, refer);
			ps1.setInt(11,lev);
			ps1.setInt(12,sunbun);
			
			int n = ps1.executeUpdate();
			return n;
			
		}finally{
			
			if(ps1!=null)ps1.close();
			if(rs1!=null)rs1.close();
			if(con!=null)con.close();
				
		}
	}

/*
 	# 1_1 refer(같은 글집단)의 최대값(max)구하기
*/
	public int getMaxRefer()
		throws SQLException{
		
		String maxRefer_sql = "select max(refer) from jsp_board";
		Connection con2 = null;
		
		try{
		
			con2 = ds.getConnection();
			ps1_1 = con2.prepareStatement(maxRefer_sql);
			rs1_1 = ps1_1.executeQuery();
			
			if(rs1_1.next()){
				int maxRefer = rs1_1.getInt(1);
				return maxRefer;
			}
			
			return -1;	
			
		}finally{
			
			if(rs1_1 != null)rs1_1.close();
			if(ps1_1 != null)ps1_1.close();
			if(con2 != null)con2.close();
		}
	}
	
	/** 2. 글 목록 ---listAll() */
	public ArrayList<BbsDTO> listAll(int cpage, int pageSize)
		throws SQLException{
		
		String list_sql = "select * from jsp_board order by idx desc";
		String list_sql2 = "select * from jsp_board order by refer desc, sunbun asc";

		ArrayList<BbsDTO> arr = new ArrayList<BbsDTO>();
		
		try{
			con = ds.getConnection();
			ps2 = con.prepareStatement(list_sql2, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			//위의 타입을 지정 안하면rs.next()외엔 수행하지 않음..
			rs2 = ps2.executeQuery();
			
			//특정한 행으로 커서를 이동...
			
			int startNo = (cpage-1)*pageSize+1;
			boolean isRs = rs2.absolute(startNo);
					
			int count = 0;
			
			if(isRs){
				do{
							
				if(count++ == pageSize)
					break;
				
				int idx = rs2.getInt(1);
				String writer = rs2.getString(2);
				String email = rs2.getString(3);
				String homepage = rs2.getString(4);
				String pwd = rs2.getString(5);
				String subject = rs2.getString(6);
				String content = rs2.getString(7);
				
				java.sql.Date writedate = rs2.getDate(8);
				int readnum = rs2.getInt(9);
				String filename = rs2.getString(10);
				long filesize = rs2.getLong(11);
				int refer = rs2.getInt(12);
				int lev = rs2.getInt(13);
				int sunbun = rs2.getInt(14);
				
				// BbsDTO 생성자 ArrayList에 초기화
				BbsDTO dto = new BbsDTO(idx,writer,email,homepage,
													pwd,subject,content,writedate,readnum,
													filename,filesize,refer,lev,sunbun); 
				arr.add(dto);
				
				}while(startNo >0 && rs2.next()); //do~while --------
			}
				return arr;
			
			
		}finally{
			if(rs2!=null) rs2.close();
			if(ps2!=null) ps2.close();
			if(con!=null) con.close();
		}
	}
	
/*
 	2_1 총 게시물 수 구하기 관련 	
*/
	public int getTotalGulCount()
		throws SQLException{
		String total_sql = "select count(*) cnt from jsp_board";
		try{
			con = ds.getConnection();
			ps2_1 = con.prepareStatement(total_sql);
			rs2_1 = ps2_1.executeQuery();
			if(rs2_1.next()){
				int cnt = rs2_1.getInt("cnt");
						 //rs2_1.getInt(1);
				
				return cnt;
				
			}else
				return -1;
			
		}finally{
			if(rs2_1 != null)
				rs2_1.close();
			if(ps2_1 != null)
				ps2_1.close();
			if(con!=null)
				con.close();
		}

	}
/*
 	3. 글내용 보기
*/
	public BbsDTO viewContent(String idx)
		throws SQLException{
		
		int idx2 = Integer.parseInt(idx.trim());
		
		String content_sql = "select * from jsp_board where idx=?";
		try{
			con = ds.getConnection();
			ps3 = con.prepareStatement(content_sql);
			ps3.setString(1,idx);
			rs3 = ps3.executeQuery();
			
			if(rs3.next()){
				String writer = rs3.getString(2);
				String email = rs3.getString(3);
				String homepage = rs3.getString(4);
				String pwd = rs3.getString(5);
				String subject = rs3.getString(6);
				String content = rs3.getString(7);
				
				java.sql.Date writedate = rs3.getDate(8);
				int readnum = rs3.getInt(9);
				String filename = rs3.getString(10);
				long filesize = rs3.getLong(11);
				
				int refer = rs3.getInt(12);
				int lev = rs3.getInt(13);
				int sunbun = rs3.getInt(14);
				
				BbsDTO dto =
					new BbsDTO(idx2,writer,email,homepage,pwd,subject,content,writedate, 
							readnum, filename,filesize,refer,lev,sunbun);
	
				return dto;
	
			}else{
				return null;
			}
			
		}finally{
			if(rs3!=null)rs3.close();
			if(ps3!=null)ps3.close();
			if(con!=null)con.close();
		}
		
	}
	
	/*
	 * 3_1. 글내용(viewContent)관련
	 *   	   조회수 (readnum) 증가 메소드
	 */
	public boolean getReadnum(String idx)
		throws SQLException{

		String readnum_sql = "update jsp_board set readnum = readnum+1 where idx=?";
		try{
			con = ds.getConnection();
			ps3_1 = con.prepareStatement(readnum_sql);
			ps3_1.setString(1, idx);
			int n =ps3_1.executeUpdate();
			
			if(n>0){
				return true;
			}else
				return false;
			
		}finally{
			if(ps3_1 != null) ps3_1.close();
			if(con!=null) con.close();
		}
		
	}
	
/*
 	#4. 글 삭제 관련 --deleteOk
*/	
	public int deleteOk(String idx, String pwd)
		throws SQLException{
		
		String selPwd_sql ="select pwd from jsp_board where idx=?";
		String delOk_sql ="delete from jsp_board where idx=?";
		
		//꼬리말 테이블을 삭제한 후 부모글을 삭제
		String replyDel_idx_sql = "delete from reply where reply_idx_fk=?";
		PreparedStatement ps4_2 = null;
		try{
			con = ds.getConnection();
			ps4 = con.prepareStatement(selPwd_sql);
			ps4.setString(1,idx);
			rs4 = ps4.executeQuery();
			
			
			if(rs4.next()){
				String dbPwd  =rs4.getString("pwd");
				
				System.out.println("dbPwd = "+dbPwd);
				System.out.println("pwd = "+pwd);
				System.out.print(idx);
				
				if(dbPwd!=null)
					dbPwd = dbPwd.trim();
				
					if(dbPwd.equals(pwd)){
						
						con.setAutoCommit(false); //꼬리글이 다 삭제된 다음에 삭제
						// 꼬리글 삭제
						ps4_2 = con.prepareStatement(replyDel_idx_sql);
						ps4_2.setString(1, idx);
						ps4_2.executeUpdate();
					
						
						/// 원래글 삭제
						ps4_1 = con.prepareStatement(delOk_sql);
						ps4_1.setString(1,idx);
						
						int n = ps4_1.executeUpdate();
						
						if(n>0)
							con.commit();
						else
							con.rollback();
											
						return n; 
						
					}else
						//비밀번호 불일치시
						return -1;
				
			}else
				return -1;
			
		}finally{
	
			con.setAutoCommit(true);
	
			if(rs4!= null) rs4.close();
			if(ps4!=null) ps4.close();
			if(ps4_1!=null) ps4_1.close();
			if(ps4_2!=null)ps4_2.close();
			if(con!=null)con.close();
		}

	}
	
/*
  	5. 글 내용 편집 관련
*/
	public BbsDTO edit(String idx)
		throws SQLException{ 
		
		return this.viewContent(idx);
	}
	
/*
  	5_1. 글 편집 성공 여부 관련

	public int editOk(HttpServletRequest req)
		throws SQLException{
		
		String selPwd_sql = "select pwd from jsp_board where idx=?";
		String editOk_sql = "update jsp_board set writer=?,"+
									"email=?,homepage=?,subject=?,content=? where idx=?";
		try{

			String idx = req.getParameter("idx");
			String writer = req.getParameter("writer");
			String pwd = req.getParameter("pwd");
			String subject = req.getParameter("subject");
			String content  =req.getParameter("content");
			String homepage = req.getParameter("homepage");
			String email = req.getParameter("email");
			
				
			con = ds.getConnection();
			ps5 = con.prepareStatement(selPwd_sql);
			ps5.setString(1,idx);
			rs5 = ps5.executeQuery();
			
			if(rs5.next()){
				String dbPwd = rs5.getString("pwd");
				if(dbPwd.equals(pwd.trim())){

					ps5_1 = con.prepareStatement(editOk_sql);
					ps5_1.setString(1, writer);
					ps5_1.setString(2,email);
					ps5_1.setString(3,homepage);
					ps5_1.setString(4,subject);
					ps5_1.setString(5,content);
					ps5_1.setString(6,idx);
					
					int n = ps5_1.executeUpdate();
					
					return n;
				}
			}
			return -1;
			
		}finally{
			if(rs5 != null)rs5.close();
			if(ps5 != null)ps5.close();
			if(ps5_1 != null)ps5_1.close();
		}
	}
*/

/*
  	5_2. 글 편집 성공 여부 관련
*/
	public int editOk(BbsEditForm req)
		throws SQLException{
		
		String selPwd_sql = "select pwd from jsp_board where idx=?";
		String editOk_sql = "update jsp_board set writer=?,"+
									"email=?,homepage=?,subject=?,content=? where idx=?";
		try{
			String idx = req.getIdx();
			String writer = req.getWriter();
			String pwd = req.getPwd();
			String subject = req.getSubject();
			String content  =req.getContent();
			String homepage = req.getHomepage();
			String email = req.getEmail();			
				
			con = ds.getConnection();
			ps5 = con.prepareStatement(selPwd_sql);
			ps5.setString(1,idx);
			rs5 = ps5.executeQuery();
			
			if(rs5.next()){
				String dbPwd = rs5.getString("pwd");
				if(dbPwd.equals(pwd.trim())){
					
					ps5_1 = con.prepareStatement(editOk_sql);
					ps5_1.setString(1, writer);
					ps5_1.setString(2,email);
					ps5_1.setString(3,homepage);
					ps5_1.setString(4,subject);
					ps5_1.setString(5,content);
					ps5_1.setString(6,idx);
					
					int n = ps5_1.executeUpdate();
					
					return n;
				}
			}
			return -1;
			
		}finally{
			if(rs5 != null)rs5.close();
			if(ps5 != null)ps5.close();
			if(ps5_1 != null)ps5_1.close();
		}
	}

/*
  	6. 답글 쓰기 관련
*/
	public int rewriteOk(MultipartRequest mr)
		throws SQLException{
		//1.사용자가 입력한 값 뽑아오기....
		String idx = mr.getParameter("idx");
		String subject = mr.getParameter("subject");
		String content = mr.getParameter("content");
		String writer = mr.getParameter("writer");
		String email = mr.getParameter("emial");
		String homepage = mr.getParameter("homepage");
		String pwd = mr.getParameter("pwd");
		String filename = mr.getFilesystemName("filename");
		String origin = mr.getOriginalFileName("filename");
		long filesize = 0;
		
		if(filename == null || filename.trim().equals("")){
			filename="";
		}else{
			filename = filename.trim();
			filesize = mr.getFile("filename").length();
		}
		
		String refer_lev_sunbun_sql
		 = "select refer, lev, sunbun from jsp_board where idx=?";
		
		String update_sunbun_sql 
		= "update jsp_board set sunbun = sunbun+1 where sunbun >? and refer=?";
		
		String rewriteOk_sql = "insert into jsp_board ( writer, email, homepage, pwd, subject, content, writedate, readnum, filename, filesize, refer, lev, sunbun)"
		      + " values(?,?,?,?,?,?,now(),"+
									"?,?,?,?,?,?)";
			
		try{ 
			con = ds.getConnection();
			ps6 = con.prepareStatement(refer_lev_sunbun_sql);
			
			ps6.setString(1, idx);
			rs6 = ps6.executeQuery();
			
			int lev=0, refer=0,sunbun=0;
			
			if(rs6.next()){
				refer = rs6.getInt("refer");
				lev = rs6.getInt("lev");
				sunbun = rs6.getInt("sunbun");
				//같은 글그룹의 순번을 업데이트;
				ps6_1 = con.prepareStatement(update_sunbun_sql);
				ps6_1.setInt(1, sunbun);
				ps6_1.setInt(2, refer);
				ps6_1.executeUpdate();
				
				//답글 insert 부분
				ps6_2 = con.prepareStatement(rewriteOk_sql);
				ps6_2.setString(1, writer);
				ps6_2.setString(2, email);
				ps6_2.setString(3, homepage);
				ps6_2.setString(4, pwd);
				ps6_2.setString(5, subject);
				ps6_2.setString(6, content);
				
				ps6_2.setInt(7, 0); //조회수 처음에 0
				ps6_2.setString(8, filename);
				ps6_2.setLong(9, filesize);
				ps6_2.setInt(10, refer);
			
				ps6_2.setInt(11, lev+1);
				
				ps6_2.setInt(12, sunbun+1);

				int n = ps6_2.executeUpdate();
				
				return n;
				
			}else{
				return -1;
			}
		
		}finally{
			if(rs6 != null)rs6.close();
			if(ps6 != null)ps6.close();
			if(ps6_1 != null)ps6_1.close();
			if(ps6_2 != null)ps6_2.close();
			if(con!=null)con.close();
		}
		
	}
/*
 	7. content에 꼬리말 쓰기 관련
*/
	public int replySave(String idx, String writer, String content, String pwd)
		throws SQLException{
		String replySave_sql ="insert into reply (writer, pwd, content, writedate, readnum, reply_idx_fk) values(?,?,?,now(),?,?)";
		
		try{
			con = ds.getConnection();
			ps7 = con.prepareStatement(replySave_sql);
			ps7.setString(1, writer);
			ps7.setString(2,pwd);
			ps7.setString(3,content);
			ps7.setInt(4,0);
			ps7.setString(5,idx);
			
			return ps7.executeUpdate();
		
		}finally{
			if(ps7!=null)ps7.close();
			if(con!=null)con.close();
		}
	}

/*
 	#7_1. 꼬리말 목록 가져오기 ----replyList()
*/	
	public ArrayList<ReplyDTO> replyList(String idx)
		throws SQLException{

		String replyList_sql = "select * from reply where reply_idx_fk =? order by no";
		PreparedStatement ps7_1 = null;
		ResultSet rs7_1 = null;
		
		try{
			con = ds.getConnection();
			ps7_1 = con.prepareStatement(replyList_sql);
			ps7_1.setString(1,idx);
			rs7_1 = ps7_1.executeQuery();
			ArrayList<ReplyDTO> arr = new ArrayList<ReplyDTO>();
			
			while(rs7_1.next()){
				int no =rs7_1.getInt(1);
				String writer = rs7_1.getString(2);
				String pwd = rs7_1.getString(3);
				String content = rs7_1.getString(4);
				java.sql.Date writedate = rs7_1.getDate(5);
				
				
				ReplyDTO rdto = new ReplyDTO(no,writer,pwd,content,writedate,0,idx);
				arr.add(rdto);
			}
			return arr;		
		}finally{
			if(rs7_1 !=null)rs7_1.close();
			if(ps7_1!=null)ps7_1.close();
			if(con!=null)con.close();
		}
	}
/*
 	#7_2. content 꼬리말 삭제 ---- replyDelPwd()
*/
	public int replyDelPwd(String no, String pwd)
		throws SQLException{
		PreparedStatement ps7_2 = null, ps7_3 = null;
		ResultSet rs7_2 = null;
		String replydel_pwd_sql = "select pwd from reply where no=?";
		String replydelok_sql = "delete from reply where no=?";
		try{
			con = ds.getConnection();
			ps7_2 = con.prepareStatement(replydel_pwd_sql);
			ps7_2.setString(1,no);
			rs7_2 = ps7_2.executeQuery();
			
			if(rs7_2.next()){
				String dbPwd = rs7_2.getString(1);
				if(pwd.equals(dbPwd)){
					ps7_3 = con.prepareStatement(replydelok_sql);
					ps7_3.setString(1, no);
					
					int n = ps7_3.executeUpdate();
					return n;
				}
			}
			return -1;
		}finally{
			if(rs7_2 != null)rs7_2.close();
			if(ps7_2 != null)ps7_2.close();
			if(ps7_3 != null) ps7_3.close();
			if(con !=null)con.close();
		}
	}
}
