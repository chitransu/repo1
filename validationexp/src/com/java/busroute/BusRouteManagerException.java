package com.java.busroute;

public class BusRouteManagerException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public BusRouteManagerException(String message) {
		super(message);
	}
	
	public BusRouteManagerException(Throwable throwable) {
		super(throwable);
	}
	
	public BusRouteManagerException(String message,Throwable throwable) {
		super(message,throwable);
	}
}
