package crepes;

import java.time.LocalDateTime;

public class Crepista extends Thread{
	@Override
	public void run() { 
		//for (int i = 0; i < Simulator.CREPRES_PER_SHIFT; i++) {
	
		while(true) {	
		System.out.println("Crepista # "+ this.getId() + " made a crepe at " + LocalDateTime.now());
		}
	}
	
	/**
	 * 
	 * */
	
	private String username, password;
	
	public boolean validateLogin() { 
		if(username.equals("joe") && password.equals("test"))  {
			return true;
		} else{ 
			return false;
		}
		
	}
}
