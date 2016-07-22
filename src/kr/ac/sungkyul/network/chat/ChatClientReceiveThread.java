package kr.ac.sungkyul.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.StringTokenizer;

public class ChatClientReceiveThread extends Thread {

	BufferedReader input;
	Socket client;

	String serverdata;
	String userName;
	

	// 호스트 ID를 받아올 String 변수
	public static String host;

	private static final String SEPARATOR = "|";
	private static final int REQ_LOGON = 1001;
	private static final int REQ_SENDWORDS = 1021;
	private static final int REQ_WISPERSEND = 1022;
	private static final int REQ_CLOSE = 1023;

	public void run(String host) {
		try {
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));

			while (true) {
				serverdata = input.readLine();
				StringTokenizer temp = new StringTokenizer(serverdata, " ");
				String compare = temp.nextToken();
				
				System.out.println(serverdata);
				if (compare.equals("From.")) {
					ChatClient.lastSender = temp.nextToken();
				} else {
					System.out.println(serverdata);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
