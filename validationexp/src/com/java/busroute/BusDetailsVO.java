package com.java.busroute;

public class BusDetailsVO {

	private String busNumber;
	private String busRouteNUmber;
	private int source;
	private int destination;
	private String dayInfo;
	private float baseFare;
	private float additionalFare;

	public String getBusNumber() {
		return busNumber;
	}

	public void setBusNumber(String busNumber) {
		this.busNumber = busNumber;
	}

	public String getBusRouteNUmber() {
		return busRouteNUmber;
	}

	public void setBusRouteNUmber(String busRouteNUmber) {
		this.busRouteNUmber = busRouteNUmber;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public int getDestination() {
		return destination;
	}

	public void setDestination(int destination) {
		this.destination = destination;
	}

	public String getDayInfo() {
		return dayInfo;
	}

	public void setDayInfo(String dayInfo) {
		this.dayInfo = dayInfo;
	}

	public float getBaseFare() {
		return baseFare;
	}

	public void setBaseFare(float baseFare) {
		this.baseFare = baseFare;
	}

	public float getAdditionalFare() {
		return additionalFare;
	}

	public void setAdditionalFare(float additionalFare) {
		this.additionalFare = additionalFare;
	}

}
