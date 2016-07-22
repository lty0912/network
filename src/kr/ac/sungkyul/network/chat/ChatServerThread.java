package kr.ac.sungkyul.network.chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

public class ChatServerThread extends Thread {
	private Socket socket;
	private PrintWriter output;
	private BufferedReader input;
	private String clientdata;
	private String serverdata;
	private ChatServer chatServer;
	private int userCount;
	

	// 프로토콜
	private static final String SEPARATOR = "|";
	private static final int REQ_LOGON = 1001;
	private static final int REQ_SENDWORDS = 1021;
	private static final int REQ_WISPERSEND = 1022;
	private static final int REQ_CLOSE = 1023;

	public ChatServerThread(ChatServer chatServer, Socket socket) {
		this.chatServer = chatServer;
		this.socket = socket;
		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			chatServer.list.add(this);
			while ((clientdata = input.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(clientdata, SEPARATOR);
				int command = Integer.parseInt(st.nextToken());
				userCount = chatServer.list.size();
				String ID = st.nextToken();
				switch (command) {
				case REQ_LOGON: {
					chatServer.users.add(ID);
					String message = ID + "님이 입장 하였습니다.";
					System.out.println(message);
					chatServer.hash.put(ID, this); // 해쉬 테이블에 아이디와 스레드를 저장한다.
//					listAdd();
					for (int i = 0; i < userCount; i++) {
						ChatServerThread SThread = (ChatServerThread) chatServer.list.get(i);
						SThread.output.write(ID + " : " + message + "\r\n");
						SThread.output.flush();
					}
					break;
				}
				case REQ_SENDWORDS: {
					String message = st.nextToken();
					System.out.println(ID + " : " + message);
					for (int i = 0; i < userCount; i++) {
						ChatServerThread SThread = (ChatServerThread) chatServer.list.get(i);
						output.write(ID + " : " + message + "\r\n");
						output.flush();
					}
					break;
				}
				case REQ_WISPERSEND: {
					String WID = st.nextToken();
					String message = st.nextToken();
					System.out.println(ID + " -> " + WID + " : " + message);
					ChatServerThread SThread = (ChatServerThread) chatServer.hash.get(ID);
					// 해쉬테이블에서 귓속말 메시지를 전송한 클라이언트의 스레드를 구함
					SThread.output.write(ID + " -> " + WID + " : " + message + "\r\n");
					// 귓속말 메세지를 전송한 클라이언트에 전송함
					SThread.output.flush();
					SThread = (ChatServerThread) chatServer.hash.get(WID);
					// 해쉬테이블에서 귓속말 메시지를 수신할 클라이언트의 스레드를 구함
					SThread.output.write("From. " + ID + " : " + message + "\r\n");
					// 귓속말 메시지를 수신할 클라이언트에 전송함
					SThread.output.flush();
					break;
				}

				// 사용자가 나갈경우 일어나는 case
				case REQ_CLOSE: {
					// 나간 아이디를 찾는다
					int count = -1;
					while (!chatServer.users.get(++count).equals(ID));
					String message = (ID + "님이 퇴장 하였습니다.");					

					// 나간 아이디를 삭제
					chatServer.users.remove(count);
//					listAdd();
					
					for (int i = 0; i < userCount; i++) {
						ChatServerThread SThread = (ChatServerThread) chatServer.list.get(i);
						SThread.output.write(ID + " : " + message + "\r\n");
						SThread.output.flush();
					}					
					
					break;
				}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		chatServer.list.remove(this);

		try {

			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 참가자 명단 작성 메소드
	// list라는 토큰으로 사용되는 문자 뒤에 차례로 사용자 ID 가 붙혀진다.
//	public void listAdd() {
//		for (int i = 0; i < userCount; i++) {
//			ChatServerThread SThread = (ChatServerThread) chatServer.list.get(i);
//			String userName = "list";
//			for (String name : chatServer.getUsersName()) {
//				userName = userName + " " + name;
//			}
//			try {
//				SThread.output.write(userName + "\r\n");
//				SThread.output.flush();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}

}
