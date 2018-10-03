package shared_corruption;

import java.util.Random;

public class MyThread extends Thread {

	private static boolean Locked = false;

	public static int sharedResource = 0;

	private static Random gen = new Random();
	
	//private  static Mutex lock  = new Mutex()

	public MyThread() { 
		
		this.setName("My Corrupting Thread " + this.getId());
		//this.setDaemon(true);
	}
	
	@Override
	public void run() {
		System.out.println("Starting Thread " + this.getId());
	
//
//	//	while (Locked) {
//			try {
//				Thread.sleep(150);
//			} catch (InterruptedException ex) {
//
//			}
//		//}
//
//		//Locked = true;

		int x = sharedResource;
		try {

			Thread.sleep(gen.nextInt(2));
		} catch (InterruptedException ex) {

		}
		x++;
		sharedResource = x;
		System.out.println("Ending Thread " + this.getId());
	}
}
