package vora.priya.discreteEventSimulation;


import java.util.Random;

public class Customer {
	private int customerId;
	private long intervalTime;
	private long arrivalTime;
	private long cutTime;
	private long waitTime;

	public Customer(int customerId) {
		this.customerId = customerId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public long getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(long intervalTime) {
		this.intervalTime = intervalTime;
	}

	public long getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(long arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public long getCutTime() {
		return cutTime;
	}

	public void setCutTime(long cutTime) {
		this.cutTime = cutTime;
	}

	public long getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(long waitTime) {
		this.waitTime = waitTime;
	}
	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", intervalTime=" + intervalTime + ", arrivalTime=" + arrivalTime
				+ ", cutTime=" + cutTime + ", waitTime=" + waitTime + "]";
	}
}
