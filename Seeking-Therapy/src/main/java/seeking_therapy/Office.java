package seeking_therapy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Office {
	private static Object therapist;
	private static boolean dayIsNotOver = true;
	public static Random random = new Random();
	private static Timer dayOverTimer = new Timer();
	private static int fullDay = 119;
	private static List<Patient> listOfpatients = new ArrayList<>();
	public static ExecutorService patientService;

	public static long therapistStartTime = System.currentTimeMillis();

	public static void main(String[] args) {

		// System.out.println("Current Time: " + therapistStartTime);
		// elapsedTime();

		dayOverTimer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				if (fullDay != 0) {
					System.out.println(fullDay);
					System.out.println(dayIsNotOver);
					dayIsNotOver = true;
					--fullDay;
				} else {
					dayOverTimer.cancel();
					dayIsNotOver = false;
					// System.out.println(fullDay);
					// System.out.println(dayIsNotOver);
				}
			}
		}, 1000, 1000);
		generatesPatients();
	}

	public static double elapsedTime() {
		long now = System.currentTimeMillis();
		return (now - therapistStartTime) / 1000;
	}

	public static void generatesPatients() {
		int maxTime = 20000;
		int minTime = 5000;
		int lowPatientCount = 1;
		int highPatientCount = 6;

		int numberOfPatients = random.nextInt(3) + 1;
		patientService = Executors.newCachedThreadPool();
		while (dayIsNotOver) {
			// Thread sleep

			try {
				Thread.sleep(random.nextInt(maxTime + 1 - minTime) + minTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Create 1-3 new Patients threads start time
			//System.out.println("Number of Patients Created: " + numberOfPatients);
			for (int i = 0; i < numberOfPatients; i++) {

				if (dayIsNotOver != false) {
					Patient currentPatient = new Patient();
					listOfpatients.add(listOfpatients.size(), currentPatient);
					patientService.submit(() -> currentPatient.run());
					if (currentPatient.sessionEnd == true) {
						listOfpatients.remove(listOfpatients.size());
					}
				}

			}
		}
		patientService.shutdownNow();
		dayIsNotOver = false;
		threadResoureStarvation();
	}

	public static void threadResoureStarvation() {
		List<Patient> patientsNotSeen = new ArrayList<>();

		int patientNotSeenCounter = 0;
		for (Patient patient : listOfpatients) {
			if (patient.sessionEnd == false) {
				patientsNotSeen.add(patient);
				patientNotSeenCounter++;
			}
		}

		System.out.println("//End of sessions -- Patients not seen: " + patientsNotSeen.size());
		for (Patient patient : patientsNotSeen) {

			System.out.println("#" + patient.getId() + " wait time: " + patient.waitTime);
		}
	}
}
