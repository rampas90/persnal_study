package my.bbs2;

import java.util.*;

import javax.servlet.http.*;

import my.bbs2.BbsDAO;
import my.bbs2.BbsDTO;
import my.bbs2.BbsManager;
import my.bbs2.ReplyDTO;
import my.bbs2.controller.form.BbsEditForm;

import com.oreilly.servlet.*;
import java.sql.*;


public class BbsManager {
	
	static private BbsManager instance = new BbsManager();
	
	private BbsManager(){
		
	}

	public static BbsManager getInstance(){
		return instance;
	}
	
	
	public int writeOk(MultipartRequest mr)
		throws SQLException{
		
		BbsDAO dao = new BbsDAO();
		int n = dao.writeOk(mr);
		return n;
		
	}
	
	
	public ArrayList<BbsDTO> listAll(int cpage, int pageSize)
		throws SQLException{
		BbsDAO dao = new BbsDAO();
		ArrayList<BbsDTO> arr=dao.listAll(cpage,pageSize);
		return arr;
	}

	public int getTotalGulCount()
		throws SQLException{
		BbsDAO dao = new BbsDAO();
		return dao.getTotalGulCount();
	}

	public BbsDTO viewContent(String idx)
		throws SQLException{
		BbsDAO dao = new BbsDAO();
		return dao.viewContent(idx);
	}

	public boolean getReadnum(String idx)
		throws SQLException{
		BbsDAO  dao = new BbsDAO();
		return dao.getReadnum(idx);
	}
	
	public int deleteOk(String idx, String pwd)
		throws SQLException{
		return new BbsDAO().deleteOk(idx, pwd);
	}
	
	public BbsDTO edit(String idx)
		throws SQLException{
		return new BbsDAO().edit(idx);
	}
	

	public int editOk(BbsEditForm form)
		throws SQLException{
		return new BbsDAO().editOk(form);
	}
	
	public int rewriteOk(MultipartRequest mr)
		throws SQLException{
		return new BbsDAO().rewriteOk(mr);
	}

	public int replySave(String idx, String writer, String content, String pwd)
		throws SQLException{
		return new BbsDAO().replySave(idx,writer,content, pwd);
	}
	
	public ArrayList<ReplyDTO> replyList(String idx)
		throws SQLException{
		return new BbsDAO().replyList(idx);
	}
	public int replyDelPwd(String no, String pwd)
		throws SQLException{
		return new BbsDAO().replyDelPwd(no,pwd);
	}

}

