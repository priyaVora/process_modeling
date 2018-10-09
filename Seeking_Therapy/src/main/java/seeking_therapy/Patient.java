package seeking_therapy;

import java.util.Random;

public class Patient extends Thread {
	private static Object Locked = new Object();

	public static int sharedTherapist_ = 0;

	private static Random gen = new Random();

	public Patient() {

		this.setName("My Corrupting Thread " + this.getId());
	}

	@Override
	public void run() {
		System.out.println("Patient starts the session " + this.getId());

		System.out.println("Lock starts...");
		
		synchronized (Locked) {			
			criticalCondition();
		} 
		

		Locked = true;
		System.out.println("Lock ends...");

	}

	public void criticalCondition() {
		int x = sharedTherapist_;
		try {
			Thread.sleep(gen.nextInt(2));
		} catch (InterruptedException ex) {

		}
		x++;
		sharedTherapist_ = x;
		System.out.println("Patient leaves the session " + this.getId());
	}
}
