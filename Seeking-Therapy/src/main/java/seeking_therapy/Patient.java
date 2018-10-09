package seeking_therapy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;

public class Patient extends Thread {
	private static Object Locked = new Object();
	public static int sharedTherapist = 0;
	private static Random gen = new Random();
	int waitTime = 0;
	boolean sessionEnd = false;
	
	@Override
	public void run() {
		System.out.println("Patient " + this.getId() + " wait time starts: " + LocalDateTime.now());

		synchronized (Locked) {
			criticalTherapistSession();
		}

		Locked = true;
	}

	private void criticalTherapistSession() {
		int x = sharedTherapist;
		try {
			int generator = gen.nextInt(10000) +5000;
			Thread.sleep(generator);
			waitTime = generator;
			System.out.println("   Wait Time + " + this.getId() + " (" +generator + ")");
		} catch (InterruptedException ex) {

		}
		x++;
		sharedTherapist = x;
		System.out.println("Patient " + this.getId() + " leaves the session (" + waitTime + ")");
		sessionEnd = true;
		
	}
	
}
