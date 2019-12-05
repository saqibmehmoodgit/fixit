package com.fixit.utility;

/**
 * @author LINCHPIN_PC
 * 
 */

public class FixitException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String exceptionMsg;
	private String exceptionCode;
	private Throwable cause;

	public FixitException() {

	}
	
	public FixitException(String exceptionCode, String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
		this.exceptionCode = exceptionCode;
	}
	
	public FixitException(Throwable cause, String exceptionCode, String exceptionMsg) {
		this.cause = cause;
		this.exceptionMsg = exceptionMsg;
		this.exceptionCode = exceptionCode;
	}

	/**
	 * @return the cause
	 */
	public Throwable getCause() {
		return cause;
	}

	/**
	 * @return the exceptionCode
	 */
	public String getExceptionCode() {
		return exceptionCode;
	}

	public String getExceptionMsg() {
		return this.exceptionMsg;
	}

}