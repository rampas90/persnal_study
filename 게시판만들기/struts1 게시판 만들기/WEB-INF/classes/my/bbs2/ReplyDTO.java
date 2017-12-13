package my.bbs2;

import java.sql.Date;

public class ReplyDTO {
	private int no;
	private String writer;
	private String pwd;
	private String content;
	private Date writedate;
	private int readnum;
	private String idx; //부모 테이블의 idx참조..
	
	public ReplyDTO(){
		
	}

	//인자 생성자 -------------
	public ReplyDTO(int no, String writer, String pwd, String content,
			Date writedate, int readnum, String idx) {
		super();
		this.no = no;
		this.writer = writer;
		this.pwd = pwd;
		this.content = content;
		this.writedate = writedate;
		this.readnum = readnum;
		this.idx = idx;
	}

	//setter, getter --------------
	
	public int getNo() {
		return no;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getWritedate() {
		return writedate;
	}

	public void setWritedate(Date writedate) {
		this.writedate = writedate;
	}

	public int getReadnum() {
		return readnum;
	}

	public void setReadnum(int readnum) {
		this.readnum = readnum;
	}

	public String getIdx() {
		return idx;
	}

	public void setIdx(String idx) {
		this.idx = idx;
	}
	

	
	
	
	
} //////////////////////////////////////////////////
