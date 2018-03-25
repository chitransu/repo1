package com.java.validation;

//Do not include any package definition 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BankDepositAccountOrganizer {

	public static void main(String[] args) {
		try {
			processBankDepositData("accountdetails.txt");
		} catch (FileNotFoundException | BankOrganizerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Map<String, List<ParentAccountVO>> processBankDepositData(
			String filePath) throws BankOrganizerException, FileNotFoundException {
		
		Map finalMap = new HashMap<String, List<ParentAccountVO>>();
		
		try {
			BankDepositAccountOrganizer bao =new BankDepositAccountOrganizer();
			
			String s1 = null;
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			BufferedReader br = null;
			System.out.println("filePath" + filePath);
			File file= new File(filePath);
			System.out.println(file.exists());
			if(file.exists()){
			FileReader fr=new FileReader(file);
			br = new BufferedReader(fr);		
		//	br = new BufferedReader(new FileReader(filePath));		

			try {
				
				List<ParentAccountVO>pst1 = new ArrayList<ParentAccountVO>();
				List<ParentAccountVO>pst2 = new ArrayList<ParentAccountVO>();
				List<ParentAccountVO>pst3 = new ArrayList<ParentAccountVO>();
			
				while ((s1 = br.readLine()) != null)
				{
					System.out.println(" s1: " + s1);
					String[] values = s1.split(",");
					for(String s : values)
						System.out.println(s);
					boolean valResult = validateData(values);
					System.out.println(" validation Result is : " + valResult);

				
					ParentAccountVO po = new ParentAccountVO();

					if (!valResult) {
						String message = "validation failed";
						throw new BankOrganizerException(message);
					} else {
						
						System.out.println(" inside else");

						List<LinkedDepositVO> lst = new ArrayList<LinkedDepositVO>();

						LinkedDepositVO ld = new LinkedDepositVO();
                     int depositamount =Integer.parseInt(values[4]);
						ld.setDepositAmount(depositamount);
						String da = values[5];
						Date date1 = formatter.parse(da);
						String db = values[6];
						Date date2 = formatter.parse(db);
						System.out.println("Deposit Start Date" + formatter.format(date1));
						System.out.println("Deposit Maturity Date" + formatter.format(date2));
						System.out.println("Deposit Amount" + Integer.parseInt(values[4]));
						System.out.println("Deposit No" + values[3]);	
						System.out.println("Maturity Amount" + bao.calculateMaturityAmount(date1,date2,depositamount));	
						ld.setLinkedDepositNo(values[3]);						
                     ld.setDepositStartDate(date1);
                     ld.setDepositMaturityDate(date2);
                     ld.setMaturityAmount(bao.calculateMaturityAmount(date1,date2,depositamount));
                     
                     boolean flag = false;
						
                     if (values[2].equals("WM")) {
							
                     	for(ParentAccountVO parentAccountVO:pst1){
                     		
                     		if(parentAccountVO.getParentAccNo()==Integer.parseInt(values[0])){
                     			flag=true;
                     			List<LinkedDepositVO> linkedDepositVOs = parentAccountVO.getLinkedDeposits();
                     			linkedDepositVOs.add(ld);
                     		}
                     		
                     	}
                     	
						}
                     
                        if (values[2].equals("SAV")) {
							
                     	for(ParentAccountVO parentAccountVO:pst2){
                     		
                     		if(parentAccountVO.getParentAccNo()==Integer.parseInt(values[0])){
                     			flag=true;
                     			List<LinkedDepositVO> linkedDepositVOs = parentAccountVO.getLinkedDeposits();
                     			linkedDepositVOs.add(ld);
                     		}
                     		
                     	}
                     	
						}                           
                        if (values[2].equals("NRI")) {
							
                        	for(ParentAccountVO parentAccountVO:pst3){
                        		
                        		if(parentAccountVO.getParentAccNo()==Integer.parseInt(values[0])){
                        			flag=true;
                        			List<LinkedDepositVO> linkedDepositVOs = parentAccountVO.getLinkedDeposits();
                        			linkedDepositVOs.add(ld);
                        		}
                        		
                        	}
                        	
						}
                     
                     if(!flag){
                     	lst.add(ld);
                     	po.setAccType(values[2]);
 						po.setName(values[1]);
 						po.setParentAccNo(Integer.parseInt(values[0]));
 						po.setLinkedDeposits(lst);
 						if (values[2].equals("WM")) {
 							pst1.add(po);    							
 						}
 						if (values[2].equals("SAV")) {
 							pst2.add(po);    							
 						}
 						if (values[2].equals("NRI")) {
 							pst3.add(po);    						
 						}
                     }                       
                
					}
				}	
							
				finalMap.put("WM", pst1);
				
				finalMap.put("SAV", pst2);
				
				finalMap.put("NRI", pst3);
				
			//	System.out.println("Final Map is " + finalMap.get("WM"));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
				throw new BankOrganizerException("FIle io exception: "
						+ e.getMessage());
			}

			}	
			
			
		 else {
		 
			 throw new FileNotFoundException("FIle Not found exception: " );
			 
		 
		 }
				
		
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
				throw new FileNotFoundException("FIle Not found exception: "
						+ e.getMessage());
			
		}
	catch(ParseException e){
			throw new BankOrganizerException("FIle Parse exception: "
					+ e.getMessage());
			} 

		System.out.println("Final Map is " + finalMap.entrySet());
		return finalMap;
		
		// TODO Associate to type their code here
		// TODO Associate to add a return statement to avoid compilation error
	}
	
	private float calculateMaturityAmount(Date date1, Date date2,int depositamount){
		float maturity_amount=0.00f;
		int noofdays=daysBetween(date1,date2);
		
		System.out.println("noofdays" + noofdays);
		
		
		if(noofdays >= 0 && noofdays <= 200){
			maturity_amount=(float)(depositamount +(depositamount*6.75/100));
		}else if(noofdays >= 201 && noofdays <= 400){
			maturity_amount=(float)(depositamount +(depositamount*7.5/100));
		}else if(noofdays >= 401 && noofdays <= 600){
			maturity_amount=(float)(depositamount +(depositamount*8.75/100));
		}else {
			maturity_amount=(float)(depositamount +(depositamount*10/100));
			
		}
		
		return maturity_amount;
		
		
	}
	public int daysBetween(Date d1, Date d2){
     return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
}
	public static boolean validateData(String[] str) {
		boolean result = true;
		// field checks
		
			if (str[0].isEmpty() || str[1].isEmpty() || str[2].isEmpty()
					|| str[3].isEmpty() || str[4].isEmpty() || str[5].isEmpty())
		{
			System.out.println("All fileds are mandatory validation failed");
			result = false;
		}
		// Account no validation		
			//System.out.println(" Account no validation" + str[0].charAt(0) );
			if(str[0].charAt(0) =='0'){
				System.out.println("Hello" + str[0].charAt(0));
			System.out.println("account number is not valid");
			result = false;			
			}
			else{
				for(Character c : str[0].toCharArray()){
				 if (!Character.isDigit(c)){ 
					 result=false;
					 System.out.println("account number is not valid");
					 break;
				 }
				}
			}
		
		// Account type

		if (!(str[2].equals("WM") || str[2].equals("SAV") || str[2]
				.equals("NRI"))) {
			System.out.println("Type of account is not valid");
			result = false;
		}
		// Linked Deposit validation

		if (!(str[3].startsWith("FD") || str[3].startsWith("RD") || str[3]
				.startsWith("MUT"))) {
			System.out.println("Linked Deposit  account is not valid");
			result = false;
		}
		
		// Deposit Start date validation
		
		if(! isValidDate(str[5])){
			result = false;
		}
		
		// Deposit Maturity date validation
		
		if(! isValidDate(str[6])){
			result = false;
		}
		return result;

	}
	public static boolean isValidDate(String inDate) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	    dateFormat.setLenient(false);
	    try {
	      dateFormat.parse(inDate.trim());
	    } catch (ParseException pe) {
	      return false;
	    }
	    return true;
	  }

}

class ParentAccountVO {

	private int parentAccNo;
	private String name;
	private String AccType;
	//private LinkedDepositVO linkedDeposit;
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
		if ((this.parentAccNo == otherAccount.parentAccNo)
				&& (this.AccType.equals(otherAccount.getAccType()) && (this.linkedDeposits
						.equals(otherAccount.getLinkedDeposits())))) {
			isEqual = true;
		}
		return isEqual;
	}

	

	@Override
	public String toString() {
		return "ParentAccountVO [parentAccNo=" + parentAccNo + ", name=" + name
				+ ", AccType=" + AccType + ", linkedDeposits=" + linkedDeposits
				+ "]";
		
	//	return parentAccNo  + "  , " +  name  + " ," + AccType + " ," +  linkedDeposits;
		
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
				&& (this.depositStartDate.equals(depositVO
						.getDepositStartDate()))
				&& (this.maturityAmount == depositVO.getMaturityAmount())) {
			isEquals = true;
		}
		return isEquals;
	}

	@Override
	public String toString() {
	
	
		return "LinkedDepositVO [linkedDepositNo=" + linkedDepositNo
				+ ", depositAmount=" + depositAmount + ", depositStartDate="
				+ depositStartDate + ", depositMaturityDate="
				+ depositMaturityDate + ", maturityAmount=" + maturityAmount
				+ "]"; 
		
	//	return linkedDepositNo  + "  , " +  depositAmount  + " ," + depositStartDate + " ," +  depositMaturityDate + "," + maturityAmount;
	}

}

class BankOrganizerException extends Exception {
	/**
	 * 
	 */
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

/************************************************************/
/*
* DO NOT CHANGE THE BELOW CLASS. THIS IS FOR VERIFYING THE CLASS NAME AND
* METHOD SIGNATURE USING REFLECTION APIs
*/
/************************************************************/
class Validator {

	private static final Logger LOG = Logger.getLogger("Validator");

	public Validator(String filePath, String className, String methodWithExcptn) {
		validateStructure(filePath, className, methodWithExcptn);
	}

	protected final void validateStructure(String filePath, String className,
			String methodWithExcptn) {

		if (validateClassName(className)) {
			validateMethodSignature(methodWithExcptn, className);
		}

	}

	protected final boolean validateClassName(String className) {

		boolean iscorrect = false;
		try {
			Class.forName(className);
			iscorrect = true;
			LOG.info("Class Name is correct");

		} catch (ClassNotFoundException e) {
			LOG.log(Level.SEVERE, "You have changed either the "
					+ "class name/package. Use the default package "
					+ "and class name as provided in the skeleton");

		} catch (Exception e) {
			LOG.log(Level.SEVERE, "There is an error in validating the "
					+ "Class Name. Please manually verify that the "
					+ "Class name is same as skeleton before uploading");
		}
		return iscorrect;

	}

	protected final void validateMethodSignature(String methodWithExcptn,
			String className) {
		Class cls;
		try {

			String[] actualmethods = methodWithExcptn.split(",");
			boolean errorFlag = false;
			String[] methodSignature;
			String methodName = null;
			String returnType = null;

			for (String singleMethod : actualmethods) {
				boolean foundMethod = false;
				methodSignature = singleMethod.split(":");

				methodName = methodSignature[0];
				returnType = methodSignature[1];
				cls = Class.forName(className);
				Method[] methods = cls.getMethods();
				for (Method findMethod : methods) {
					if (methodName.equals(findMethod.getName())) {
						foundMethod = true;
						if ((findMethod.getExceptionTypes().length != 1)) {
							LOG.log(Level.SEVERE, "You have added/removed "
									+ "Exception from '" + methodName
									+ "' method. "
									+ "Please stick to the skeleton provided");
						}
						if (!(findMethod.getReturnType().getName()
								.equals(returnType))) {
							errorFlag = true;
							LOG.log(Level.SEVERE, " You have changed the "
									+ "return type in '" + methodName
									+ "' method. Please stick to the "
									+ "skeleton provided");

						}

					}
				}
				if (!foundMethod) {
					errorFlag = true;
					LOG.log(Level.SEVERE,
							" Unable to find the given public method "
									+ methodName + ". Do not change the "
									+ "given public method name. "
									+ "Verify it with the skeleton");
				}

			}
			if (!errorFlag) {
				LOG.info("Method signature is valid");
			}

		} catch (Exception e) {
			LOG.log(Level.SEVERE,
					" There is an error in validating the "
							+ "method structure. Please manually verify that the "
							+ "Method signature is same as the skeleton before uploading");
		}
	}
}
