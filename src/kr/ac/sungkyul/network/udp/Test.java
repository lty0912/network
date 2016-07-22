package kr.ac.sungkyul.network.udp;

import java.util.Calendar;

public class Test {
	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
//		int month = (cal.get(Calendar.MONTH) + 1);
//		System.out.println(month);
		String now = cal.get(Calendar.YEAR) +"년 "+ (cal.get(Calendar.MONTH) + 1) + "월 " + cal.get(Calendar.DATE) + "일 " + cal.get(Calendar.HOUR) + "시 " + cal.get(Calendar.MINUTE) + "분 " + cal.get(Calendar.SECOND) + "초";
		System.out.println(now);
	}

}
