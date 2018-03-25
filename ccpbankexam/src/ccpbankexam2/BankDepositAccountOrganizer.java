package ccpbankexam2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class BankDepositAccountOrganizer {

	public static void main(String[] args) throws FileNotFoundException, IOException, BankOrganizerException {
		processBankDepositData("C:\\Users\\ADMIN\\eclipse-workspace\\ccpbankexam\\src\\accountdetails.txt");
	}

	private static final List<ParentAccountVO> data = new ArrayList<ParentAccountVO>();

	public static Map<String, List<ParentAccountVO>> processBankDepositData(String filePath)
			throws BankOrganizerException, FileNotFoundException {
		try {
			parseAccountData(filePath);
		} catch (IOException e) {
			throw new BankOrganizerException("Error in parsing data");
		}

		Map<String, List<ParentAccountVO>> outputMap = new HashMap<String, List<ParentAccountVO>>();
		for(int i=0;i<data.size();i++) {
			if(outputMap.containsKey(data.get(i).getAccType())) {
				List<ParentAccountVO> parentVOList=outputMap.get(data.get(i).getAccType());
				parentVOList.add(data.get(i));
			}else {
				List<ParentAccountVO> pList=new ArrayList<ParentAccountVO>();
				pList.add(data.get(i));
				outputMap.put(data.get(i).getAccType(), pList);
			}
		}
	/*	List<ParentAccountVO> savList = new ArrayList<ParentAccountVO>();
		List<ParentAccountVO> wmList = new ArrayList<ParentAccountVO>();
		List<ParentAccountVO> nriList = new ArrayList<ParentAccountVO>();

		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getAccType().equals("SAV")) {
				savList.add(data.get(i));
				outputMap.put(data.get(i).getAccType(), savList);
			}
			if (data.get(i).getAccType().equals("WM")) {
				wmList.add(data.get(i));
				outputMap.put(data.get(i).getAccType(), wmList);
			}
			if (data.get(i).getAccType().equals("NRI")) {
				nriList.add(data.get(i));
				outputMap.put(data.get(i).getAccType(), nriList);
			}
		}*/

		// System.out.println(outputMap);

		Iterator<Map.Entry<String, List<ParentAccountVO>>> entryItr = outputMap.entrySet().iterator();
		System.out.println("key" + "--------------" + "Value");
		while (entryItr.hasNext()) {
			Map.Entry<String, List<ParentAccountVO>> entry = entryItr.next();
			System.out.println(entry.getKey() + "------" + entry.getValue());
		}

		return outputMap;
	}

	private static void parseAccountData(String filePath)
			throws FileNotFoundException, IOException, BankOrganizerException {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line = br.readLine();
			while (line != null) {
				String args[] = line.split(",");
				if (!validateData(args)) {
					throw new BankOrganizerException("Invalid Data");
				} else {
					ParentAccountVO pAccountVO = new ParentAccountVO();
					pAccountVO.setParentAccNo(Integer.parseInt(args[0]));
					pAccountVO.setName(args[1]);
					pAccountVO.setAccType(args[2]);
					List<LinkedDepositVO> lDepositVOList = new ArrayList<LinkedDepositVO>();
					LinkedDepositVO lDepositVO = new LinkedDepositVO();
					lDepositVO.setLinkedDepositNo(args[3]);
					lDepositVO.setDepositAmount(Integer.parseInt(args[4]));
					lDepositVO.setDepositStartDate(parseDate(args[5]));
					lDepositVO.setDepositMaturityDate(parseDate(args[6]));
					lDepositVO.setMaturityAmount(calculateMaturityAmount(lDepositVO.getDepositStartDate(),
							lDepositVO.getDepositMaturityDate(), lDepositVO.getDepositAmount()));
					lDepositVOList.add(lDepositVO);
					pAccountVO.setLinkedDeposits(lDepositVOList);
					data.add(pAccountVO);
				}
				line = br.readLine();
			}
		}
	}

	private static Date parseDate(String date) {
		SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
		try {
			return sf.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	private static float calculateMaturityAmount(Date date1, Date date2, int depositamount) {
		float maturity_amount = 0.00f;
		int noOfDays = calculateDays(date1, date2);
		if (noOfDays >= 0 && noOfDays <= 200) {
			maturity_amount = (float) (depositamount + (depositamount * 6.75 / 100));
		} else if (noOfDays >= 201 && noOfDays <= 400) {
			maturity_amount = (float) (depositamount + (depositamount * 7.5 / 100));
		} else if (noOfDays >= 401 && noOfDays <= 600) {
			maturity_amount = (float) (depositamount + (depositamount * 8.75 / 100));
		} else {
			maturity_amount = (float) (depositamount + (depositamount * 10 / 100));
		}
		return maturity_amount;

	}

	private static int calculateDays(Date date1, Date date2) {
		int days = 0;
		if (date2.after(date1))
			days = (int) (date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24);
		return days;
	}

	public static boolean validateData(String[] strArray) throws BankOrganizerException {
		boolean result = true;

		if (strArray[0].isEmpty() || strArray[1].isEmpty() || strArray[2].isEmpty() || strArray[3].isEmpty()
				|| strArray[4].isEmpty() || strArray[5].isEmpty() || strArray[6].isEmpty()) {
			System.out.println("All fields are mendetory validation failed");
			result = false;
		}

		/*if (strArray[0].charAt(0) == '0') {
			System.out.println("Invalid account no validation failed" + strArray[0]);
			result = false;
		} else {
			for (Character c : strArray[0].toCharArray()) {
				if (!Character.isDigit(c)) {
					System.out.println("Invalid account no validation failed" + strArray[0]);
					result = false;
					break;
				}
			}
		}*/
		try {
		if (strArray[0].charAt(0) == '0' || Pattern.matches("[^0-9]", strArray[0])) {
			System.out.println("Invalid account no validation failed" + strArray[0]);
			result = false;
		}}catch(NumberFormatException e) {
			throw new BankOrganizerException("invalid account number validation failed",e);
		}

		if (!isValidDate(strArray[5])) {
			System.out.println("Invalid Date format");
			result = false;
		}

		if (!isValidDate(strArray[6])) {
			System.out.println("Invalid Date format");
			result = false;
		}

		if (!(strArray[2].equals("WM") || strArray[2].equals("SAV") || strArray[2].equals("NRI"))) {
			System.out.println("Invalid account type");
			result = false;
		}

		if (!(strArray[3].startsWith("FD") || strArray[3].startsWith("RD") || strArray[3].startsWith("MUT"))) {
			System.out.println("Invalid linked account depost number");
			result = false;
		}

		return result;
	}

	private static boolean isValidDate(String date) {
		SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
		try {
			sf.parse(date.trim());
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

}

class ParentAccountVO {

	private int parentAccNo;
	private String name;
	private String AccType;
	private List<LinkedDepositVO> linkedDeposits;

	public int getParentAccNo() {
		return parentAccNo;
	}

	public void setParentAccNo(int parentAccNo) {
		this.parentAccNo = parentAccNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccType() {
		return AccType;
	}

	public void setAccType(String accType) {
		AccType = accType;
	}

	public List<LinkedDepositVO> getLinkedDeposits() {
		return linkedDeposits;
	}

	public void setLinkedDeposits(List<LinkedDepositVO> linkedDeposits) {
		this.linkedDeposits = linkedDeposits;
	}

	public boolean equals(Object object) {
		boolean isEqual = false;
		ParentAccountVO otherAccount = (ParentAccountVO) object;
		if ((this.parentAccNo == otherAccount.parentAccNo) && (this.AccType.equals(otherAccount.getAccType())
				&& (this.linkedDeposits.equals(otherAccount.getLinkedDeposits())))) {
			isEqual = true;
		}
		return isEqual;
	}

	@Override
	public String toString() {
		return "ParentAccountVO [parentAccNo=" + parentAccNo + ", name=" + name + ", AccType=" + AccType
				+ ", linkedDeposits=" + linkedDeposits + "]";
	}

}

class LinkedDepositVO {

	private String linkedDepositNo;
	private int depositAmount;
	private Date depositStartDate;
	private Date depositMaturityDate;
	private float maturityAmount;

	public String getLinkedDepositNo() {
		return linkedDepositNo;
	}

	public void setLinkedDepositNo(String linkedDepositNo) {
		this.linkedDepositNo = linkedDepositNo;
	}

	public int getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(int depositAmount) {
		this.depositAmount = depositAmount;
	}

	public Date getDepositStartDate() {
		return depositStartDate;
	}

	public void setDepositStartDate(Date depositStartDate) {
		this.depositStartDate = depositStartDate;
	}

	public Date getDepositMaturityDate() {
		return depositMaturityDate;
	}

	public void setDepositMaturityDate(Date depositMaturityDate) {
		this.depositMaturityDate = depositMaturityDate;
	}

	public float getMaturityAmount() {
		return maturityAmount;
	}

	public void setMaturityAmount(float maturityAmount) {
		this.maturityAmount = maturityAmount;
	}

	public boolean equals(Object object) {
		boolean isEquals = false;
		LinkedDepositVO depositVO = (LinkedDepositVO) object;
		if (this.linkedDepositNo.equals(depositVO.getLinkedDepositNo())
				&& (this.depositAmount == depositVO.getDepositAmount())
				&& (this.depositStartDate.equals(depositVO.getDepositStartDate()))
				&& (this.maturityAmount == depositVO.getMaturityAmount())) {
			isEquals = true;
		}
		return isEquals;
	}

	@Override
	public String toString() {
		return "LinkedDepositVO [linkedDepositNo=" + linkedDepositNo + ", depositAmount=" + depositAmount
				+ ", depositStartDate=" + depositStartDate + ", depositMaturityDate=" + depositMaturityDate
				+ ", maturityAmount=" + maturityAmount + "]";
	}

}

class BankOrganizerException extends Exception {
	private static final long serialVersionUID = 1L;

	public BankOrganizerException(String message) {
		super(message);
	}

	public BankOrganizerException(Throwable throwable) {
		super(throwable);
	}

	public BankOrganizerException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
