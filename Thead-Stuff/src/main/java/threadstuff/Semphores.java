package threadstuff;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Semphores {
	
	final static int STUDENTS = 100;
	final static int MAX_OCCUPANCY = 5;
	
	
	public static void main(String[] args) {
		Semaphore semphores = new Semaphore(MAX_OCCUPANCY, true); // only use true if it is necessary in the code
		ExecutorService svc = Executors.newFixedThreadPool(STUDENTS);
		for (int i = 0; i < STUDENTS; i++) {
			final int j = i;
			svc.submit(() -> { 
				try { 
				semphores.acquire();} catch(InterruptedException ex) {}
				try  {
				System.out.println("Thread " + j + " has entered the classroom");
				sleep(500);
				System.out.println("Thread " + j + " has left the classroom");
				sleep(10);
				} finally  {	
					semphores.release();
				}
			});
		}
	}
	
	private static void sleep(long t) { 
		try { 
			Thread.sleep(t);
		} catch(InterruptedException ex) { 
			ex.printStackTrace();
		}
	}
}
