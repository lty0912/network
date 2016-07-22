package kr.ac.sungkyul.thread;

public class ThreadExample {

	public static void main(String[] args) {
		
		DigitThread th1 = new DigitThread();
		DigitThread th2 = new DigitThread();
		AlphabetThread th3 = new AlphabetThread();
		Thread th4 = new Thread( new UpperCaseAlphabetRunnableImpl());
		
		th1.start();		
		th2.start();
		th3.start();
		th4.start();
		
		
		

		
	}

}
