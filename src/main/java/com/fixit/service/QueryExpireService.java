package com.fixit.service;

import java.util.Set;

import com.fixit.domain.bo.QueryBo;
import com.fixit.domain.vo.Query;
import com.fixit.domain.vo.QueryExpire;
import com.fixit.domain.vo.User;
import com.fixit.utility.FixitException;

public interface QueryExpireService {
	 /**
	  * used to save query expire.
	  * 
	  * @param fixers - Set(collection) of type user(fixer).
	  * @param memberId - id of a user (member).
	  * @param baseUrl - current url . 
	  * 
	  * @return Set<QueryExpire>
	  */
	 public Set<QueryExpire> saveQueryExpire(Set<User> fixers , Integer memberId ,Integer queryId,String baseUrl) throws FixitException;
	 /**
	  * 
	  * used ot find query expire by fixerId and hashcode.
	  * @param fixerId - fixerId of a User(Fixer).
	  * @param hashcode - generated hashCode
	  * 
	  * @return QueryExpire
	  */ 
	 public QueryExpire findQueryExpireByFixerIdAndhashcode(Integer fixerId,String hashcode) throws FixitException;
	 /**
	  * 
	  * used to delete the query expire based on days interval.
	  * @param currentDate - current date from system
	  * @param days - number of days .
	  * 
	  * @return String
	  */
	 public String deleteQueryExpireByDaysInterval(String currentDate,int days) throws FixitException;

	
}
