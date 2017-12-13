import java.awt.Graphics;   

//import 패키지명(소문자).클래스명(대문자로 시작)
//import java.awt.*;
//java.awt패키지에 포함된 모든 클래스를 가져다 쓰겠다는 의미

import java.awt.*;
import java.applet.*;
public class MyApplet extends Applet
{
	public void paint(Graphics g)
	{
		g.drawString("Hello Applet",50,50);
		//x=50, y=50인 지점에 Hello Applet 이란 문자열을 그려라
		g.setColor(Color.red);		
		g.drawString("안녕하세요?", 50,70);
		g.setColor(Color.blue);
		g.drawRect(50,90,100,100);
	}
}