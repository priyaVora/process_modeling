package sonic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Customer extends Thread {
	private static Object Locked = new Object();
	public static int paymentProcessingSystem = 0;
	private static Random gen = new Random();
	long waitTime = 0;
	long totalTime = 0;
	int criticalSessionTime = 0;
	long startTime = 0;
	boolean sessionEnd = false;

	@Override
	public void run() {
		startTime = System.currentTimeMillis();
		synchronized (Locked) {
			waitTime = System.currentTimeMillis() - startTime;
			criticalPaymentSession();
		}
		Locked = true;
	}

	private void criticalPaymentSession() {
		int x = paymentProcessingSystem;
		System.out.println("# " + this.getId() + " - payment is processed.");
		x++;
		paymentProcessingSystem = x;

		sessionEnd = true;
	}
}
