package kr.ac.sungkyul.network.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer {
	private final static int ECHO_SERVER_PORT = 1000;

	public static void main(String[] args) {

		ServerSocket serverSocket = null;
		Socket socket = null;

		try {
			serverSocket = new ServerSocket();

			InetAddress inetAddress = InetAddress.getLocalHost();
			String serverAddress = inetAddress.getHostAddress();
			InetSocketAddress inetSocketAddress = new InetSocketAddress(serverAddress, ECHO_SERVER_PORT);

			serverSocket.bind(inetSocketAddress);
			System.out.println("[server] bind: " + serverAddress + ":" + ECHO_SERVER_PORT);

			socket = serverSocket.accept();
			InetSocketAddress remoteAddress = (InetSocketAddress) socket.getRemoteSocketAddress();

			String remoteHostAddress = remoteAddress.getAddress().getHostAddress();
			int remoteHostPort = remoteAddress.getPort();
			System.out.println("[server] 연결 성공 from " + remoteHostAddress + ":" + remoteHostPort);

			try {
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();

				while (true) {

					byte[] buffer = new byte[256];
					int readbytes = is.read(buffer);

					if (readbytes <= -1) {
						System.out.println("[server] closed by client");
						return;
					}

					String data = new String(buffer, 0, readbytes, "utf-8");
					System.out.println("[server] recieved : " + data);

					os.write(data.getBytes("utf-8"));
				}
			} catch (SocketException e) {
				System.out.println("[server] 비정상적으로 클라이언트가 연결을 끊었습니다. ");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {

				if (serverSocket != null && serverSocket.isClosed() == false) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
