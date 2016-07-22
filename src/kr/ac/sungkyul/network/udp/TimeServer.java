package kr.ac.sungkyul.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeServer {
	private static final int PORT = 1000;
	private static final int BUFFER_SIZE = 1000;

	public static void main(String[] args) {
		DatagramSocket socket = null;

		try {
			// 1. 소켓생성
			socket = new DatagramSocket(PORT);
			Calendar cal = Calendar.getInstance();

			while (true) {

				// 2. 수신 대기
				System.out.println("수신대기");
				DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
				socket.receive(receivePacket);
				
				// 3. 현재 시간 송신		
				String now = cal.get(Calendar.YEAR) +"년 "+ (cal.get(Calendar.MONTH) + 1) + "월 " + cal.get(Calendar.DATE) + "일 " + cal.get(Calendar.HOUR) + "시 " + cal.get(Calendar.MINUTE) + "분 " + cal.get(Calendar.SECOND) + "초";
				byte[] sendData = now.getBytes(StandardCharsets.UTF_8);
				
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
						new InetSocketAddress(receivePacket.getAddress(), receivePacket.getPort()));							

				socket.send(sendPacket);

			}

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null && socket.isClosed() == false) {
				socket.close();
			}
		}
	}

}
