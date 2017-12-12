package com.pw.payslip.exception;

public class PaySlipException extends Exception{
	
	public PaySlipException (String s, Throwable throwable){
		super(s,throwable);
	}
	
	 public PaySlipException(Throwable throwable) {
	        super(throwable);
	    }

}
