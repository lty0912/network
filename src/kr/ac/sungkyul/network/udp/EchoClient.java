package kr.ac.sungkyul.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class EchoClient {
	private static final String SENDER_IP = "61.79.155.136";
	private static final int SENDER_PORT = 1000;
	private static final int BUFFER_SIZE = 1000;

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		DatagramSocket socket = null;

		try {
			// 1. 소켓 생성
			socket = new DatagramSocket();

			while (true) {

				// 2. 사용자 입력값 받음
				System.out.print(">>");
				String message = scanner.nextLine();
				if ("quit".equals(message)) {
					break;
				}
				byte[] sendData = message.getBytes(StandardCharsets.UTF_8);

				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
						new InetSocketAddress(SENDER_IP, SENDER_PORT));
				socket.send(sendPacket);

				// 3. 데이터 수신
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
				socket.receive(receivePacket);

				String data = new String(receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.UTF_8);
				System.out.println("<<" + data);
			}

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null && socket.isClosed() == false) {
				socket.close();
			}
			if (scanner != null) {
				scanner.close();
			}
		}
	}

}
