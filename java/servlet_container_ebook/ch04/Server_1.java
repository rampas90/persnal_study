import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
/*
		HTTP GET ��û ó����
*/
public class Server_1 {
  public static void main(String[] args) throws IOException{
    Server_1 server = new Server_1();
    server.boot();
  }
  private void boot() throws IOException{
    serverSocket = new ServerSocket(8000);
    Socket socket = serverSocket.accept();
    InputStream in = socket.getInputStream();
    int oneInt = -1;
    byte oldByte = (byte)-1;
    StringBuilder sb = new StringBuilder();
    int lineNumber = 0;
    while(-1 != (oneInt = in.read())){
      byte thisByte = (byte)oneInt;
      if(thisByte == Server_1.LF && oldByte == Server_1.CR){
        // CRLF �� �ϼ��Ǿ���. ���� ���� CRLF ���� ��������� �� ���̴�.
        // -2�� �ƴ϶� -1�� �ϴ� ������ ���� LF �� ���ۿ� ���� ���̱� �����̴�.
        String oneLine = sb.substring(0, sb.length()-1);
        lineNumber ++;
        System.out.printf("%d: %s\n", lineNumber, oneLine);
        if(oneLine.length()<=0){
          // ������ ���� ��
          // ���� �޽��� ����� �������� ����̴�.
          System.out.println("[SYS] ������ ���� ���, �� �޽��� ����� ��");
          // �� ��Ȳ������ �޽��� �ٵ�� ó������ ����� �Ѵ�.
          break;
        }
        sb.setLength(0);
      }else{
        sb.append((char)thisByte);
      }
      oldByte = (byte)oneInt;
    }
    in.close();
    socket.close();
  }
  
  public static final byte CR = '\r';
  public static final byte LF = '\n';
  
  private ServerSocket serverSocket;
}
