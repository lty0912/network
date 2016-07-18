package kr.ac.sungkyul.network.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class EchoClient {

	private static final String SERVER_IP = "172.16.105.30";
	private static final int SERVER_PORT = 1000;

	public static void main(String[] args) {
		Socket socket = null;
		Scanner scanner = new Scanner(System.in);

		try {
			socket = new Socket();

			InetSocketAddress serverSocketAddress = new InetSocketAddress(SERVER_IP, SERVER_PORT);
			socket.connect(serverSocketAddress);

			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();

			while (true) {

				System.out.print(">>");
				String data = scanner.nextLine();
				os.write(data.getBytes("utf-8"));

				byte[] buffer = new byte[256];
				int readBytes = is.read(buffer);
				if (readBytes <= -1) {
					System.out.println("[client] closed by server.");
					return;
				}

				data = new String(buffer, 0, readBytes, "utf-8");
				if ("exit".equals(data)) {
					System.out.println("프로그램을 종료합니다.");
					return;
				}
				System.out.println("<<" + data);

			}

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
				if (scanner != null) {
					scanner.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
