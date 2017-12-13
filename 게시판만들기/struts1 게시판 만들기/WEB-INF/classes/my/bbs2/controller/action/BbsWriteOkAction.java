package my.bbs2.controller.action;
import org.apache.struts.action.*;

import javax.servlet.ServletContext;
import javax.servlet.http.*;

import com.oreilly.servlet.*;
import com.oreilly.servlet.multipart.*;

import my.bbs2.*;

public class BbsWriteOkAction extends Action{

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/**
		 * 1. ActionForm은  파일 업로드 관련 데이터 셋팅 언됨
		 * 	업로드할 경우 request의 기능이 상실됨 -->MultipartRequest가 가져가므로  
		 * */
		HttpSession session = request.getSession();
		ServletContext ctx = session.getServletContext();		
		String upDir = ctx.getRealPath("/bbs2/Upload"); 
		String upDir2 = "C:/javaide/workspace/bbs-all/WebContent/bbs2/Upload";
		
		MultipartRequest mr = new MultipartRequest(request,upDir2,10*1024*1024,"UTF-8", new DefaultFileRenamePolicy());
		BbsManager mgr = BbsManager.getInstance();
		int result = mgr.writeOk(mr);
		
		System.out.println("upDir : "+upDir);
		System.out.println("upDir2 : "+upDir2);
		
		String msg="", url="";
		if(result > 0){
			msg = "글쓰기 성공";
			url = "bbs-list.do?method=list";
		}else{
			msg = "글쓰기 실패";
			url = "javascript:history.go(-1)";
		}
		request.setAttribute("bbs-msg", msg);
		request.setAttribute("bbs-url", url);
		
		return mapping.findForward("gb-bbs-msg");
		//return null;

	}	
	
}
