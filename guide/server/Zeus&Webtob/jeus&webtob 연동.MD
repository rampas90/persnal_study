# JUSE&WEBTOB 연동

----------------

##### 1.개요

제우스6.0 웹투비4.1 윈도우설치 기준으로 작성하였습니다.


참고 URL : [egloos.zum.com/windydh/v/4618738](http://egloos.zum.com/windydh/v/4618738)

---------------

##### 2. http.m

```css
*DOMAIN
webtob1 

*NODE
HKKIM-PC	WEBTOBDIR="C:/TmaxSoft/JEUS6.0/webserver", 
            SHMKEY = 54000,
            DOCROOT="C:/TmaxSoft/WebtoB4.1/docs",
            PORT = "8080", 
            HTH = 1,
            NODENAME = "$(NODENAME)",
            ERRORDOCUMENT = "503",
            #Options="IgnoreExpect100Continue",
            LOGGING = "log1",
            ERRORLOG = "log2",
            SYSLOG = "syslog",
            JSVPORT = 9900

*SVRGROUP
htmlg		NODENAME = "HKKIM-PC", SVRTYPE = HTML
cgig		NODENAME = "HKKIM-PC", SVRTYPE = CGI
ssig		NODENAME = "HKKIM-PC", SVRTYPE = SSI
jsvg		NODENAME = "HKKIM-PC", SVRTYPE = JSV 

*SERVER
html		SVGNAME = htmlg, MinProc = 2, MaxProc = 10, ASQCount = 1 
cgi		SVGNAME = cgig, MinProc = 2, MaxProc = 10, ASQCount = 1
ssi 		SVGNAME = ssig, MinProc = 2, MaxProc = 10, ASQCount = 1 
MyGroup		SVGNAME = jsvg, MinProc = 2, MaxProc = 10

*URI
uri1		Uri = "/cgi-bin/",   Svrtype = CGI
uri2   		Uri = "/examples/",  SvrType=JSV, SvrName=MyGroup

*ALIAS
alias1  	URI = "/cgi-bin/", RealPath = "C:/TmaxSoft/WebtoB4.1/cgi-bin/"

*LOGGING
syslog		Format = "SYSLOG", FileName = "C:/TmaxSoft/WebtoB4.1/log/system.log_%M%%D%%Y%",
			Option = "sync"
log1		Format = "DEFAULT", FileName = "C:/TmaxSoft/WebtoB4.1/log/access.log_%M%%D%%Y%", 
			Option = "sync"
log2		Format = "ERROR", FileName = "C:/TmaxSoft/WebtoB4.1/log/error.log_%M%%D%%Y%", 
			Option = "sync"


*ERRORDOCUMENT
503			status = 503,
			url = "/503.html"

*EXT
htm			MimeType = "text/html", SvrType = HTML
jsp   		Mimetype ="application/jsp",  Svrtype=JSV,  SvrName=MyGroup

```

###### *NODE영역의 WEBTOBDIR 와 DOCROOT 경로를 was, web에 맞게 설정해줍니다.


----------------

##### 3.실행

jeus, webtob 서버를 각각 실행시킨 후에 연동되는지 확인해 봅니다.

----------------







