package seeking_therapy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Therapist {

	public static Timer timer = new Timer();
	public static Random random = new Random();
	
	public static void main(String[] args) {
		System.out.println("Current Therapist " + "");
		generatesPatients();
	}

	private static void generatesPatients() {
		ExecutorService patientService = Executors.newScheduledThreadPool(random.nextInt(3)+1);
		int min = 5000;
		int max = 20000;
	
		Patient currentPatient = new Patient();
		
		
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
			
				
				
				patientService.submit(() -> currentPatient.run());
				patientService.shutdown();
			}
		};
		timer.scheduleAtFixedRate(task, 1, random.nextInt(max + 1 -min) + min);
		
		
	}
}
