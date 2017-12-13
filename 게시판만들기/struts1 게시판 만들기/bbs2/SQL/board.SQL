create table jsp_board(
	idx number primary key,
	writer varchar2(25),
	email varchar2(30),
	homepage varchar2(50),
	pwd varchar2(20),
	subject varchar2(200),
	content varchar2(2000),
	writedate date,
	readnum number,
	filename varchar2(200),
	filesize number,
	refer number,
	lev number,
	sunbun number
);

create sequence scott.jsp_board_idx 
	increment by 1
	start with 1
	nocache;

//꼬리글 테이블
create table reply(
	no number primary key,
	writer varchar2(25),
	pwd varchar2(20),
	content varchar2(1000),
	writedate date,
	readnum number,
	reply_idx_fk references jsp_board(idx)
);

create sequence scott.reply_no
increment by 1
start with 1
nocache;