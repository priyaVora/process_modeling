package shared_corruption;

public class ThreadDriver {

	public static void main(String[] args) {
		MyThread t1 = new MyThread();
		MyThread t2 = new MyThread();
		
		System.out.println(MyThread.sharedResource);
		t1.start();
		t2.start();
		try { 
			Thread.sleep(2000);
		} catch(InterruptedException ex) {}
		System.out.println(MyThread.sharedResource);
	}
}
