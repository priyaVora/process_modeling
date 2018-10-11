package sonic;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SonicDrive {

	final static int CUSTOMERS = 10;
	final static int MAX_PARKING_OCCUPANCY = 3;
	final static int EMPLOYEE = 2;

	private static ExecutorService sonicService;
	private static Semaphore parkingSpots = new Semaphore(MAX_PARKING_OCCUPANCY);
	private static Semaphore employees = new Semaphore(EMPLOYEE);

	public static Random random = new Random();

	public static void main(String[] args) {
		generatesCustomers();
	}

	public static void generatesCustomers() {
		sonicService = Executors.newFixedThreadPool(MAX_PARKING_OCCUPANCY);

		for (int i = 0; i < CUSTOMERS; i++) {
			final int j = i;
			Customer currentCustomer = new Customer();
			System.out.println(
					"Customer " + currentCustomer.getId() + " wait time starts: " + System.currentTimeMillis());
			System.out.println("   Diner Session + " + currentCustomer.getId() + " begins");

			// int waitForParkingSpot = random.nextInt(1000);
			// sleep(waitForParkingSpot);
			// System.out.println("#" + currentCustomer.getId() + " Wait--Parking Spot");

			sonicService.submit(() -> {
				try {
					System.out.println("Customer " + currentCustomer.getId() + " has parking spot.");
					parkingSpots.acquire();

				} catch (InterruptedException ex) {
				}
				try {
					try {
						employees.acquire();

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					employeeService(currentCustomer);

				} finally {
					parkingSpots.release();
				}
			});
		}

	}

	private static void employeeService(Customer currentCustomer) {
		// checkEmployeeAvailability(currentCustomer);
		// sleep while taking order
		int waitForEmployeeToTakeOrder = random.nextInt(1000);
		sleep(waitForEmployeeToTakeOrder);
		System.out.println("#" + currentCustomer.getId() + " wait for employee.(" + waitForEmployeeToTakeOrder + ")");
		try {
			processPayment(currentCustomer);
			employees.release();
			prepareOrder(currentCustomer);
			foodDelivery(currentCustomer);
			mealTime(currentCustomer);
			System.out.println("Customer #" + currentCustomer.getId() + " vacates the parking lot.");
		} finally {
			// employees.release();
		}

	}

	private static void mealTime(Customer currentCustomer) {
		int customerMealTime = random.nextInt(3000);
		sleep(customerMealTime);
		System.out.println("#" + currentCustomer.getId() + " ate meal..." + "(" + customerMealTime + ")");
	}

	private static void foodDelivery(Customer currentCustomer) {
		try {
			employees.acquire();
			System.out.println("Employee deliver's food: " + currentCustomer.getId());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			employees.release();
		}

	}

	// public static void checkEmployeeAvailability(Customer currentCustomer) {
	// try {
	// if (employees.availablePermits() != 0) {
	// System.out.println("#" + currentCustomer.getId() + " employee is available
	// for order.");
	// employees.acquire();
	// } else {
	// sleep(random.nextInt(2000));
	// }
	//
	// } catch (InterruptedException ex) {
	//
	// }
	// }

	public static void prepareOrder(Customer currentCustomer) {
		int preparingOrder = random.nextInt(5000);
		sleep(preparingOrder);
		System.out.println("#" + currentCustomer.getId() + "'s order prepared (" + preparingOrder + ")");
	}

	public static void processPayment(Customer currentCustomer) {
		currentCustomer.run();
	}

	private static void sleep(long t) {
		try {
			Thread.sleep(t);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
