package my.bbs2.controller.action;

import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.actions.*;

import my.bbs2.*;
import my.bbs2.controller.form.BbsEditForm;
import org.apache.commons.beanutils.*;
import java.util.*;


public class BbsAction extends DispatchAction{
	
	/**	1. 글쓰기 양식 불러오기. */
	public ActionForward write(ActionMapping map, ActionForm form,HttpServletRequest req, HttpServletResponse res)throws Exception{
		System.out.println("method : "+req.getParameter("method"));
		ActionForward af = map.findForward("fw-bbs-write");
		
		return af;
	}
	
	/**
		DispatchAction으로는 구현할 수 없다.
		DispatchAction은 GET방식
		파일 업로드는 POST방식
		따라서 별도의 Action으로 제어
		---> BbsWriteOkAction
	*/
	public ActionForward writeOk(ActionMapping map, ActionForm form,HttpServletRequest req, HttpServletResponse res) throws Exception{
		return null;
	}
	
	/**	2. 글쓰기 목록 가져오기. */
	public ActionForward list(ActionMapping map, ActionForm form,HttpServletRequest req, HttpServletResponse res) throws Exception{
		System.out.println("method : "+req.getParameter("method"));
		//현재 페이지 cp값
		String cpStr = req.getParameter("cp");
		if(cpStr == null){
			cpStr = "1";
		}
		cpStr = cpStr.trim();
		
		HttpSession session = req.getSession();
		
		String psStr = req.getParameter("ps");
		if(psStr == null){
			psStr = (String)session.getAttribute("ps");
			if(psStr == null){
				psStr = "5";
			}
		}
		
		psStr = psStr.trim();
		session.setAttribute("ps", psStr);
		
		int cpage = Integer.parseInt(cpStr);
		int pageSize = Integer.parseInt(psStr);
		
		BbsManager mgr = BbsManager.getInstance();
		java.util.ArrayList<BbsDTO> arr = mgr.listAll(cpage, pageSize);
		int totalGulCount = mgr.getTotalGulCount();
		
		req.setAttribute("bbslist", arr);
		req.setAttribute("tgc", new Integer(totalGulCount));
		req.setAttribute("cp", cpStr);
		req.setAttribute("ps", psStr);
				
		return map.findForward("fw-bbs-list");
	}
	
	/**	3. 글내용 가져오기.. */
	public ActionForward content(ActionMapping map, ActionForm form,HttpServletRequest req, HttpServletResponse res)throws Exception{
		String method = req.getParameter("method");
		System.out.println("method : "+method);
		
		String idxStr = req.getParameter("idx");
		String cpStr = req.getParameter("cp");
		
		HttpSession session = req.getSession();
		if(cpStr == null){
			cpStr = (String)session.getAttribute("cp");
			if(cpStr ==null){
				ActionForward af = new ActionForward("/bbs-list.do?method=list",true);
				return af;
			}
		}
		cpStr = cpStr.trim();
		session.setAttribute("cp", cpStr);
		
		if(idxStr == null){
			idxStr = (String)session.getAttribute("idx");
			if(idxStr==null){
				return new ActionForward("/bbs-list.do?method=list",true);
			}
		}

		idxStr = idxStr.trim();
		session.setAttribute("idx",idxStr);

		BbsManager mgr = BbsManager.getInstance();

		BbsDTO dto = mgr.viewContent(idxStr);
		
	   mgr.getReadnum(idxStr);
     
		
		ArrayList<ReplyDTO> reply = mgr.replyList(idxStr);
		
		
		if(dto == null || reply == null){
			System.out.println("[content]:dto 또는 reply  가 널");
		}else {
			req.setAttribute("gul",dto);
			req.setAttribute("reply", reply);
		}
		
		return map.findForward("fw-bbs-content");

	}
	
   /**   3. 글내용 가져오기.. */
   public ActionForward contentDown(ActionMapping map, ActionForm form,HttpServletRequest req, HttpServletResponse res)throws Exception{
      String method = req.getParameter("method");
      System.out.println("method : "+method);
      
      String idxStr = req.getParameter("idx");
      String cpStr = req.getParameter("cp");
      
      HttpSession session = req.getSession();
      if(cpStr == null){
         cpStr = (String)session.getAttribute("cp");
         if(cpStr ==null){
            ActionForward af = new ActionForward("/bbs-list.do?method=list",true);
            return af;
         }
      }
      cpStr = cpStr.trim();
      session.setAttribute("cp", cpStr);
      
      if(idxStr == null){
         idxStr = (String)session.getAttribute("idx");
         if(idxStr==null){
            return new ActionForward("/bbs-list.do?method=content",true);
         }
      }

      idxStr = idxStr.trim();
      session.setAttribute("idx",idxStr);

      BbsManager mgr = BbsManager.getInstance();

      BbsDTO dto = mgr.viewContent(idxStr);    
      
      ArrayList<ReplyDTO> reply = mgr.replyList(idxStr);
      
      
      if(dto == null || reply == null){
         System.out.println("[content]:dto 또는 reply  가 널");
      }else {
         req.setAttribute("downs",dto);
         req.setAttribute("reply", reply);
      }
      
      return map.findForward("fw-bbs-content-down");

   }	
	
	/**	3_2. 꼬리글 달기.. */
	public ActionForward replyOk(ActionMapping map, ActionForm form,HttpServletRequest req, HttpServletResponse res)throws Exception{
		String method = req.getParameter("method");
		System.out.println("method : "+method);
		
		String writer = req.getParameter("reply_writer");
		String content = req.getParameter("reply_content");
		String pwd = req.getParameter("reply_pwd");
		
		if(writer!=null)writer = writer.trim();
		if(content!=null)content = content.trim();
		if(pwd!=null)pwd = pwd.trim();
		
		HttpSession ses = req.getSession();
		String idxStr = (String)ses.getAttribute("idx");
		
		if(idxStr == null){
			String path = "bbs-list.do?method=list";
			return new ActionForward(path,true);
		}
		
		idxStr = idxStr.trim();
		BbsManager mgr = BbsManager.getInstance();
		int result = mgr.replySave(idxStr, writer, content, pwd);
		
		String path = "/bbs-content.do?method=content";
		return new ActionForward(path,true);
	}
	
	/**	3_2. 꼬리글 삭제.. */
	public ActionForward replyDelOk(ActionMapping map, ActionForm form,HttpServletRequest req, HttpServletResponse res)throws Exception{
		
		System.out.println("method : "+req.getParameter("method"));
		String no = req.getParameter("no");
		String pwd = req.getParameter("delPwd");
		
		BbsManager mgr= BbsManager.getInstance();
		int result = mgr.replyDelPwd(no, pwd);
		
		String msg="",url="";
		if(result >0){
			msg = "꼬리글 삭제 성공";
			url = "bbs-content.do?method=content";
		}else{
			msg = "꼬리글 삭제 실패";
			url = "javascript:history.go(-1)";
		}
			
		req.setAttribute("bbs-msg", msg);
		req.setAttribute("bbs-url", url);
		
		return map.findForward("gb-bbs-msg");		
	}
	
	/**	4. 본 문장 삭제.. 
	 *  bbs-delete.do--->ForwardAction--->bbs_delete.jsp
	 *  bbs-deleteOk.do--->BbsAction(deleteOk())
	 *  ---> gb-bbs-msg로 forward
	 * */
	public ActionForward deleteOk(ActionMapping map, ActionForm form,HttpServletRequest req, HttpServletResponse res)throws Exception{
		
		System.out.println("method : "+req.getParameter("method"));
		
		String idxStr = (String)req.getAttribute("idx");
		HttpSession ses = req.getSession();
		
		if(idxStr == null){
			idxStr = (String)ses.getAttribute("idx");
			if(idxStr==null){
				String path ="bbs-list.do?method=list";
				return new ActionForward(path, true);
			}
		}
		
		idxStr = idxStr.trim();

		String pwd = req.getParameter("pwd");
		
		BbsManager mgr = BbsManager.getInstance();
		int result = mgr.deleteOk(idxStr, pwd);
		
		String msg="", url="";
		if(result>0){
			msg="삭제 성공";
			url="bbs-list.do?method=list";
		}else{
			msg="글 삭제 실패";
			url="javascript:history.go(-1)";
		}
		
		req.setAttribute("bbs-msg", msg);
		req.setAttribute("bbs-url", url);
		
		return map.findForward("gb-bbs-msg");
	}
	
	/**	 5. 답변글 달기	 * */
	public ActionForward rewrite(ActionMapping map, ActionForm form,HttpServletRequest req, HttpServletResponse res)throws Exception{
		
		System.out.println("method : "+req.getParameter("method"));
		
		HttpSession ses = req.getSession();
		
		String idxStr = (String)ses.getAttribute("idx");
		
		if(idxStr==null){
			
			String path = "bbs-list.do?method=list";
			return new ActionForward(path,true);
		}else{
			return map.findForward("fw-bbs-rewrite");			
		}
	}
	
	/**	
	 *  5. 답글 달기 성공 	
	 * */
	public ActionForward rewriteOk(ActionMapping map, ActionForm form,HttpServletRequest req, HttpServletResponse res)throws Exception{
		
		System.out.println("method : "+req.getParameter("method"));
		return null;
	}
	
	/**	
	 *  6 글 내용 수정	 
	 * */
	public ActionForward edit(ActionMapping map, ActionForm form,HttpServletRequest req, HttpServletResponse res)throws Exception{
		String method = req.getParameter("method");
		System.out.println("method : "+method);
		HttpSession ses = req.getSession();
		String idxStr = (String)ses.getAttribute("idx");
		
		if(idxStr == null){
			String path = "/bbs-list.do?method=list";
			return new ActionForward(path,true);
		}
		BbsManager mgr = BbsManager.getInstance();
		BbsDTO dto = mgr.edit(idxStr);
		req.setAttribute("gul", dto);
		
		return map.findForward("fw-bbs-edit");
								
	}
	
	/**	
	 	6_1 글 내용 수정 완료 - editOk
	 * */
	public ActionForward editOk(ActionMapping map, ActionForm form,HttpServletRequest req, HttpServletResponse res)throws Exception{
		String method = req.getParameter("method");
		System.out.println("method: "+method);

		BbsEditForm bf = (BbsEditForm)form;
		
		BbsManager mgr = BbsManager.getInstance();
		
		int result = mgr.editOk(bf);
		
		String msg="", url="";
		if(result > 0){
			msg = "수정 성공";
			url = "bbs-content.do?method=content";
		}else{
			msg = "수정 실패";
			url = "javscript:history.go(-1)";
		}
		
		req.setAttribute("bbs-msg", msg);
		req.setAttribute("bbs-url", url);
		
		return map.findForward("gb-bbs-msg");
	}
		
		
}
