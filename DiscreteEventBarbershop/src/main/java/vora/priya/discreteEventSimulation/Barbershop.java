package vora.priya.discreteEventSimulation;

import java.util.ArrayList;
import java.util.List;

public class Barbershop {
	private final int MAXCUSTOMER = 10000;
	private static final int MEANARRIVALTIME = 20;
	private static final int MEANCUSTOMERHAIRCUT = 15;
	private int simulationClock = 0;
	private int totalWaitTime = 0;

	private List<Customer> customers = new ArrayList<Customer>();

	public static void main(String[] args) {
		Barbershop shop = new Barbershop();
		List<Customer> customersList = shop.createCustomers();
		shop.processSimulation(customersList);
	}

	public int generateTime(int mean) {
		double randomValue = Math.random();
		int actualTime = (int) ((-Math.log(1 - randomValue)) * mean);
		return actualTime;
	}

	public List<Customer> createCustomers() {
		List<Customer> listofCustomers = new ArrayList();
		for (int i = 0; i < MAXCUSTOMER; i++) {
			Customer newCustomer = new Customer(i);
			listofCustomers.add(newCustomer);
		}
		return listofCustomers;
	}

	public void processSimulation(List<Customer> customers) {
		for (int i = 0; i < customers.size(); i++) {
			Customer currentCustomer = customers.get(i);
			if (i == 0) {
				currentCustomer.setArrivalTime(generateTime(MEANARRIVALTIME));
				System.out.println("Customer " + currentCustomer.getCustomerId() + " arrival time: "
						+ currentCustomer.getArrivalTime());
				currentCustomer.setIntervalTime(currentCustomer.getArrivalTime());
				System.out.println("Customer " + currentCustomer.getCustomerId() + " interval time: "
						+ currentCustomer.getIntervalTime());
				while (simulationClock < currentCustomer.getArrivalTime()) {
					simulationClock++;
				}
				totalWaitTime += currentCustomer.getWaitTime();
				currentCustomer.setCutTime(generateTime(MEANCUSTOMERHAIRCUT));
				System.out.println(
						"Customer " + currentCustomer.getCustomerId() + " cut time: " + currentCustomer.getCutTime());
				simulationClock = (int) (currentCustomer.getArrivalTime() + currentCustomer.getCutTime());
				System.out.println("Clock: " + simulationClock);

			} else {
				// Arrival time = Previous Customer's Arrival time Plus the current customer's
				// Interval time
				currentCustomer.setIntervalTime(generateTime(MEANARRIVALTIME));
				currentCustomer
						.setArrivalTime((customers.get(i - 1).getArrivalTime() + currentCustomer.getIntervalTime()));

				System.out.println("Customer " + currentCustomer.getCustomerId() + " arrival time: "
						+ currentCustomer.getArrivalTime());
				System.out.println("Customer " + currentCustomer.getCustomerId() + " interval time: "
						+ currentCustomer.getIntervalTime());

				currentCustomer.setWaitTime(simulationClock - currentCustomer.getArrivalTime());
				if (currentCustomer.getWaitTime() < 0) {
					currentCustomer.setWaitTime(0);
				}
				while (simulationClock < currentCustomer.getArrivalTime()) {
					simulationClock++;
				}
				totalWaitTime += currentCustomer.getWaitTime();
				currentCustomer.setCutTime(generateTime(MEANCUSTOMERHAIRCUT));
				System.out.println(
						"Customer " + currentCustomer.getCustomerId() + " cut time: " + currentCustomer.getCutTime());
				System.out.println(
						"Customer " + currentCustomer.getCustomerId() + " wait time: " + currentCustomer.getWaitTime());
				System.out.println("Before Clock: " + simulationClock);
				simulationClock += (int) currentCustomer.getCutTime();
				System.out.println("After Clock: " + simulationClock);
			}
			this.customers.add(i, currentCustomer);
		}
		
		System.out.println("\nTotal Time: " + totalWaitTime);
		System.out.println("Total Customers: " + MAXCUSTOMER);
		System.out.println("Mean Wait Time: " + (totalWaitTime / MAXCUSTOMER));
	}

	public void runSimulationClock() {

	}
}
