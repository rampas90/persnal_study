import java.awt.Graphics;   

//import ��Ű����(�ҹ���).Ŭ������(�빮�ڷ� ����)
//import java.awt.*;
//java.awt��Ű���� ���Ե� ��� Ŭ������ ������ ���ڴٴ� �ǹ�

import java.awt.*;
import java.applet.*;
public class MyApplet extends Applet
{
	public void paint(Graphics g)
	{
		g.drawString("Hello Applet",50,50);
		//x=50, y=50�� ������ Hello Applet �̶� ���ڿ��� �׷���
		g.setColor(Color.red);		
		g.drawString("�ȳ��ϼ���?", 50,70);
		g.setColor(Color.blue);
		g.drawRect(50,90,100,100);
	}
}