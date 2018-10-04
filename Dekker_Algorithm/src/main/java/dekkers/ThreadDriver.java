package dekkers;

public class ThreadDriver {

	public static void main(String[] args) {
		MyThread t1 = new MyThread(0);
		MyThread t2 = new MyThread(1);
		
		System.out.println(MyThread.sharedResource);
		t1.start();
		t2.start();
		try { 
			Thread.sleep(2000);
		} catch(InterruptedException ex) {}
		System.out.println(MyThread.sharedResource);
	}
}
