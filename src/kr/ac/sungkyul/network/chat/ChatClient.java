package kr.ac.sungkyul.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ChatClient {
	
	public static String lastSender;

	private static final String SEPARATOR = "|";
	private static final int REQ_LOGON = 1001;
	private static final int REQ_SENDWORDS = 1021;
	private static final int REQ_WISPERSEND = 1022;
	private static final int REQ_CLOSE = 1023;

	public static void main(String args[]) {
		
		

		PrintWriter output;
		Socket client;
		StringBuffer clientdata;
		String userName;
		String host;

		ChatClientReceiveThread chatClientReceiveThread = new ChatClientReceiveThread();

		if (args.length > 0) {
			host = args[0];
			System.out.println(host);
		} else {
			host = "localhost";
		}

		try {

			client = new Socket(host, 5000);
			System.out.println(("연결된 서버이름 : " + client.getInetAddress().getHostName()));
			output = new PrintWriter(new OutputStreamWriter(client.getOutputStream(), StandardCharsets.UTF_8), true);
			clientdata = new StringBuffer(2048);

			Scanner scanner = new Scanner(System.in);
			System.out.print("이름을 입력하세요:");
			userName = scanner.nextLine();

			try {
				clientdata.setLength(0);
				clientdata.append(REQ_LOGON);
				clientdata.append(SEPARATOR);
				clientdata.append(userName);
				output.write(clientdata.toString() + "\r\n");
				output.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
					
			while(true) {
				String message = scanner.nextLine();
				StringTokenizer st = new StringTokenizer(message, " ");
				st.nextToken(); // "/w" "/r" "/c"를 삭제한다.
				
				if (message.startsWith("/w") || message.startsWith("/ㅈ")) {	
					String WID = st.nextToken();
					String Wmessage = st.nextToken();

					while (st.hasMoreTokens()) {
						Wmessage = Wmessage + " " + st.nextToken();
					}
					clientdata.setLength(0);
					clientdata.append(REQ_WISPERSEND);
					clientdata.append(SEPARATOR);
					clientdata.append(userName);
					clientdata.append(SEPARATOR);
					clientdata.append(WID);
					clientdata.append(SEPARATOR);
					clientdata.append(Wmessage);
					output.write(clientdata.toString() + "\r\n");
					output.flush();
					
				} 
				// 귓속말 답변 /r 혹은 /ㄱ 뒤에 바로 할말
				else if (message.startsWith("/r") || message.startsWith("/ㄱ") ) {
					String Wmessage = st.nextToken();

					while (st.hasMoreTokens()) {
						Wmessage = Wmessage + " " + st.nextToken();
					}
					clientdata.setLength(0);
					clientdata.append(REQ_WISPERSEND);
					clientdata.append(SEPARATOR);
					clientdata.append(userName);
					clientdata.append(SEPARATOR);
					clientdata.append(lastSender);
					clientdata.append(SEPARATOR);
					clientdata.append(Wmessage);
					output.write(clientdata.toString() + "\r\n");
					output.flush();
					

				}else {
					clientdata.setLength(0);
					clientdata.append(REQ_SENDWORDS);
					clientdata.append(SEPARATOR);
					clientdata.append(userName);
					clientdata.append(SEPARATOR);
					clientdata.append(message);
					output.write(clientdata.toString() + "\r\n");
					output.flush();
					
				}
				
			}
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
