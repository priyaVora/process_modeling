package barbershop;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class BarbershopSimulation {
	private final int BARBER = 1;
	private final static int CUSTOMERS = 100000;
	private static int simulationClock = 0;
	private static Random random = new Random();
	private final static int MAX_CUSTOMER = 1100000;
	private static Map<Integer, Customer> customers;
	private static Barber barber;
	private static Semaphore barberAvailiability = new Semaphore(1);
	private static long totalWaitTime = 0;
	private static long meanWaitTime = 0;
	private static int customerNumber = 1;

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		customers = new HashMap<Integer, Customer>();
		System.out.println("Barbershop Simulation has started: " + startTime);
		barber = new Barber();
		System.out.println("Barber (#" + barber.getBarberId() + ")");

		while (customerNumber < MAX_CUSTOMER) {
			//sleep(random.nextInt(10000));
			int numberOfCustomers = random.nextInt(CUSTOMERS);
			for (int i = 0; i < numberOfCustomers; i++) {

				Customer newCustomer = newCustomersArrive(customerNumber, startTime);

				try {
					System.out.println("Barber is acquireed..." + "#" + newCustomer.getCustomerId());
					barberAvailiability.acquire();
					customerIntervalTime(newCustomer);
					barber.hairCut(newCustomer);
					System.out.println("_Simulation Clock: " + simulationClock);
					System.out.println(" Wait time start: (#" + System.currentTimeMillis());
					waitTime(newCustomer);
					System.out.println(" Wait time ends: (#" + System.currentTimeMillis());
					updateSimulationClock(newCustomer.getCustomerId(), newCustomer.getIntervalTime(),
							newCustomer.getArrivalTime(), newCustomer.getCutTime(), newCustomer.getWaitTime());
					customerNumber++;
					barberAvailiability.release();
					System.out.println("Barber is released..." + "#" + newCustomer.getCustomerId());
				} catch (InterruptedException e) {
					System.out.println("FAILED TO ACQUIRE...");
					e.printStackTrace();
				}

			}
		}
		updateMeanWaitTime();
		System.out.println("\n\nMean Wait Time: (" + meanWaitTime + ")");
	}

	public static void updateMeanWaitTime() {
		long meanTotal = totalWaitTime / customerNumber;
		meanWaitTime = meanTotal;
	}

	private static void waitTime(Customer newCustomer) {
		long waitTime = calculateWaitTime(newCustomer.getArrivalTime());
		newCustomer.setWaitTime(waitTime);
		totalWaitTime += waitTime;
		System.out.println("_Wait Time: (#" + newCustomer.getCustomerId() + ") " + newCustomer.getWaitTime());
	}

	private static void customerIntervalTime(Customer newCustomer) {
		setIntervalTime(newCustomer.getCustomerId(), newCustomer.getArrivalTime());
		System.out.println("_Interval Time: (#" + newCustomer.getCustomerId() + ") " + newCustomer.getIntervalTime());
	}

	private static Customer newCustomersArrive(int customerNumber, long startTime) {
		Customer newCustomer = new Customer(customerNumber);
		long customerArrivalTime = System.currentTimeMillis() - startTime;
		newCustomer.setArrivalTime(customerArrivalTime);
		System.out.println("\nArrival Time: (Customer #" + newCustomer.getCustomerId() + ") "
				+ newCustomer.getArrivalTime() + " minutes.");
		customers.put(customerNumber, newCustomer);
		return newCustomer;
	}

	private static long calculateWaitTime(long arrivalTime) {
		if (simulationClock != 0) {
			long waitTime = simulationClock - arrivalTime;
			return waitTime;
		} else {
			return 0;
		}
	}

	private static void updateSimulationClock(int customerId, long intervalTime, long arrivalTime, long cutTime,
			long waitTime) {
		if (customerId != 1) {
			simulationClock += cutTime;
		} else {
			simulationClock += arrivalTime + cutTime;
		}
		System.out.println("_Simulation Clock: " + simulationClock);
	}

	private static void setIntervalTime(int customerNumber, long currentArrival) {
		long intervalTime = 0;
		if (customerNumber != 1) {
			long previousCustomerArrival = customers.get(customerNumber - 1).getArrivalTime();
			intervalTime = currentArrival - previousCustomerArrival;
			customers.get(customerNumber).setIntervalTime(intervalTime);
		} else {
			intervalTime = currentArrival;
			customers.get(customerNumber).setIntervalTime(intervalTime);
		}
	}

	private static void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
