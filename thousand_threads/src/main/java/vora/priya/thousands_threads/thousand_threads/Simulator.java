package vora.priya.thousands_threads.thousand_threads;

import java.util.ArrayList;
import java.util.List;

public class Simulator {
	public static final int COUNT_SHIP = 1000;
	public static final int SHIP_PER_SHIFT = 1000;
	
	public static void main(String[] args) {
		List<Ship> ship_list = new ArrayList<Ship>();
		
		for(int i = 0; i< COUNT_SHIP; i++) { 
			ship_list.add(new Ship());	
		}
		
		for(Ship current_ship: ship_list) { 
			current_ship.start();
		}
	}
}
