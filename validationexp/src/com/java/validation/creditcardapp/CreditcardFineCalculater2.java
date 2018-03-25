package com.java.validation.creditcardapp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreditcardFineCalculater2 {

	private static final List<CreditCardVO> creditCardDeta=new ArrayList<CreditCardVO>();
	
	public static void main(String[] args) throws CreditCardFileCalculatorException {
		CreditcardFineCalculater2 creditcardFineCalculater=new CreditcardFineCalculater2();
		String filePath="C:\\Users\\ADMIN\\eclipse-workspace\\validationexp\\creditcarddata.txt";
		creditcardFineCalculater.creditCardFineDetails(filePath);
	}
	
	public Map<Integer, Map<String, CreditCardVO>> creditCardFineDetails(String filePath)
			throws CreditCardFileCalculatorException {
		parseFileData(filePath);
		Map<Integer, Map<String, CreditCardVO>> outputmap = new HashMap<Integer, Map<String, CreditCardVO>>();
		
		for (CreditCardVO creditCardVO : creditCardDeta) {
			String creditCardNo = creditCardVO.getCreditCardNumber();
			if (getCreditCardType(creditCardNo).equals("VISA")) {
				if (outputmap.containsKey(1)) {
					Map<String, CreditCardVO> innMap = outputmap.get(1);
					innMap.put(creditCardNo, creditCardVO);
				}else {
					Map<String, CreditCardVO> innerMap = new HashMap<String, CreditCardVO>();
					innerMap.put(creditCardNo, creditCardVO);
					outputmap.put(1, innerMap);
				}
			}else {
				if (outputmap.containsKey(2)) {
					Map<String, CreditCardVO> innMap = outputmap.get(2);
					innMap.put(creditCardNo, creditCardVO);
				}else {
					Map<String, CreditCardVO> innerMap = new HashMap<String, CreditCardVO>();
					innerMap.put(creditCardNo, creditCardVO);
					outputmap.put(2, innerMap);
				}
			}
		}

		System.out.println(outputmap);
		return outputmap;
	}

	private void parseFileData(String filePath) throws CreditCardFileCalculatorException {
		BufferedReader br=null;
		try {
			br=new BufferedReader(new FileReader(filePath));
			String line=br.readLine();
			while(line!=null) {
				String[] strArgs=line.split("\\|");
				if(!validateInput(strArgs)) {
					throw new CreditCardFileCalculatorException("validation failed");
				}else {
					CreditCardVO creditCardVO=new CreditCardVO();
					creditCardVO.setCreditCardNumber(strArgs[0]);
					creditCardVO.setCustomerName(strArgs[1]);
					creditCardVO.setBillAmount(Integer.parseInt(strArgs[2]));
					creditCardVO.setDueDate(parseDate(strArgs[3]));
					creditCardVO.setPaymentDate(parseDate(strArgs[4]));
					creditCardVO.setCreditCardGrade(getCreditGrade(creditCardVO.getDueDate(),creditCardVO.getPaymentDate()));
					creditCardVO.setFine(calculateFine(creditCardVO));
					creditCardDeta.add(creditCardVO);
				}
				line=br.readLine();
			}
		} catch (FileNotFoundException e) {
			throw new CreditCardFileCalculatorException("file is not present in specified location");
		} catch (IOException e) {
			throw new CreditCardFileCalculatorException("error while reading file");
		}finally {
			try {
				br.close();
			} catch (IOException e) {
				throw new CreditCardFileCalculatorException("error while closing bufered reader");
			}
		}
		System.out.println(creditCardDeta);
	}

	private int calculateFine(CreditCardVO creditCardVO) {
		int fine = 0;
		int noOfDays = calculateNoOfDays(creditCardVO.getDueDate(), creditCardVO.getPaymentDate());
		if (getCreditCardType(creditCardVO.getCreditCardNumber()).equals("VISA")) {
			if (noOfDays <= 5) {
				fine = creditCardVO.getBillAmount() * 10 / 100;
			} else {
				fine = creditCardVO.getBillAmount() * 20 / 100;
			}
		}
		if (getCreditCardType(creditCardVO.getCreditCardNumber()).equals("AMEX")) {
			if (noOfDays <= 5) {
				fine = creditCardVO.getBillAmount() * 10 / 100;
			}else if (noOfDays >= 5 && creditCardVO.getBillAmount() <= 15000) {
				fine = creditCardVO.getBillAmount() * 20 / 100;
			} else {
				fine = creditCardVO.getBillAmount() * 30 / 100;
			}
		}
		return fine;
	}
	
	private String getCreditCardType(String creditCardNo) {
		String result="";
		if(creditCardNo.startsWith("4") && creditCardNo.length() == 16) {
			 result="VISA";
		}else if((creditCardNo.startsWith("34") || creditCardNo.startsWith("37"))
				&& creditCardNo.length() == 15){
			 result="AMEX";
		}
		return result;
	}

	private int calculateNoOfDays(Date dueDate, Date paymentDate) {
		if(paymentDate.after(dueDate)) {
			return (int) ((paymentDate.getTime()-dueDate.getTime())/(1000*60*60*24));
		}
		return 0;
	}

	private char getCreditGrade(Date dueDate, Date paymentDate) {
		if(paymentDate.after(dueDate)) {
			return 'B';
		}else {
			return 'A';
		}
	}

	private Date parseDate(String string) {
		SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
		formatter.setLenient(false);
		try {
			return formatter.parse(string);
		} catch (ParseException e) {
			return null;
		}
	}

	private boolean validateInput(String[] strArgs) {
		boolean result = true;
		if (strArgs.length != 5 || strArgs[0].isEmpty() || strArgs[1].isEmpty() || strArgs[2].isEmpty()
				|| strArgs[3].isEmpty() || strArgs[4].isEmpty()) {
			System.out.println("validation failed -- All fields are mendetory");
			result = false;
		}

		if (!strArgs[0].startsWith("4") && strArgs[0].length() == 16) {
			System.out.println("validation failed -- Invalid VISA credit card");
			result = false;
		} else if(strArgs[0].startsWith("4") && strArgs[0].length() != 16) {
			System.out.println("validation failed -- Invalid VISA credit card");
			result = false;
		}
		
		if ((strArgs[0].startsWith("34") || strArgs[0].startsWith("37")) && strArgs[0].length() != 15) {
			System.out.println("validation failed -- Invalid AMEX credit card");
			result = false;
		}else if(!(strArgs[0].startsWith("34") || strArgs[0].startsWith("37")) && strArgs[0].length() == 15) {
			System.out.println("validation failed -- Invalid AMEX credit card");
			result = false;
		}

		if (!isValidDate(strArgs[3])) {
			System.out.println("validation failed -- Invalid due date");
			result = false;
		}

		if (!isValidDate(strArgs[4])) {
			System.out.println("validation failed -- Invalid payment date");
			result = false;
		}

		return result;
	}

	private boolean isValidDate(String string) {
		SimpleDateFormat format =new SimpleDateFormat("dd/MM/yyyy");
		format.setLenient(false);
		try {
			format.parse(string);
			return true;
		} catch (ParseException e) {
			return false;
		}
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
