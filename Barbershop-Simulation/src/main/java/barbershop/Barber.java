package barbershop;

import java.util.Random;

public class Barber {

	private int barberId;
	private Customer currentCustomer;
	private static Random random = new Random();

	public Barber() {
		this.barberId = random.nextInt(1000);
	}

	public static void hairCut(Customer newCustomer) {
		System.out.println(" Hair Cut Starts");
		newCustomer.setCutTime(haircutTime());
		System.out.println(" Hair Cut Start Time: " + System.currentTimeMillis());
		System.out.println("_Hair Cut: (#" + newCustomer.getCustomerId() + ") " + newCustomer.getCutTime());
		System.out.println(" Hair Cut Ended Time: " + System.currentTimeMillis());
	}

	private static Integer haircutTime() {
		return random.nextInt(60000);
	}

	public int getBarberId() {
		return barberId;
	}

	public void setBarberId(int barberId) {
		this.barberId = barberId;
	}

	public Customer getCurrentCustomer() {
		return currentCustomer;
	}

	public void setCurrentCustomer(Customer currentCustomer) {
		this.currentCustomer = currentCustomer;
	}

	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}
}
