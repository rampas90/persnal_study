
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
/*
		메시지의 끝은 어디인가?
*/
public class Server_0 {
	public static void main(String[] args) throws IOException{
		Server_0 server = new Server_0();
		server.boot();
	}
	private void boot() throws IOException{
		// 8000번 포트를 연 후 Socket 연결에 대해 들어온 요청 내용을 표준 출력에 표시한다.
		serverSocket = new ServerSocket(8000);
		Socket socket = serverSocket.accept();
		InputStream in = socket.getInputStream();
		OutputStream out = socket.getOutputStream();
		/*
			intputStream의 read메서드가 -1을 반환하면 종료한다.
			intputStream의 read메소드는 스트림의 끝에 다다를 경우, 즉 소켓에서 얻은 스트림의 연결이 끊어질 경우에 -1을 반환한다.
			따라서 웹 브라우저의 [x] 버튼을 사용해 연결을 종료하면 종료조건이 만족돼 while 루프에서 탈출한다.

			이와 같은 종료 조건은 클라이언트 -서버 구조에서 사용하기가 어렵다.
			메시지의 끝을 연결 종료로 파악할 수밖에 없다면, 양방향 통신이 필요할 때 요청에 대한 응답을 보낼 수 없기(이미 끊어졌기)
			때문이다.
			따라서 서블릿 컨테이너는 스트림을 사용해 Socket에서 한 바이트씩 읽어들이면서 어느 시점에서 읽기를 중단하고
			지금까지 받은 내용을 기반으로 요청 메시지를 구성할지 판단해야 한다.
			HTTP프로토콜에 대해 이런 상태를 분석하는 코드를 HTTP 상태 기계라고 한다.
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
