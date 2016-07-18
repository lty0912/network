package kr.ac.sungkyul.network.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHost {
	
	public static void main(String[] args) {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String hostname = inetAddress.getHostName();
			System.out.println("hostname: " + hostname);
			
			String hostAddress = inetAddress.getHostAddress();
			System.out.println("hostAddress: " + hostAddress);
			
			byte[] addresses = inetAddress.getAddress();
			for(int i=0; i<addresses.length; i++) {
				System.out.print(addresses[i] & 0xff);
				if(i<addresses.length - 1) {
					System.out.print(".");
				}
			}
			
			
		} catch (UnknownHostException e) {			
			e.printStackTrace();
		}
	}

}
