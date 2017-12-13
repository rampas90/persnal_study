
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
/*
		�޽����� ���� ����ΰ�?
*/
public class Server_0 {
	public static void main(String[] args) throws IOException{
		Server_0 server = new Server_0();
		server.boot();
	}
	private void boot() throws IOException{
		// 8000�� ��Ʈ�� �� �� Socket ���ῡ ���� ���� ��û ������ ǥ�� ��¿� ǥ���Ѵ�.
		serverSocket = new ServerSocket(8000);
		Socket socket = serverSocket.accept();
		InputStream in = socket.getInputStream();
		OutputStream out = socket.getOutputStream();
		/*
			intputStream�� read�޼��尡 -1�� ��ȯ�ϸ� �����Ѵ�.
			intputStream�� read�޼ҵ�� ��Ʈ���� ���� �ٴٸ� ���, �� ���Ͽ��� ���� ��Ʈ���� ������ ������ ��쿡 -1�� ��ȯ�Ѵ�.
			���� �� �������� [x] ��ư�� ����� ������ �����ϸ� ���������� ������ while �������� Ż���Ѵ�.

			�̿� ���� ���� ������ Ŭ���̾�Ʈ -���� �������� ����ϱⰡ ��ƴ�.
			�޽����� ���� ���� ����� �ľ��� ���ۿ� ���ٸ�, ����� ����� �ʿ��� �� ��û�� ���� ������ ���� �� ����(�̹� ��������)
			�����̴�.
			���� ���� �����̳ʴ� ��Ʈ���� ����� Socket���� �� ����Ʈ�� �о���̸鼭 ��� �������� �б⸦ �ߴ��ϰ�
			���ݱ��� ���� ������ ������� ��û �޽����� �������� �Ǵ��ؾ� �Ѵ�.
			HTTP�������ݿ� ���� �̷� ���¸� �м��ϴ� �ڵ带 HTTP ���� ����� �Ѵ�.
		*/
		int oneInt = -1;
		while(-1 != (oneInt = in.read())){
			System.out.print((char)oneInt);
		}

		out.close();
		in.close();
		socket.close();
	}
	private ServerSocket serverSocket;    
}
