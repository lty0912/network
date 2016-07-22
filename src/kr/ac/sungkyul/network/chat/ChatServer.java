package kr.ac.sungkyul.network.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class ChatServer {

	List<ChatServerThread> list;
	Hashtable hash;
	public ChatServerThread SThread;

	public void runServer() {
		ServerSocket server;
		Socket socket;
		ChatServerThread SThread;

		try {
			server = new ServerSocket(5000, 100);
			hash = new Hashtable();
			list = new ArrayList<ChatServerThread>();

			try {
				while (true) {
					socket = server.accept();
					SThread = new ChatServerThread(this, socket);
					System.out.println("입장");
					SThread.start();
					System.out.println(socket.getInetAddress().getHostName() + "서버는 클라이언트와 연결됨");
				}
			} catch (IOException e) {
				server.close();
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		ChatServer chatServer = new ChatServer();
		chatServer.runServer();
	}

	ArrayList<String> users = new ArrayList<String>();

	public String getUsers() {
		return "" + users.size();
	}

	public String[] getUsersName() {

		int i = 0;
		String[] usersName = new String[users.size()];
		for (String str : users) {
			usersName[i++] = str;
		}

		return usersName;

	}

}
