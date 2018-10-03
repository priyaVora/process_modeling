package vora.priya.thousands_threads.thousand_threads;

import java.time.LocalDateTime;


public class Ship extends Thread {
	@Override
	public void run() {
		try {
			Thread.sleep(((800-5) + 1) + 5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//for (int i = 0; i < Simulator.SHIP_PER_SHIFT; i++) {
			System.out.println("Ship ID: #"+ this.getId());
			System.out.println("Ship name: " + this.getName());
		//}
	}
}
