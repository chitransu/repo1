package com.java.busroute;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BusRouteManager {

	public static void main(String[] args) throws BusRouteManagerException {
	BusRouteManager busRouteManager=new BusRouteManager();
	List<BusDetailsVO> availableBusDetailsList=busRouteManager.getBusDetailsForTravel("",7,11,"17-12-2012");
	}
	private List<BusDetailsVO> getBusDetailsForTravel(String filePath, int source, int destination, String dateOfTravel) throws BusRouteManagerException {
		List<BusDetailsVO> busDetailsVOList=new ArrayList<BusDetailsVO>();
		List<BusDetailsVO> availableBusDetailsList=null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			String line = br.readLine();
			while (line != null) {
				String[] strArgs = line.split("\\|");
				if (!validateInput(strArgs)) {
					throw new BusRouteManagerException("Input validation failed");
				} else {
					BusDetailsVO busDetailsVO = new BusDetailsVO();
					busDetailsVO.setBusNumber(strArgs[0]);
					busDetailsVO.setBusRouteNUmber(strArgs[1]);
					busDetailsVO.setSource(Integer.parseInt(strArgs[2]));
					busDetailsVO.setDestination(Integer.parseInt(strArgs[3]));
					busDetailsVO.setDayInfo(strArgs[4]);
					busDetailsVO.setBaseFare(Float.valueOf(strArgs[5]));
					busDetailsVO.setAdditionalFare(Float.valueOf(strArgs[6]));
					busDetailsVOList.add(busDetailsVO);
				}
				line = br.readLine();
			}
			availableBusDetailsList = new ArrayList<BusDetailsVO>();
			int numberOfStops = destination - source;
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Date travelDate = formatter.parse(dateOfTravel);
			SimpleDateFormat formatter2 = new SimpleDateFormat("EEEE");
			String dayStr = formatter2.format(travelDate);
		} catch (FileNotFoundException e) {
			throw new BusRouteManagerException("file is not present");
		}catch (IOException e) {
			throw new BusRouteManagerException("IO:file is not present");
		} catch (ParseException e) {
			throw new BusRouteManagerException("Travel date is not in correct format");
		}
		return null;
	}
	private boolean validateInput(String[] strArgs) {
		boolean result=true;
		if(strArgs[4].length()!=7) {
			System.out.println("Invalid Day Info : there should be seven digit");
			result=false;
		}
		int source=Integer.parseInt(strArgs[2]);
		int destiation=Integer.parseInt(strArgs[3]);
		if((source<1 || source>50)||(destiation<1 || destiation>50)) {
			System.out.println("Source or Destination is not valid");
			result=false;
		}
		return result;
	}
}
