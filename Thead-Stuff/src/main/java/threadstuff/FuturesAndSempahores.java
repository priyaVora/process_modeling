package threadstuff;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FuturesAndSempahores {
	private static final int COUNT_TASK = 5;
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService svc = Executors.newFixedThreadPool(COUNT_TASK);
		List<Future<?>> result = new ArrayList<>();
		
		for(int i = 0; i < COUNT_TASK; i++) { 
			final int j = i;
		result.add(svc.submit(() -> {
				try { Thread.sleep(10);
				}catch (Exception e) {}				
				System.out.println("Thread " + j + " executing");
				return j + j + 1;
			}));	
		}
		
		System.out.println("All threads started");
		//deal with return 
		//print the return value
		
		for(Future<?> future: result) { 
			Object o = future.get();
			System.out.println("Thread ");
		}
	}
}
