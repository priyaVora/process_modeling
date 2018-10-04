package dekkers;

import java.util.Random;

public class MyThread extends Thread {
	private int currentId = 0;
	
	private int otherId = 0;

	private static boolean Locked = false;
	
	private static boolean[] requestSection = new boolean[2];
	
	private static int turn = 0;
	
	public static int sharedResource = 0;

	private static Random gen = new Random();
	
	public MyThread(int currentId) { 
		this.currentId = currentId;
		//this.otherId = otherId;
		this.setName("My Corrupting Thread " + this.getId());
		//taskId = (int) this.getId();
	}
	
	@Override
	public void run() {
		try {
			dekkers();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void dekkers() throws InterruptedException { 
			otherId = currentId == 0 ? 1 : 0;
			requestSection[currentId] = true;
			System.out.println("Starting Thread " + currentId);
			
			while(requestSection[otherId]) { 
				if(turn != currentId) { 
					requestSection[currentId] = false;
					while(turn != currentId) { }
					requestSection[currentId] = true;
					Thread.sleep(50);
				}
			}
			//Critical Section
			criticalSection();
			turn = otherId;
			requestSection[currentId] = false;	
			System.out.println("Ending Thread " + currentId);
	}	
	
	public void criticalSection() { 
		
		int x = sharedResource;
		try {
			Thread.sleep(gen.nextInt(15));
		} catch (InterruptedException ex) {
		
		}
		x++;
		sharedResource = x;
	}
}
