package kr.ac.sungkyul.thread;

public class AlphabetThread extends Thread {
	@Override
	public void run() {
		for (int i = 'a'; i < 'z'; i++) {
			System.out.print((char) i);

		}
	}
}
