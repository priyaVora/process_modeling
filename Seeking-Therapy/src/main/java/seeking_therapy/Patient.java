package seeking_therapy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;

public class Patient extends Thread {
	private static Object Locked = new Object();
	public static int sharedTherapist = 0;
	private static Random gen = new Random();
	long waitTime = 0;
	long totalTime = 0;
	int therapySessionTime = 0;
	long startTime = 0;
	boolean sessionEnd = false;

	@Override
	public void run() {
		startTime = System.currentTimeMillis();
		System.out.println("Patient " + this.getId() + " wait time starts: " + startTime);

		synchronized (Locked) {
			waitTime = System.currentTimeMillis() - startTime;
			criticalTherapistSession();
		}

		Locked = true;
	}

	private void criticalTherapistSession() {
		int x = sharedTherapist;
		try {
			int therapySession = gen.nextInt(10000) + 5000;
			Thread.sleep(therapySession);
			therapySessionTime = therapySession;
			System.out.println("   Therapy Session + " + this.getId() + " (" + therapySession + ")");
		} catch (InterruptedException ex) {

		}
		x++;
		sharedTherapist = x;
		
		totalTime = waitTime + therapySessionTime;
		System.out.println("Patient " + this.getId() + " leaves the session");
		System.out.println("--------Wait Time: " + waitTime + "(" + this.getId() + ")");
		System.out.println("--------Total Time: " + totalTime + "(" + this.getId() + ")");
		sessionEnd = true;

	}

}
