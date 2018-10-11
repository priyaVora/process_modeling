package sonic;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SonicDrive {

	final static int CUSTOMERS = 10;
	final static int MAX_OCCUPANCY = 5;
	final static int MAX_PARKING_OCCUPANCY = 3;
	final static int EMPLOYEE = 10;

	private static ExecutorService parkingService;
	private static ExecutorService employeeService;

	public static Random random = new Random();

	public static void main(String[] args) {
		generatesCustomers();
	}

	public static void generatesCustomers() {
		Semaphore parkingSpots = new Semaphore(MAX_PARKING_OCCUPANCY);
		parkingService = Executors.newFixedThreadPool(CUSTOMERS);
		Semaphore employees = new Semaphore(EMPLOYEE);
		employeeService = Executors.newFixedThreadPool(EMPLOYEE);

		for (int i = 0; i < CUSTOMERS; i++) {
			final int j = i;
			Customer currentCustomer = new Customer();
			System.out.println("\n\nCurrent Available parking spots " + parkingSpots.availablePermits());

			System.out.println(
					"Customer " + currentCustomer.getId() + " wait time starts: " + System.currentTimeMillis());
			System.out.println("   Diner Session + " + currentCustomer.getId() + " begins");

			int waitForParkingSpot = random.nextInt(10000) + 5000;
			sleep(waitForParkingSpot);
			System.out.println("#" + currentCustomer.getId() + " Wait--Parking Spot");

			parkingService.submit(() -> {
				try {
					parkingSpots.acquire();
				} catch (InterruptedException ex) {
				}
				try {
					System.out.println("Customer " + currentCustomer.getId() + " has parking spot.");
					int waitForEmployeeToTakeOrder = random.nextInt(10);
					try {
						Thread.sleep(waitForEmployeeToTakeOrder);
						System.out.println("#" + currentCustomer.getId() + " wait for employee.("
								+ waitForEmployeeToTakeOrder + ")");

						employeeService.submit(() -> {
							try {
								employees.acquire();
								System.out.println("#" + currentCustomer.getId() + " employee is available for order.");
							} catch (InterruptedException ex) {

							}
							try {
								currentCustomer.run();
								System.out.println(
										"#" + currentCustomer.getId() + " employee processes the customer's payment.");

								////////////////////////////////////////////////////////
								int preparingOrder = random.nextInt(5000);
								sleep(preparingOrder);
								System.out.println(
										"#" + currentCustomer.getId() + "'s order prepared (" + preparingOrder + ")");
								////////////////////////////
								/**
								 * Employee must be available to deliver food
								 */

								employeeService.submit(() -> {
									try {
										employees.acquire();
									} catch (InterruptedException ex) {
									}
									try {
										System.out.println("Employee deliver's food: " + currentCustomer.getId());
									} finally {
										employees.release();
									}
								});
								///////////////////////////
								int customerMealTime = random.nextInt(3000);
								sleep(customerMealTime);
								System.out.println(
										"#" + currentCustomer.getId() + " ate meal..." + "(" + customerMealTime + ")");

								System.out
										.println("Customer #" + currentCustomer.getId() + " vacates the parking lot.");
							} finally {
								employees.release();
							}
						});

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} finally {
					parkingSpots.release();
				}

			});

		}

	}

	private static void sleep(long t) {
		try {
			Thread.sleep(t);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
