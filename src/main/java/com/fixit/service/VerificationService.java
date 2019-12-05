package com.fixit.service;

import java.util.Calendar;
import java.util.Set;

import com.fixit.domain.vo.Verification;
import com.fixit.utility.FixitException;

public interface VerificationService {
	 /**
   	 * method used to find verification based in userId.
   	 * 
   	 * @param userId - Take Integer as argument.
   	 *  
   	 *  @return Set<Verification>
   	 */
	public Set<Verification> findVerificationByUserId(Integer userId) throws FixitException;
	 /**
   	 * method used to delete verification based in userId.
   	 * 
   	 * @param userId - Take Integer as argument.
   	 *  
   	 *  @return Integer
   	 */
	public int deleteVerificationByUserId(Integer userId) throws FixitException;
	/**
   	 * method used to save verification.
   	 * 
   	 * @param verification - Take Verification as argument.
   	 *  
   	 *  @return Verification
   	 */
	public Verification saveVerification(Verification verification) throws FixitException;
	/**
   	 * method used to find verification based in hashcode.
   	 * 
   	 * @param hashCode - Take String as argument.
   	 * @param time-Take calender as argument.
   	 *  
   	 *  @return Verification
   	 */
	public Verification  findVerificationByHashCode(String  hashCode, Calendar time) throws FixitException;
}
