package com.java.validation;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CreditcardFineCalculater {

	public static void main(String[] args) {
		try {
			Map<Integer, Map<String, CreditCardVO>> outputMap = getCreditCardFineDetails("creditcarddata.txt");
		} catch (CreditCardFileCalculatorException e) {
			e.printStackTrace();
		}
	}

	public static Map<Integer, Map<String, CreditCardVO>> getCreditCardFineDetails(String filePath)
			throws CreditCardFileCalculatorException {
		Map<Integer, Map<String, CreditCardVO>> finalMap = new HashMap<Integer, Map<String, CreditCardVO>>();
		Map<String, CreditCardVO> visaMap = new HashMap<String, CreditCardVO>();
		Map<String, CreditCardVO> amexMap = new HashMap<String, CreditCardVO>();
		File file = new File(filePath);
		if (file.exists()) {
			try {
				Scanner sc = new Scanner(file);
				sc.useDelimiter("[|\n]");
				while (sc.hasNext()) {
					String creditCardNumber = sc.next();
					String customerName = sc.next();
					int billAmount = Integer.parseInt(sc.next());
					String dueDate = sc.next();
					Date parsedDueDate = convertStringToDate(dueDate);
					String paymentDate = sc.next();
					Date parsedPaymentDate = convertStringToDate(paymentDate);
					boolean isValid = validateInput(creditCardNumber, customerName, billAmount, dueDate, paymentDate);
					if (isValid) {
						CreditCardVO creditCardVO = new CreditCardVO();
						creditCardVO.setCreditCardNumber(creditCardNumber);
						creditCardVO.setCustomerName(customerName);
						creditCardVO.setBillAmount(billAmount);
						creditCardVO.setDueDate(parsedDueDate);
						creditCardVO.setPaymentDate(parsedPaymentDate);
						creditCardVO.setFine(calculateFine(parsedDueDate, parsedPaymentDate, billAmount));
						creditCardVO.setCreditCardGrade(calculateGrade(parsedDueDate, parsedPaymentDate, billAmount));
						if (creditCardNumber.charAt(0) == '4' && creditCardNumber.length() == 16) {
							visaMap.put(creditCardNumber, creditCardVO);
						}
						if ((creditCardNumber.startsWith("34") || creditCardNumber.startsWith("37"))
								&& creditCardNumber.length() == 15) {
							amexMap.put(creditCardNumber, creditCardVO);
						}
					} else {
						throw new CreditCardFileCalculatorException("Input validation failed");
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			throw new CreditCardFileCalculatorException("FIle Not found exception:");
		}
		finalMap.put(1, visaMap);
		finalMap.put(2, amexMap);
		return finalMap;
	}

	private static char calculateGrade(Date parsedDueDate, Date parsedPaymentDate, int billAmount) {
		int noOfDays = calculateDaysBetween(parsedDueDate, parsedPaymentDate);
		char grade;
		if (noOfDays == 0) {
			grade = 'A';
			return grade;
		} else {
			grade = 'B';
			return grade;
		}
	}

	private static int calculateFine(Date parsedDueDate, Date parsedPaymentDate, int billAmount) {
		int noOfDays = calculateDaysBetween(parsedDueDate, parsedPaymentDate);
		int fine = 0;
		if (noOfDays < 5) {
			fine = billAmount * (10 / 100);
		} else if (noOfDays > 5 && billAmount <= 15000) {
			fine = billAmount * (20 / 100);
		} else {
			fine = billAmount * (30 / 100);
		}
		return fine;
	}

	private static int calculateDaysBetween(Date d1, Date d2) {
		// TODO Auto-generated method stub
		return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}

	private static boolean validateInput(String creditCardNumber, String customerName, int billAmount, String dueDate,
			String paymentDate) throws CreditCardFileCalculatorException {
		boolean isValid = true;
		if (!((creditCardNumber.charAt(0) == '4' && creditCardNumber.length() == 16)
				|| ((creditCardNumber.startsWith("34") || creditCardNumber.startsWith("37"))
						&& creditCardNumber.length() == 15))) {
			isValid = false;
			throw new CreditCardFileCalculatorException("Invalid credit card number");
		}

		if (!isValidDate(dueDate)) {
			isValid = false;
			throw new CreditCardFileCalculatorException("Invalid Due Date");
		}
		if (!isValidDate(paymentDate)) {
			isValid = false;
			throw new CreditCardFileCalculatorException("Invalid Payment Date");
		}
		return isValid;
	}

	private static boolean isValidDate(String dueDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		formatter.setLenient(false);
		try {
			formatter.parse(dueDate.trim());
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private static Date convertStringToDate(String dateString) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = formatter.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}

class CreditCardVO {
	private String creditCardNumber;
	private int billAmount;
	private int fine;
	private Date dueDate;
	private Date paymentDate;
	private String customerName;
	private char creditCardGrade;

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public int getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(int billAmount) {
		this.billAmount = billAmount;
	}

	public int getFine() {
		return fine;
	}

	public void setFine(int fine) {
		this.fine = fine;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public char getCreditCardGrade() {
		return creditCardGrade;
	}

	public void setCreditCardGrade(char creditCardGrade) {
		this.creditCardGrade = creditCardGrade;
	}

}

class CreditCardFileCalculatorException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CreditCardFileCalculatorException(String message) {
		super(message);
	}

	public CreditCardFileCalculatorException(Throwable throwable) {
		super(throwable);
	}

	public CreditCardFileCalculatorException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
